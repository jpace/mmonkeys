package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.type.Chars
import org.incava.rando.RndSlots

object StrRandFactory {
    val build = ::buildString
    val assemble = ::assembleString

    fun create(numSlots: Int, slotsProvider: (Int, Int, Int) -> RndSlots, stringProvider: (Int, () -> Int) -> String): StrSupplier {
        val slots = slotsProvider(Chars.NUM_ALL_CHARS, numSlots, 10000)
        return object : StrLenRand(slots) {
            override fun getString(length: Int) = stringProvider(length) { Chars.randCharAz() }
        }
    }

    fun buildString(length: Int, charProvider: () -> Int): String {
        val sb = StringBuilder(length)
        repeat(length) {
            val ch = charProvider()
            sb.append('a' + ch)
        }
        return sb.toString()
    }

    fun assembleString(length: Int, charProvider: () -> Int): String {
        val bytes = ByteArray(length)
        repeat(length) { index ->
            val n = charProvider()
            bytes[index] = ('a' + n).toByte()
        }
        return String(bytes)
    }
}