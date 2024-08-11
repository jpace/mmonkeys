package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.util.SimClock2

abstract class MonkeyAttempts {
    var totalKeystrokes: Long = 0L
    var count: Long = 0L
    val matchKeystrokes = mutableMapOf<Int, Int>()
    val simClock = SimClock2()

    open fun add(matchData: MatchData) {
        // add 1 to account for the space after the match/mismatch
        totalKeystrokes += (matchData.keystrokes + 1)
        count++
        if (matchData.isMatch) {
            simClock.writeLine(totalKeystrokes, "${matchData.keystrokes} - ${matchData.index}")
            matchKeystrokes.merge(matchData.keystrokes, 1) { prev, _ -> prev + 1 }
        }
    }

    abstract fun summarize()

    abstract fun showMatches(limit: Int)

    operator fun plusAssign(matchData: MatchData) {
        add(matchData)
    }
}

class MonkeyAttemptsList(private val tick: Int = 50_000) : MonkeyAttempts() {
    private val attempts = mutableListOf<MatchData>()

    override fun add(matchData: MatchData) {
        super.add(matchData)
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

class MonkeyAttemptsMapAndList(private val tick: Int = 50_000) : MonkeyAttempts() {
    val succeeded = mutableMapOf<Int, MatchData>()
    val failed = mutableListOf<Long>()
    var index = 0
    var errantKeystrokes = 0L

    override fun add(matchData: MatchData) {
        super.add(matchData)
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

class MonkeyAttemptsCounting(private val tick: Int = 50_000) : MonkeyAttempts() {
    override fun add(matchData: MatchData) {
        super.add(matchData)
        if (count % (tick * 100L) == 0L) {
            Console.info("count", count)
        }
    }

    override fun showMatches(limit: Int) {
    }

    override fun summarize() {
        Console.info("#keystrokes", totalKeystrokes)
        Console.info("count", count)
    }
};

class StrokesAndIndex(val keystrokes: Long, val index: Int)

class MonkeyAttemptsMapListPair(private val tick: Int = 1000) : MonkeyAttempts() {
    // key: keystrokes, value: list of (first: previous errant keystrokes, second: index)
    val results = mutableMapOf<Int, MutableList<StrokesAndIndex>>()
    var errantKeystrokes = 0L

    override fun add(matchData: MatchData) {
        super.add(matchData)
        if (matchData.isMatch) {
            results.computeIfAbsent(matchData.keystrokes) { mutableListOf() }
                .also { it += StrokesAndIndex(errantKeystrokes, matchData.index) }
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
