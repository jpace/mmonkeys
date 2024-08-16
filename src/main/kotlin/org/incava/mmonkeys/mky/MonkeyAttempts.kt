package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.util.SimClock4

interface MonkeyMonitor {
    fun add(monkey: Monkey, matchData: MatchData)

    fun summarize()

    fun attemptCount(): Long

    fun matchCount(): Int

    fun keystrokesCount(): Long
}

abstract class MonkeyAttempts(val id: Int, val monkey: Monkey) : MonkeyMonitor {
    var totalKeystrokes: Long = 0L
    var count: Long = 0L
    val matchKeystrokes = mutableMapOf<Int, Int>()

    override fun add(monkey: Monkey, matchData: MatchData) {
        // add 1 to account for the space after the match/mismatch
        totalKeystrokes += (matchData.keystrokes + 1)
        count++
        if (matchData.isMatch) {
            // totalKeystrokes == virtual seconds:
            // simClock.writeMatch(monkey, totalKeystrokes, matchData, "wtf?")
            matchKeystrokes.merge(matchData.keystrokes, 1) { prev, _ -> prev + 1 }
        }
    }

    override fun keystrokesCount(): Long = totalKeystrokes

    override fun attemptCount(): Long = count

    override fun matchCount(): Int = matchKeystrokes.values.sum()

    abstract fun showMatches(limit: Int)
}

class MonkeyAttemptsList(id: Int, monkey: Monkey, private val tick: Int = 50_000) : MonkeyAttempts(id, monkey) {
    private val attempts = mutableListOf<MatchData>()

    override fun add(monkey: Monkey, matchData: MatchData) {
        super.add(monkey, matchData)
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

class MonkeyAttemptsMapAndList(id: Int, monkey: Monkey, private val tick: Int = 50_000) : MonkeyAttempts(id, monkey) {
    val succeeded = mutableMapOf<Int, MatchData>()
    val failed = mutableListOf<Long>()
    var index = 0
    var errantKeystrokes = 0L

    override fun add(monkey: Monkey, matchData: MatchData) {
        super.add(monkey, matchData)
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

class MonkeyManager(val corpus: Corpus) : MonkeyMonitor {
    private var totalKeystrokes: Long = 0L
    private var count: Long = 0L
    private val matchKeystrokes = mutableMapOf<Int, Int>()
    private val simClock = SimClock4(corpus)

    override fun add(monkey: Monkey, matchData: MatchData) {
        // add 1 to account for the space after the match/mismatch
        totalKeystrokes += (matchData.keystrokes + 1)
        count++
        if (matchData.isMatch) {
            // totalKeystrokes == virtual seconds:
            simClock.writeMatch(monkey, matchData, totalKeystrokes, matchCount())
            matchKeystrokes.merge(matchData.keystrokes, 1) { prev, _ -> prev + 1 }
        }
    }

    override fun summarize() {
        Console.info("#keystrokes", totalKeystrokes)
        Console.info("count", count)
    }

    override fun attemptCount(): Long = count

    override fun matchCount(): Int = matchKeystrokes.values.sum()

    override fun keystrokesCount() = totalKeystrokes
}
