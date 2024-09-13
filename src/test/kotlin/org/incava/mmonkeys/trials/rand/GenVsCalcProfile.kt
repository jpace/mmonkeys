package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.Profiler
import org.incava.rando.RandCalcMap
import org.incava.rando.RandGenMap
import kotlin.math.abs

class GenVsCalcProfile(private val numInvokes: Long = 100_000_000L, private val trialInvokes: Int = 10) {
    private fun saveNumber(numbers: MutableMap<Int, Int>, number: Int) {
        numbers[number] = (numbers[number] ?: 0) + 1
    }

    fun profile() {
        val size = 27
        val calc = RandCalcMap(size, 10000)
        val gen = RandGenMap(size, 10000)

        calc.slots.forEach { (key, value) -> println("calc[$key] = $value") }
        gen.slots.forEach { (key, value) -> println("calc[$key] = $value") }

        val profiler = Profiler(numInvokes, trialInvokes)
        val calcNumbers = mutableMapOf<Int, Int>()
        profiler.add("calc " + calc.numSlots + " / " + calc.numIterations) {
            val result = calc.nextInt()
            saveNumber(calcNumbers, result)
        }
        val genNumbers = mutableMapOf<Int, Int>()
        profiler.add("gen " + gen.size) {
            val result = gen.nextInt()
            saveNumber(genNumbers, result)
        }

        profiler.runAll()
        profiler.showResults()
        val keys = calcNumbers.keys + genNumbers.keys
        keys.sorted().forEach { key ->
            val x = calcNumbers[key] ?: 0
            val y = genNumbers[key] ?: 0
            System.out.printf("%,12d | %,12d | %,12d | %d\n", key, x, y, abs(x - y))
        }
    }
}

fun main() {
    val obj = GenVsCalcProfile()
    obj.profile()
}