package org.incava.rando

import kotlin.random.Random

class RandIntsFactory {
    private val random = Random.Default

    fun nextInts() : IntArray {
        val ary = IntArray(9)
        val number = random.nextLong()
        repeat(9) {
            val value = (number shr (7 * it)) and 0x7f
            ary[it] = value.toInt()
        }
        return ary
    }
}