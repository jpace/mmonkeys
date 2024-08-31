package org.incava.ikdk.math

import org.incava.ikdk.io.Console.printf
import org.incava.mmonkeys.trials.base.InvokeTrial
import org.incava.time.Durations
import java.lang.Thread.sleep
import java.math.BigInteger

class MathsTrial {
    private val iterations = 100_000_000L

    private fun runIt(name: String, iterations: Long, powerFunction: () -> Any) {
        Maths.clear()
        try {
            val result = powerFunction()
            val invokeTrial = InvokeTrial(name, iterations, false, powerFunction)
            val duration = invokeTrial.run()
            val durStr = Durations.formatted(duration)
            printf("%-24.24s | %-,16d | %-10s", name, result, durStr)
        } catch (ex: Exception) {
            printf("%-24.24s | %-16s | %-10s", name, "exception", ex)
        }
        sleep(500)
    }

    fun runTrial(base: Int, exponent: Int, iterations: Long = this.iterations) {
        println("$base ** $exponent ($iterations)")

        val intTrials = mapOf(
            "Int Pow" to Maths::powerIntPow,
            "Int Repeat" to Maths::powerIntRepeat,
            "Int Recurse" to Maths::powerIntRecurse,
            "Int Cached" to Maths::powerIntCached,
            "Int Repeat Exact" to Maths::powerIntRepeatExact,
            "Int Recurse Exact" to Maths::powerIntRecurseExact,
            "Int Cached Exact" to Maths::powerIntCachedExact,
        )
        val longTrials = mapOf(
            "Long Pow" to Maths::powerLongPow,
            "Long Repeat" to Maths::powerLongRepeat,
            "Long Recurse" to Maths::powerLongRecurse,
            "Long Cached" to Maths::powerLongCached,
        )
        val bigIntTrials = mapOf(
            "Big Repeat" to Maths::powerBigRepeat,
            "Big Recurse" to Maths::powerBigRecurse,
            "Big Cached" to Maths::powerBigCached,
        )
        intTrials.forEach { (name, function) ->
            runIt(name, iterations) { function(base, exponent) }
        }
        println()
        val baseLong = base.toLong()
        longTrials.forEach { (name, function) ->
            runIt(name, iterations) { function(baseLong, exponent) }
        }
        println()
        val baseBigInt = BigInteger.valueOf(baseLong)
        bigIntTrials.forEach { (name, function) ->
            runIt(name, iterations) { function(baseBigInt, exponent) }
        }
        println()
    }
}

fun main() {
    val test = MathsTrial()
    test.runTrial(26, 6)
    test.runTrial(26, 4)
    test.runTrial(200, 3)
    test.runTrial(10, 10, 10_000_000)
    test.runTrial(1000, 10, 10_000_000)
    test.runTrial(10, 26, 10_000_000L)
}
