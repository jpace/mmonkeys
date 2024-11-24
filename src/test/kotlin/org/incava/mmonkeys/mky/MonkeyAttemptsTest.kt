package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.MapMonkeyUtils
import org.incava.mmonkeys.util.MemoryUtil
import org.junit.jupiter.api.Test
import kotlin.random.Random

// keeping these around in case I want to profile the overhead
abstract class MonkeyAttempts(val monkey: Monkey) : MonkeyMonitor {
    private var totalKeystrokes: Long = 0L
    var count: Long = 0L
    private var matchCount = 0

    override fun add(monkey: Monkey, matchData: MatchData) {
        // add 1 to account for the space after the match/mismatch
        // totalKeystrokes == virtual seconds:
        totalKeystrokes += (matchData.keystrokes + 1)
        count++
        if (matchData.index != null) {
            ++matchCount
        }
    }

    override fun keystrokesCount(): Long = totalKeystrokes

    override fun attemptCount(): Long = count

    override fun matchCount(): Int = matchCount

    abstract fun showMatches(limit: Int)
}

class MonkeyAttemptsList(id: Int, monkey: Monkey, private val tick: Int = 50_000) : MonkeyAttempts(monkey) {
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

class MonkeyAttemptsMapAndList(id: Int, monkey: Monkey, private val tick: Int = 50_000) : MonkeyAttempts(monkey) {
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

class MonkeyAttemptsTest {
    val random = Random.Default

    init {
        val (total, _, _) = MemoryUtil.currentMemory()
        Console.info("total", total)
    }

    fun runIt(iteration: Int, index: Int, attempts: MonkeyAttempts, monkey: Monkey): Int {
        tick(iteration)
        val (matchData, newIndex) = createMatchData(index)
        attempts.add(monkey, matchData)
        return newIndex
    }

    @Test
    fun addMonkeyAttemptsList() {
        val monkey = MapMonkeyUtils.createDefaultMonkey(listOf("abc"))
        val obj = MonkeyAttemptsList(1, monkey)
        var index = 0
        repeat(100_000_000) {
            index = runIt(it, index, obj, monkey)
        }
        obj.summarize()
    }

    @Test
    fun addMonkeyAttemptsMapAndList() {
        val monkey = MapMonkeyUtils.createDefaultMonkey(listOf("abc"))
        val obj = MonkeyAttemptsMapAndList(1, monkey)
        var index = 0
        repeat(1_000_000_000) {
            index = runIt(it, index, obj, monkey)
        }
        obj.summarize()
    }

    @Test
    fun addMonkeyAttemptsMapAndList1() {
        val monkey = MapMonkeyUtils.createDefaultMonkey(listOf("abc"))
        var index = 0
        val monkeyAttempts = mutableListOf<MonkeyAttemptsMapAndList>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttemptsMapAndList(100_000, monkey)
            monkeyAttempts += obj
            Console.info("outer", outer)
            repeat(1_000_000_000) { inner ->
                index = runIt(inner, index, obj, monkey)
            }
            if (outer % 100 == 0) {
                obj.summarize()
            }
        }
    }

    private fun createMatchData(index: Int): Pair<MatchData, Int> {
        val isMatch = random.nextInt(10_000) == 3
        val keystrokes = random.nextInt(40)
        return if (isMatch) {
            MatchData(keystrokes, index) to index + 1
        } else {
            MatchData(false, keystrokes, null) to index
        }
    }

    private fun tick(iterations: Int, every: Int = 500_000) {
        if (iterations % every == 0) {
            Console.info("iterations", iterations)
            val (_, free, used) = MemoryUtil.currentMemory()
            Console.info("free", free)
            Console.info("used", used)
            println()
        }
    }
}