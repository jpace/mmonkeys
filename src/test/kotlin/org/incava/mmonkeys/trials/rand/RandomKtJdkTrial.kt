package org.incava.mmonkeys.trials.rand

import org.incava.time.Durations
import java.lang.Thread.sleep
import java.time.Duration
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class RandomKtJdkTrial(private val numChars: Int, private val strLength: Int, val iterations: Long) {
    private val ktRandom = Random.Default
    private val jdkRandom = java.util.Random()

    private fun runTest(block: () -> Unit): Duration {
        val numInvokes = iterations * strLength
        return Durations.measureDuration {
            (0 until numInvokes).forEach { _ ->
                block()
            }
        }.second
    }

    private fun `kt rand next(ch)`(): Duration = runTest {
        val x = ktRandom.nextInt(numChars)
    }

    private fun `jdk rand next(ch)`(): Duration = runTest {
        val x = jdkRandom.nextInt(numChars)
    }

    private fun `kt rand next`(): Duration = runTest {
        val x = ktRandom.nextInt()
    }

    private fun `jdk rand next`(): Duration = runTest {
        val x = jdkRandom.nextInt()
    }

    private fun `thr local next`(): Duration {
        val t = ThreadLocalRandom.current()
        val numInvokes = iterations * strLength
        return Durations.measureDuration {
            (0 until numInvokes).forEach { _ ->
                val x = t.nextInt()
            }
        }.second
    }

    private fun `thr local next(ch)`(): Duration {
        val t = ThreadLocalRandom.current()
        val numInvokes = iterations * strLength
        return Durations.measureDuration {
            (0 until numInvokes).forEach { _ ->
                val x = t.nextInt(numChars)
            }
        }.second
    }

    private fun `no op`(): Duration {
        val x = 3
        return runTest {
            val y = x * 3
        }
    }

    fun run() {
        val runs = listOf(
            ::`kt rand next(ch)`,
            ::`jdk rand next(ch)`,
            ::`kt rand next`,
            ::`jdk rand next`,
            ::`thr local next`,
            ::`thr local next(ch)`,
            ::`no op`,
        ).map { it.name to it }
        val durations = mutableMapOf<String, MutableList<Duration>>()
        val trialInvokes = 5

        repeat(trialInvokes) { inv ->
            print("$trialInvokes - $inv")
            runs.shuffled().forEach { (name, function) ->
                print(" ... $name")
                val duration = function()
                durations.computeIfAbsent(name) { mutableListOf() }.also { it.add(duration) }
                sleep(250L)
            }
            println()
        }
        val table = TrialTable()
        table.show(durations.toSortedMap(), trialInvokes, iterations)
        println()
    }
}

fun main() {
    val factor = 10_000L
    val params = listOf(
        4 to 300,
        5 to 200,
        6 to 200,
        7 to 100,
        8 to 100,
        9 to 100,
    )
    params.forEach {
        val obj = RandomKtJdkTrial(27, it.first, it.second * factor)
        obj.run()
    }
}