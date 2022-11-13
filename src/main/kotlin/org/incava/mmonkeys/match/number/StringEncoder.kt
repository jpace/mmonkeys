package org.incava.mmonkeys.match.number

import java.lang.StringBuilder

object StringEncoder {
    fun encodeToInt(string: String) : Int {
        var value = 1
        string.toCharArray().forEach {
            value *= 26
            value += it - 'a'
        }
        return value
    }

    fun encodeToLong(string: String) : Long {
        var value = 1L
        string.toCharArray().forEach {
            value *= 26L
            value += it - 'a'
        }
        return value
    }

    fun encode(string: String) : Number {
        return if (string.length <= 6) encodeToInt(string) else encodeToLong(string)
    }

    fun decode(number: Int) : String {
        val sb = StringBuilder()
        var value = number
        while (true) {
            val x = number % 26
            value /= 26
            if (value == 0) {
                break
            } else {
                val ch = 'a' + x
                sb.insert(0, ch)
            }
        }
        return sb.toString()
    }

    fun decode(number: Long) : String {
        val sb = StringBuilder()
        var value = number
        while (true) {
            val x = (number % 26).toInt()
            value /= 26
            if (value == 0L) {
                break
            } else {
                val ch = 'a' + x
                sb.insert(0, ch)
            }
        }
        return sb.toString()
    }
}