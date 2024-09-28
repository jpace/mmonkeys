package org.incava.rando

object BitsString {
    fun toBits(number: Int): String {
        val str = Integer.toBinaryString(number).padStart(Int.SIZE_BITS, '0')
        return splitify(str, '_')
    }

    fun toBits(number: Long): String {
        val (x, y) = longToInts(number)
        return toBits(x) + '_' + toBits(y)
    }

    fun splitify(str: String, ch: Char): String {
        val sb = StringBuilder()
        str.indices.forEach {
            if (it > 0 && it % 4 == 0) {
                sb.insert(0, ch)
            }
            sb.insert(0, str[str.length - 1 - it])
        }
        return sb.toString()
    }

    fun toHex(number: Int): String {
        val hexStr = Integer.toHexString(number)
        val hexPadded = hexStr.padStart(8, '0')
        return splitify(hexPadded, '_')
    }

    fun toHex(number: Long): String {
        val (x, y) = longToInts(number)
        return toHex(x) + '_' + toHex(y)
    }

    fun longToInts(number: Long) : Pair<Int, Int> {
        val x = (number shr Int.SIZE_BITS).toInt()
        val y = (number and Int.MAX_VALUE.toLong()).toInt()
        return x to y
    }
}