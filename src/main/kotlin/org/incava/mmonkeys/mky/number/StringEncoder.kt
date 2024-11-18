package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.type.Chars
import java.math.BigInteger

object StringEncoder {
    // a .. z (no numbers yet)
    private const val NUM_CHARS = Chars.NUM_ALPHA_CHARS

    fun encodeToInt(string: String): Int {
        return encode2(string, -1) { value, num ->
            (value + 1) * NUM_CHARS + num
        }
    }

    fun encodeToLong(string: String): Long {
        return encode2(string, -1L) { value: Long, num: Int ->
            (value + 1L) * NUM_CHARS + num
        }
    }

    fun encodeToBigInt(string: String): BigInteger {
        return encode2(string, BigInteger.valueOf(-1)) { value: BigInteger, num: Int ->
            (value + BigInteger.ONE) * BigInteger.valueOf(NUM_CHARS.toLong()) + BigInteger.valueOf(num.toLong())
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

    private fun <T> encode2(string: String, negativeOne: T, folding: (T, Int) -> T): T {
        return if (string.isBlank())
            negativeOne
        else
            string.toCharArray().map { it - 'a' }.fold(negativeOne, folding)
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