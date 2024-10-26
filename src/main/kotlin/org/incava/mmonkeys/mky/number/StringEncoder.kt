package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.type.Chars
import java.math.BigInteger

object StringEncoder {
    // a .. z (no numbers yet)
    private const val NUM_CHARS = Chars.NUM_ALPHA_CHARS

    fun encodeToInt(string: String): Int {
        return encode(string, -1) { value, ch ->
            val num = ch - 'a'
            (value + 1) * NUM_CHARS + num
        }
    }

    fun encodeToLong(string: String): Long {
        return encode(string, -1L) { value, ch ->
            val num = ch - 'a'
            (value + 1L) * NUM_CHARS + num
        }
    }

    fun decode(number: Int): String {
        val mod: (Int) -> Int = { it % NUM_CHARS }
        val div: (Int) -> Int = { it / NUM_CHARS }
        val dec: (Int) -> Int = { it - 1 }
        return decode(number, 0, mod, div, dec)
    }

    fun decode(number: Long): String {
        val mod: (Long) -> Int = { (it % NUM_CHARS).toInt() }
        val div: (Long) -> Long = { it / NUM_CHARS }
        val dec: (Long) -> Long = { it - 1L }
        return decode(number, 0, mod, div, dec)
    }

    fun decode(number: BigInteger): String {
        val numCharsBig = NUM_CHARS.toBigInteger()
        val mod: (BigInteger) -> Int = { it.mod(numCharsBig).toInt() }
        val div: (BigInteger) -> BigInteger = { it.divide(numCharsBig) }
        val dec: (BigInteger) -> BigInteger = { it.minus(BigInteger.ONE) }
        return decode(number, BigInteger.ZERO, mod, div, dec)
    }

    private fun <T> encode(string: String, negativeOne: T, folding: (T, Char) -> T): T {
        return if (string.isBlank())
            negativeOne
        else
            string.toCharArray().fold(negativeOne, folding)
    }

    private fun <T : Comparable<T>> decode(number: T, zero: T, mod: (T) -> Int, div: (T) -> T, dec: (T) -> T): String {
        val sb = StringBuilder()
        var value = number
        val x = mod(value)
        value = div(value)
        sb.insert(0, 'a' + x)
        while (value > zero) {
            val y = mod(value)
            value = div(value)
            if (y == 0) {
                sb.insert(0, 'z')
                value = dec(value)
            } else {
                sb.insert(0, 'a' + y - 1)
            }
        }
        return sb.toString()
    }
}