package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.math.pow
import kotlin.random.Random

internal class MultiRandTest {
    private val random = Random.Default

    @Test
    fun multiRand() {
        val num = random.nextInt(100)
        Console.info("num", num)

        // this uses XorWow, which isn't thread safe:
        val rnd = Random(System.currentTimeMillis())
        val num2 = rnd.nextInt(100)
        Console.info("num2", num2)

        Console.info("Int.max", Int.MAX_VALUE)
        Console.info("pow", 10.0.pow(9).toLong())

        val num3 = random.nextInt()
        Console.info("num3", num3)

        // if num4 == Int.MIN_VALUE we in trouble, since the result is MIN_VALUE
        val num4 = abs(num3)
        Console.info("num4", num4)

        // 2147483647 rounded down to multiple of 100
        val num5 = num4.coerceAtMost(2147483600)
        Console.info("num5", num5)

        var n = num5

        val nums = mutableListOf<Int>()

        while (n > 0) {
            val rem = n % 100
            Console.info("rem", rem)
            nums += rem
            n /= 100
        }

        Console.info("nums", nums)
    }
}