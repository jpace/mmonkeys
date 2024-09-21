package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.trials.base.InvokeTrial
import org.incava.mmonkeys.type.Keys
import org.incava.time.Durations.measureDuration
import java.lang.Thread.sleep
import kotlin.random.Random

class RandomsTrial(private val numChars: Int, private val strLength: Int, private val iterations: Int) {
    private val random = Random.Default

    init {
        Console.info("numChars", numChars)
        Console.info("strLength", strLength)
        Console.info("iterations", iterations)
    }

    private fun runTest(block: () -> Unit) {
        val numInvokes = iterations.toLong() * strLength
        val trial = InvokeTrial("anonymous", numInvokes, block)
        val duration = trial.run()
        Console.info("trial.duration", duration)
    }

    private fun runTest2(name: String, block: () -> Unit) {
        Console.info("name", name)
        block()
    }

    private fun `int numChars`() {
        runTest {
            val x = random.nextInt(numChars)
        }
    }

    private fun `int numChars List`() {
        val list = Keys.fullList()
        runTest {
            val x = random.nextInt(numChars)
            val y = list[x]
        }
    }

    private fun `int numChars Array`() {
        val ary = Keys.fullList().toTypedArray()
        runTest {
            val x = random.nextInt(numChars)
            val y = ary[x]
        }
    }

    private fun `int numChars List StringBuffer`() {
        val list = Keys.fullList()
        val sb = StringBuffer()
        runTest {
            val x = random.nextInt(numChars)
            val y = list[x]
            sb.append(y)
        }
    }

    private fun `int numChars List toString`() {
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
        Console.info("duration", duration)
    }

    private fun `int numChars Array toString`() {
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
        Console.info("duration", duration)
    }

    private fun nextBytes() {
        val bytes = ByteArray(strLength)
        runTest {
            val x = random.nextBytes(bytes)
        }
    }

    private fun `nextBytes toString`() {
        val bytes = ByteArray(strLength)
        val list = Keys.fullList()
        val duration = measureDuration {
            repeat(iterations) {
                val str = (bytes.indices).map { idx ->
                    val b = bytes[idx]
                    val asAbs = b.toInt() + 127
                    val aryIndex = asAbs * 27 / 255
                    list[aryIndex]
                }.joinToString("")
            }
        }
        Console.info("duration", duration)
    }

    fun runTest() {
        val methods = listOf(
            ::`int numChars`,
            ::`int numChars List`,
            ::`int numChars Array`,
            ::`int numChars List StringBuffer`,
            ::`int numChars List toString`,
            ::`int numChars Array toString`,
            ::nextBytes,
            ::`nextBytes toString`,
        )
        val methods2 = methods.map { it.name to it }
        // val shuffled = methods.shuffled()
        methods2.forEach { (name, function) ->
            runTest2(name, function)
            sleep(500L)
        }
        println()
    }
}

fun main() {
    val params = listOf(
        4 to 100_000_000,
        5 to 70_000_000,
        6 to 40_000_000,
        7 to 20_000_000,
        8 to 20_000_000,
        9 to 10_000_000,
    )
    params.forEach {
        val obj = RandomsTrial(27, it.first, it.second)
        obj.runTest()
    }
}