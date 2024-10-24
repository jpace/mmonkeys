package org.incava.rando

import kotlin.math.abs
import kotlin.random.Random

class RandIntGenerator {
    private val random = Random.Default

    fun nextInts3(): List<Int> {
        val num1 = random.nextInt()
        val num2 = abs(num1)
        val num3 = if (num2 >= 100_000_000) num2 % 100_000_000 else num2
        return toNumbersList(num3)
    }

    fun nextInts4(): IntArray {
        val num1 = random.nextInt()
        val num2 = abs(num1)
        val num3 = if (num2 >= 100_000_000) num2 % 100_000_000 else num2
        return toNumberArray(num3)
    }

    private fun toNumberArray(num: Int): IntArray {
        var n = num
        val nums = IntArray(4)

        var index = 0
        while (n > 0) {
            val rem = n % 100
            nums[index] = rem
            n /= 100
            ++index
        }
        // "padded" with last digit 0
        return nums
    }

    private fun toNumbersList(num: Int): List<Int> {
        var n = num
        // we probably know the size ... log something?
        val nums = mutableListOf<Int>()
        while (n > 0) {
            val rem = n % 100
            nums += rem
            n /= 100
        }
        if (nums.size < 4) {
            nums += 0
        }
        return nums
    }
}