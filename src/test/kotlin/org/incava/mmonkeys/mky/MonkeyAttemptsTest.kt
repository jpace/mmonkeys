package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.LengthCorpus
import org.incava.mmonkeys.mky.corpus.LengthCorpusMonkey
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.MemoryUtil
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.random.Random

@Disabled
class MonkeyAttemptsTest {
    val random = Random.Default

    init {
        val (total, _, _) = MemoryUtil.currentMemory()
        Console.info("total", total)
    }

    @Test
    fun addMonkeyAttemptsList() {
        val monkey = LengthCorpusMonkey(LengthCorpus(listOf("abc")), 1, Typewriter())
        val obj = MonkeyAttemptsList(1, monkey)
        var index = 0
        repeat(100_000_000) {
            tick(it)
            val (matchData, newIndex) = createMatchData(index)
            index = newIndex
            obj.add(monkey, matchData)
        }
        obj.summarize()
    }

    @Test
    fun addMonkeyAttemptsMapAndList() {
        val monkey = LengthCorpusMonkey(LengthCorpus(listOf("abc")), 1, Typewriter())
        val obj = MonkeyAttemptsMapAndList(1, monkey)
        var index = 0
        repeat(1_000_000_000) {
            tick(it, 1_000_000)
            val (matchData, newIndex) = createMatchData(index)
            index = newIndex
            obj.add(monkey, matchData)
        }
        obj.summarize()
    }

    @Test
    fun addMonkeyAttemptsMapAndList1() {
        val monkey = LengthCorpusMonkey(LengthCorpus(listOf("abc")), 1, Typewriter())
        var index = 0
        val monkeyAttempts = mutableListOf<MonkeyAttemptsMapAndList>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttemptsMapAndList(100_000, monkey)
            monkeyAttempts += obj
            Console.info("outer", outer)
            repeat(1_000_000_000) { inner ->
                tick(inner, 500_000_000)
                val (matchData, newIndex) = createMatchData(index)
                index = newIndex
                obj.add(monkey, matchData)
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
            MatchData(true, keystrokes, index) to index + 1
        } else {
            MatchData(false, keystrokes, 0) to index
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