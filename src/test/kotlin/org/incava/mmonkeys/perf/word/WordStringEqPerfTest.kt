package org.incava.mmonkeys.perf.word

import org.incava.mmonkeys.util.Console
import org.incava.mmonkeys.word.Word
import java.lang.Thread.sleep
import kotlin.math.pow
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class WordStringEqPerfTest {
    private fun runPerfTest(x: Any, y: Any, z: Any, type: String, iterations: Long) {
        val whence = "WordStringEqPerfTest"
        val duration = measureTimeMillis {
            for (i in 0..iterations) {
                val a = x == x
                val b = x == y
                val c = x == z
            }
        }
        Console.info(whence, type)
        Console.info(whence, "iterations", iterations)
        Console.info(whence, "duration", duration)
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
        val whence = "WordStringEqPerfTest"
        sleep(2000L)
        val b = Random.nextBoolean()
        Console.info(whence, "s", s)
        Console.info(whence, "t", t)
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

    fun equalsPerformance() {
        val exponent = 8
        val iterations = 10.0.pow(exponent).toLong()
        runPerfTest("this is a test", "this is also a test", iterations)
        runPerfTest("abc", "this is also a test", iterations)
        runPerfTest("abc", "defghi", iterations)
        runPerfTest("abc", "abcdef", iterations)
    }
}

fun main() {
    WordStringEqPerfTest().equalsPerformance()
}