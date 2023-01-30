package org.incava.mmonkeys.rand

import org.incava.mmonkeys.util.Maths
import kotlin.random.Random

open class Randough(val length: Int) {
    fun nextInt(digits: Int): Int {
        val max = Maths.power2(length, digits) * 2
        return Random.nextInt(max)
    }

    fun nextLong(digits: Int): Long {
        val max = Maths.power2(length.toLong(), digits) * 2
        return Random.nextLong(max)
    }
}