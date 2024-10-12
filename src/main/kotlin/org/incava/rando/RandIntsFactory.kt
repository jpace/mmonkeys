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

    fun nextInts2() : IntArray {
        val ary = IntArray(63)
        val number = random.nextLong()
        var index = 0
        repeat(9) { i ->
            val value = (number shr (7 * i)) and 0x7f
            ary[index++] = value.toInt()
            var v = value
            repeat(6) {
                v = v shr 1 or (v and 0x01 shl 6)
                ary[index++] = v.toInt()
            }
        }
        return ary
    }
}