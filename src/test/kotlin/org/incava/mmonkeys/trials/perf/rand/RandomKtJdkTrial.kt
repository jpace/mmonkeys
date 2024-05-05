package org.incava.mmonkeys.trials.perf.rand

import org.incava.mmonkeys.testutil.InvokeUnitTrial
import org.incava.ikdk.io.Console
import java.lang.Thread.sleep
import kotlin.random.Random

class RandomKtJdkTrial(private val numChars: Int, private val strLength: Int, val iterations: Int) {
    private val ktRandom = Random.Default
    private val jdkRandom = java.util.Random()

    init {
        Console.info("strLength", strLength)
        Console.info("iterations", iterations)
    }

    private fun runTest(name: String, block: () -> Unit) {
        val numInvokes = iterations.toLong() * strLength
        val trial = InvokeUnitTrial(block)
        val duration = trial.run(numInvokes)
        Console.info(name, duration)
    }

    private fun nextIntNumKt() {
        runTest("nextIntNumKt") {
            val x = ktRandom.nextInt(numChars)
        }
    }

    private fun nextIntNumJdk() {
        runTest("nextIntNumJdk") {
            val x = jdkRandom.nextInt(numChars)
        }
    }

    private fun nextIntKt() {
        runTest("nextIntKt") {
            val x = ktRandom.nextInt()
        }
    }

    private fun nextIntJdk() {
        runTest("nextIntJdk") {
            val x = jdkRandom.nextInt()
        }
    }

    private fun noop() {
        val x = 3
        runTest("noop") {
            val y = x * 3
        }
    }

    fun runTest() {
        val methods = listOf(
            { nextIntNumKt() },
            { nextIntNumJdk() },
            { nextIntKt() },
            { nextIntJdk() },
            { noop() },
        )
        // val shuffled = methods.shuffled()
        methods.forEach {
            it()
            sleep(250L)
        }
        println()
    }
}

fun main() {
    val params = listOf(
        4 to 300,
        5 to 200,
        6 to 200,
        7 to 100,
        8 to 100,
        9 to 100,
    )
    params.forEach {
        val obj = RandomKtJdkTrial(27, it.first, it.second * 1_000_000)
        obj.runTest()
    }
}