package org.incava.mmonkeys

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.MatchData

interface MonkeyAttempting {
    fun add(matchData: MatchData)

    fun summarize()

    fun showMatches(limit: Int)
}

class MonkeyAttempts(private val tick: Int = 50_000) : MonkeyAttempting {
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

class MonkeyAttempts2(private val tick: Int = 50_000) : MonkeyAttempting {
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

class MonkeyAttempts3(private val tick: Int = 1000) {
    val succeeded = mutableMapOf<Int, MutableList<Int>>()
    val failed = mutableListOf<Long>()
    var index = 0
    var errantKeystrokes = 0L

    fun add(matchData: MatchData) {
        if (matchData.isMatch) {
            failed += errantKeystrokes
            if (failed.size % tick == 0) {
                Console.info(".failed.#", failed.size)
            }
            succeeded.computeIfAbsent(matchData.keystrokes) { mutableListOf() }.also { it += matchData.index }
            errantKeystrokes = 0L
        } else {
            errantKeystrokes += matchData.keystrokes
        }
        index++
    }

    fun summarize() {
        Console.info("attempts.#", succeeded.size + failed.size)
        Console.info("index", index)
        Console.info(".success.#", succeeded.size)
        Console.info(".failed.#", failed.size)
        Console.info(".success.keys.#", succeeded.keys.size)
//        Console.info(".succeeded", succeeded)
//        Console.info(".failed", failed)
        Console.info(".failed.sum", failed.sum())
        val sum = succeeded.keys.fold(0) { acc, key ->
            acc + succeeded[key]!!.sum()
        }
        Console.info(".success.sum", sum)
    }
}
