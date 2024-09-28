package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.math.pow

fun MutableMap<Int, Int>.add(number: Int) {
    this[number] = (this[number] ?: 0) + 1
}

class RandIntGeneratorTest {
    @Test
    fun nextInts4Dist() {
        multiRandDist2 { it.nextInts4() }
    }

    @Test
    fun nextInts5Dist() {
        multiRandDist2 { it.nextInts5() }
    }

    @Test
    fun nextInts5() {
        val gen = RandIntGenerator()
        val result = gen.nextInts5()
        Console.info("result", result.toList())
        // result.forEach(generated::add)
    }

    fun rotateRight(number: Int, places: Int) : Int {
        return number shr places or (number shl Int.SIZE_BITS - places)
    }

    private fun checkDistributions(numbers: Map<Int, Int>, until: Int, maxVariance: Double) {
        val expected = numbers.values.sum() / until
        Console.info("numbers.#", numbers.size)
        Console.info("expected", expected)
        (0 until until).forEach { number ->
            val count = numbers[number] ?: 0
            val diff = abs(count - expected)
            val pct = 100 * diff.toDouble() / expected
            val str = String.format("%3d - %,7d - %.1f", number, count, pct)
            assertTrue(pct < maxVariance, str)
        }
    }

    fun showNumberAndBits(n: Int) {
        val bitStr = BitsString.toBits(n)
        val hexStr = BitsString.toHex(n)
        System.out.printf("%,14d - %s - %s\n", n, hexStr, bitStr)
    }

    @Test
    fun mod() {
        val num = 320_317_420
        val bits1 = BitsString.toBits(num)
        println("$num - $bits1")
        val anded = num and 100_000_000
        println(BitsString.toBits(100_000_000))
        val num2 = num % 100_000_000
        println("$num (${BitsString.toBits(num)}) - $num2 (${BitsString.toBits(num2)})")
        var n = num2
        showNumberAndBits(100_000_000)
        showNumberAndBits(1)
        showNumberAndBits(2)
        showNumberAndBits(3)
        showNumberAndBits(4)
        showNumberAndBits(5)
        showNumberAndBits(6)
        showNumberAndBits(100)
        showNumberAndBits(63)
        showNumberAndBits(64)
        showNumberAndBits(127)
        showNumberAndBits(128)
        (0 until 31).forEach {
            println(it)
            showNumberAndBits(2.0.pow(it).toInt() - 1)
            showNumberAndBits(2.0.pow(it).toInt())
        }
        showNumberAndBits(Int.MAX_VALUE)
        repeat(10) {
            n = rotateRight(n, 1)
            showNumberAndBits(n)
        }
    }

    @Test
    fun longToBits() {
        val num = 0x44ff_88ff_0444_3117
        Console.info("num", num)
        val result = BitsString.toBits(num)
        Console.info("result", result)
        showNumberAndBits(Int.MAX_VALUE)
    }

    @Test
    fun andShiftInt() {
        val x = 0x44ff_88ff
        showNumberAndBits(x)
        val y = x and 0xffff
        showNumberAndBits(y)
    }

    @Test
    fun andShiftLong() {
        val x = 0x44ff_88ff_0444_3117
        Console.info("x", x)
        val y = x and 0xffff_ffff
        Console.info("y", y)
        Console.info("y.int", y.toInt())
        showNumberAndBits(y.toInt())
        val z = x and Int.MAX_VALUE.toLong()
        Console.info("z", z)
    }

    @Test
    fun multiRand2() {
        val gen = RandIntGenerator()
        repeat(10) {
            val nums = gen.nextInts()
            Console.info("nums", nums.toList())
        }

    }

    fun multiRandDist1(verbose: Boolean = false, blk: (RandIntGenerator) -> List<Int>) {
        val generated = mutableMapOf<Int, Int>()
        val gen = RandIntGenerator()
        repeat(10_000_000) {
            val nums = blk(gen)
            nums.forEach {
                generated.add(it)
            }
        }
        if (verbose) {
            generated.toSortedMap().forEach { (num, count) ->
                Console.info("$num", count)
            }
        }
        checkDistributions(generated, 100, 1.1)
    }

    fun multiRandDist2(verbose: Boolean = false, blk: (RandIntGenerator) -> IntArray) {
        val generated = mutableMapOf<Int, Int>()
        val gen = RandIntGenerator()
        repeat(10_000_000) {
            val nums = blk(gen)
            nums.forEach {
                generated.add(it)
            }
        }
        if (verbose) {
            generated.toSortedMap().forEach { (num, count) ->
                Console.info("$num", count)
            }
        }
        checkDistributions(generated, 100, 1.1)
    }

    @Test
    fun multiRandDist() {
        multiRandDist1 { it.nextInts() }
    }

    @Test
    fun multiRandDist2() {
        multiRandDist1 { it.nextInts2() }
    }

    @Test
    fun multiRandDist3() {
        // 0 is less frequent, because we take the absolute value (so why is it 300K instead of 200K?)
        multiRandDist1 { it.nextInts3() }
    }

    @Test
    fun toNumbers() {
        val gen = RandIntGenerator()
        repeat(100) {
            val x = 900_000 + (5000 * it)
            val nums = gen.toNumbersList(x)
            Console.info("$it $x", nums.toList())
        }
    }

    @Test
    fun pows() {
        (0 .. 6).forEach {
            val result = 100.0.pow(it).toInt()
            Console.info("result $it", result)
        }
    }

    @Test
    fun bits() {
        val x = 246
        val y = x and 10000
        Console.info("x", x)
        Console.info("y", y)
    }
}