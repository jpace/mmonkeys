package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console

interface MonkeyAttempting {
    fun add(matchData: MatchData)

    fun summarize()

    fun showMatches(limit: Int)
}

class MonkeyAttemptsList(private val tick: Int = 50_000) : MonkeyAttempting {
    private val attempts = mutableListOf<MatchData>()

    override fun add(matchData: MatchData) {
        if (matchData.isMatch && attempts.size % tick == 0) {
            Console.info("attempts.#", attempts.size)
        }
        attempts += matchData
    }

    override fun showMatches(limit: Int) {
        (0 until limit).forEach { index ->
            Console.info("attempts[$index]", attempts[index])
        }
    }

    override fun summarize() {
        Console.info("attempts.#", attempts.size)
        Console.info(".success.#", attempts.filter { it.isMatch }.size)
        Console.info(".failed.#", attempts.filterNot { it.isMatch }.size)
        Console.info(".keys.#", attempts.map { it.keystrokes }.size)
        Console.info(".keys.set.#", attempts.map { it.keystrokes }.toSet().size)
        Console.info(".keys", attempts.map { it.keystrokes }.toSortedSet())
    }
}

class MonkeyAttemptsMapAndList(private val tick: Int = 50_000) : MonkeyAttempting {
    val succeeded = mutableMapOf<Int, MatchData>()
    val failed = mutableListOf<Long>()
    var index = 0
    var errantKeystrokes = 0L

    override fun add(matchData: MatchData) {
        if (matchData.isMatch) {
            failed += errantKeystrokes
            if (failed.size % tick == 0) {
                Console.info(".failed.#", failed.size)
            }
            succeeded[index] = matchData
            errantKeystrokes = 0L
            index++
        } else {
            errantKeystrokes += matchData.keystrokes
        }
    }

    override fun showMatches(limit: Int) {
        Console.info("succeeded.#", succeeded.size)
        Console.info("failed.#", failed.size)
        (0 until limit).forEach { index ->
            System.out.printf("%12d | %12d | %d\n", index, failed[index], succeeded[index]?.keystrokes)
        }
        Console.info("errantKeystrokes", errantKeystrokes)
    }

    override fun summarize() {
        Console.info("attempts.#", succeeded.size + failed.size)
        Console.info("index", index)
        Console.info(".success.#", succeeded.size)
        Console.info(".success.keys.#", succeeded.keys.size)
        Console.info(".failed.#", failed.size)
        Console.info(".failed.sum", failed.sum())
        val sum = succeeded.keys.fold(0) { acc, key ->
            acc + succeeded[key]!!.keystrokes
        }
        Console.info(".success.sum", sum)
    }
}

class MonkeyAttemptsNoOp(private val tick: Int = 50_000) : MonkeyAttempting {
    var invocations: Long = 0L

    override fun add(matchData: MatchData) {
        if (invocations % (tick * 100L) == 0L) {
            Console.info("invocations", invocations)
        }
        invocations++
    }

    override fun showMatches(limit: Int) {
    }

    override fun summarize() {
        Console.info("invocations", invocations)
    }
};

class StrokesAndIndex(val keystrokes: Long, val index: Int)

class MonkeyAttemptsMapListPair(private val tick: Int = 1000) : MonkeyAttempting {
    // key: keystrokes, value: list of (first: previous errant keystrokes, second: index)
    val results = mutableMapOf<Int, MutableList<StrokesAndIndex>>()
    var errantKeystrokes = 0L

    override fun add(matchData: MatchData) {
        if (matchData.isMatch) {
            results.computeIfAbsent(matchData.keystrokes) { mutableListOf() }.also { it += StrokesAndIndex(errantKeystrokes, matchData.index) }
            errantKeystrokes = 0L
        } else {
            errantKeystrokes += matchData.keystrokes
        }
    }

    override fun summarize() {
        Console.info("result.#", results.size)
        Console.info("result.keys", results.keys.toSortedSet())
    }

    override fun showMatches(limit: Int) {
        Console.info("result.#", results.size)
        results.keys.toSortedSet().forEach { key ->
            Console.info("key", key)
            val matches = results[key]!!
            Console.info("results[$key]", matches.subList(0, 10.coerceAtMost(matches.size)))
        }
        Console.info("errantKeystrokes", errantKeystrokes)
    }
}
