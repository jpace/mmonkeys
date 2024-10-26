package org.incava.rando

import kotlin.random.Random

class RandIntsFactory {
    private val random = Random.Default

    fun nextInts1(): IntArray {
        // into 9 slices of 7 bits:
        val ary = IntArray(9)
        val number = random.nextLong()
        repeat(9) {
            val value = getBits(number, it)
            ary[it] = value
        }
        return ary
    }

    fun nextInts2(): IntArray {
        return create { number, ary ->
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
        }
    }

    fun nextInts3(): IntArray {
        return create { number, ary ->
            var index = 0
            repeat(7) {
                repeat(9) { i ->
                    val value = getBits(number, i)
                    ary[index++] = value
                }
            }
        }
    }

    fun nextInts4(): IntArray {
        val ary = IntArray(9 * 50)
        val number = random.nextLong()
        var index = 0
        repeat(50) {
            repeat(9) { i ->
                val value = getBits(number, i)
                ary[index++] = value
            }
        }
        return ary
    }

    private fun create(block: (Long, IntArray) -> Unit): IntArray {
        val ary = IntArray(63)
        val number = random.nextLong()
        block(number, ary)
        return ary
    }

    private fun getBits(number: Long, shift: Int): Int {
        val value = (number shr (7 * shift)) and 0x7f
        return value.toInt()
    }
}
