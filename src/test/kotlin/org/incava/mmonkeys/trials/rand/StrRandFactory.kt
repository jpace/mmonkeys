package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.type.Chars
import org.incava.rando.RandSlotsFactory
import org.incava.rando.RndSlots

object StrRandFactory {
    val genArray = RandSlotsFactory::genArray
    val genList = RandSlotsFactory::genList
    val genMap = RandSlotsFactory::genMap

    val calcArray = RandSlotsFactory::calcArray
    val calcList = RandSlotsFactory::calcList
    val calcMap = RandSlotsFactory::calcMap

    val build = ::buildString
    val buffer = ::bufferString
    val assemble = ::assembleString

    fun create(numSlots: Int, slotsProvider: (Int, Int, Int) -> RndSlots, stringProvider: (Int, () -> Int) -> String): StrSupplier {
        val slots = slotsProvider(Chars.NUM_ALL_CHARS, numSlots, 10000)
        return object : StrLenRand(slots) {
            override fun getString(length: Int) = stringProvider(length) { Chars.randCharAz() }
        }
    }

    private fun buildString(length: Int, charProvider: () -> Int): String {
        val sb = StringBuilder()
        repeat(length) {
            val ch = charProvider()
            sb.append('a' + ch)
        }
        return sb.toString()
    }

    private fun bufferString(length: Int, charProvider: () -> Int): String {
        val sb = StringBuffer(length)
        repeat(length) {
            val n = charProvider()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    private fun assembleString(length: Int, charProvider: () -> Int): String {
        val bytes = ByteArray(length)
        repeat(length) { index ->
            val n = charProvider()
            bytes[index] = ('a' + n).toByte()
        }
        return String(bytes)
    }
}