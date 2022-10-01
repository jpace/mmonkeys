package org.incava.mmonkeys.perf.rand

import org.incava.mmonkeys.util.Console
import java.lang.Thread.sleep
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class RandomsTest(
    private val numChars: Int = 27,
    private val strLength: Int = 6,
    private val iterations: Int = 100_000_000,
) {
    private val random = Random.Default

    init {
        Console.info("numChars", numChars)
        Console.info("strLength", strLength)
        Console.info("iterations", iterations)
    }

    private fun runTest(block: () -> Unit) {
        val duration = measureTimeMillis {
            repeat(iterations) {
                repeat(strLength) {
                    block()
                }
            }
        }
        Console.info("duration", duration)
    }

    private fun `int numChars`() {
        runTest {
            val x = random.nextInt(numChars)
        }
    }

    private fun `int numChars List`() {
        val list = ('a'..'z').toList() + ' '
        runTest {
            val x = random.nextInt(numChars)
            val y = list[x]
        }
    }

    private fun `int numChars Array`() {
        val ary = (('a'..'z').toList() + ' ').toTypedArray()
        runTest {
            val x = random.nextInt(numChars)
            val y = ary[x]
        }
    }

    private fun `int numChars List StringBuffer`() {
        val list = ('a'..'z').toList() + ' '
        val sb = StringBuilder()
        runTest {
            val x = random.nextInt(numChars)
            val y = list[x]
            sb.append(y)
        }
    }

    private fun `int numChars List toString`() {
        val list = ('a'..'z').toList() + ' '
        val whence = Thread.currentThread().stackTrace[1]
        val duration = measureTimeMillis {
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
        val ary = (('a'..'z').toList() + ' ').toTypedArray()
        val whence = Thread.currentThread().stackTrace[1]
        val duration = measureTimeMillis {
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
        val list = ('a'..'z').toList() + ' '
        val whence = Thread.currentThread().stackTrace[1]
        val duration = measureTimeMillis {
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
            { `int numChars`() },
            { `int numChars List`() },
            { `int numChars Array`() },
            { `int numChars List StringBuffer`() },
            { `int numChars List toString`() },
            { `int numChars Array toString`() },
            { `nextBytes`() },
            { `nextBytes toString`() },
        )
        // val shuffled = methods.shuffled()
        methods.forEach {
            it()
            sleep(500L)
        }
        println()
    }
}

fun main() {
    val params = listOf(
        4 to 10_000_000,
        5 to 7_000_000,
        6 to 4_000_000,
        7 to 2_000_000,
        8 to 2_000_000,
        9 to 1_000_000,
    )
    params.forEach {
        val obj = RandomsTest(27, it.first, it.second * 10)
        obj.runTest()
    }
}