package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.trials.base.InvokeTrial
import org.incava.mmonkeys.type.Keys
import org.incava.time.Durations
import org.incava.time.Durations.measureDuration
import java.lang.Thread.sleep
import java.time.Duration
import kotlin.random.Random

class RandomsTrial(private val numChars: Int, private val strLength: Int, private val iterations: Int) {
    private val random = Random.Default

    init {
        Console.info("numChars", numChars)
        Console.info("strLength", strLength)
        Console.info("iterations", iterations)
    }

    private fun showResults(name: String, duration: Duration) {
        System.out.printf("%-24.24s | %s\n", name, Durations.formatted(duration))
    }

    private fun runTest(name: String, block: () -> Unit) {
        val numInvokes = iterations.toLong() * strLength
        val trial = InvokeTrial(name, numInvokes, block)
        val duration = trial.run()
        showResults(name, duration)
    }

    private fun `int numChars only`() {
        runTest("int numChars only") {
            val x = random.nextInt(numChars)
        }
    }

    private fun `int numChars List get`() {
        val list = Keys.fullList()
        runTest("int numChars List get") {
            val x = random.nextInt(numChars)
            val y = list[x]
        }
    }

    private fun `int numChars Array get`() {
        val ary = Keys.fullList().toTypedArray()
        runTest("int numChars Array get") {
            val x = random.nextInt(numChars)
            val y = ary[x]
        }
    }

    private fun `int numChars List StringBuffer`() {
        val list = Keys.fullList()
        val sb = StringBuffer()
        runTest("int numChars List StringBuffer") {
            val x = random.nextInt(numChars)
            val y = list[x]
            sb.append(y)
        }
    }

    private fun `int numChars List StringBuilder`() {
        val list = Keys.fullList()
        val duration = measureDuration {
            repeat(iterations) {
                val sb = StringBuilder()
                repeat(strLength) {
                    val x = random.nextInt(numChars)
                    val y = list[x]
                    sb.append(y)
                }
                val str = sb.toString()
            }
        }
        showResults("int numChars List StringBuilder", duration.second)
    }

    private fun `int numChars Array StringBuilder`() {
        val ary = Keys.fullList().toTypedArray()
        val duration = measureDuration {
            repeat(iterations) {
                val sb = StringBuilder()
                repeat(strLength) {
                    val x = random.nextInt(numChars)
                    val y = ary[x]
                    sb.append(y)
                }
                val str = sb.toString()
            }
        }
        showResults("int numChars Array StringBuilder", duration.second)
    }

    private fun nextBytes() {
        val bytes = ByteArray(strLength)
        runTest("nextBytes") {
            val x = random.nextBytes(bytes)
        }
    }

    private fun `nextBytes toString`() {
        val bytes = ByteArray(strLength)
        val list = Keys.fullList()
        val duration = measureDuration {
            repeat(iterations) {
                val x = random.nextBytes(bytes)
                val str = (x.indices).map { idx ->
                    val b = x[idx]
                    val asAbs = b.toInt() + 127
                    val aryIndex = asAbs * 27 / 255
                    list[aryIndex]
                }.joinToString("")
            }
        }
        showResults("nextBytes toString", duration.second)
    }

    fun runTest() {
        val methods = listOf(
            ::`int numChars only`,
            ::`int numChars List get`,
            ::`int numChars Array get`,
            ::`int numChars List StringBuffer`,
            ::`int numChars List StringBuilder`,
            ::`int numChars Array StringBuilder`,
            ::nextBytes,
            ::`nextBytes toString`,
        )
        methods.forEach { function ->
            function()
            sleep(500L)
        }
        println()
    }
}

fun main() {
    val params = listOf(
        4 to 100_000_000,
        5 to 70_000_000,
//        6 to 40_000_000,
//        7 to 20_000_000,
//        8 to 20_000_000,
//        9 to 10_000_000,
    )
    params.forEach {
        val obj = RandomsTrial(27, it.first, it.second)
        obj.runTest()
    }
}