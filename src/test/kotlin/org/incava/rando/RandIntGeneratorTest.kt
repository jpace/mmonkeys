package org.incava.rando

import org.incava.ikdk.io.Console
import org.incava.rando.DistributionAssert.assertVariance
import org.junit.jupiter.api.Test

fun MutableMap<Int, Int>.add(number: Int) {
    this[number] = (this[number] ?: 0) + 1
}

class RandIntGeneratorTest {
    @Test
    fun multiRandDist() {
        multiRandDist { it.nextInts() }
    }

    @Test
    fun multiRandDist2() {
        multiRandDist { it.nextInts2() }
    }

    @Test
    fun multiRandDist3() {
        multiRandDist { it.nextInts3() }
    }

    @Test
    fun nextInts4Dist() {
        multiRandDist { it.nextInts4().toList() }
    }

    private fun multiRandDist(verbose: Boolean = false, blk: (RandIntGenerator) -> List<Int>) {
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
        assertVariance(generated, 100, 1.2)
    }

    @Test
    fun toNumbers() {
        val gen = RandIntGenerator()
        repeat(100) {
            val x = 900_000 + (113 * it)
            val nums = gen.toNumbersList(x)
            Console.info("$it $x", nums.toList())
        }
    }
}