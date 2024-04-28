package org.incava.mmonkeys.rand

import org.incava.ikdk.math.Maths
import kotlin.random.Random

open class Randough(val length: Int) {
    fun nextInt(digits: Int): Int {
        val max = Maths.powerIntRepeat(length, digits) * 2
        return Random.nextInt(max)
    }

    fun nextLong(digits: Int): Long {
        val max = Maths.powerLongRepeat(length.toLong(), digits) * 2
        return Random.nextLong(max)
    }
}