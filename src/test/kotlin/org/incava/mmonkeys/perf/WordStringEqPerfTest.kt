package org.incava.mmonkeys.perf

import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.util.Console.log
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep
import kotlin.math.pow
import kotlin.random.Random
import kotlin.system.measureTimeMillis

@Disabled
internal class WordStringEqPerfTest {
    private fun runPerfTest(x: Any, y: Any, z: Any, type: String, iterations: Long) {
        val duration = measureTimeMillis {
            for (i in 0..iterations) {
                val a = x == x
                val b = x == y
                val c = x == z
            }
        }
        log(type)
        log("iterations", iterations)
        log("duration", duration)
    }

    private fun runPerfTestWord(s: String, t: String, iterations: Long) {
        val x = Word(s)
        val y = Word(s)
        val z = Word(t)
        runPerfTest(x, y, z, "Word", iterations)
    }

    private fun runPerfTestString(s: String, t: String, iterations: Long) {
        // copy the strings, so the references are not the same
        val x = String(s.toByteArray())
        val y = String(s.toByteArray())
        val z = String(t.toByteArray())
        runPerfTest(x, y, z, "String", iterations)
    }

    private fun runPerfTest(s: String, t: String, iterations: Long) {
        sleep(2000L)
        val b = Random.nextBoolean()
        log("s", s)
        log("t", t)
        if (b) {
            runPerfTestWord(s, t, iterations)
            sleep(2000L)
            runPerfTestString(s, t, iterations)
        } else {
            runPerfTestString(s, t, iterations)
            sleep(2000L)
            runPerfTestWord(s, t, iterations)
        }
        sleep(2000L)
    }

    @Test
    fun equalsPerformance() {
        val exponent = 8
        val iterations = 10.0.pow(exponent).toLong()
        runPerfTest("this is a test", "this is also a test", iterations)
        runPerfTest("abc", "this is also a test", iterations)
        runPerfTest("abc", "defghi", iterations)
        runPerfTest("abc", "abcdef", iterations)
    }
}