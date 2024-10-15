package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.rando.RndSlots

class StrRandFiltered(slots: RndSlots) : StrLenRand(slots) {
    override fun getString(length: Int) : String {
        val bytes = ByteArray(length)
        repeat(length) { index ->
            val n = randCharAz()
            bytes[index] = ('a' + n).toByte()
        }
        return String(bytes)
    }

    override fun get(): String {
        val len = randomLength()
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = randomLength()
        return if (len > filter) "" else getString(len)
    }

    override fun doGet(length: Int): Any = getString(length)

    override fun doGet(length: Int, filter: GenFilter): Any? {
        val bytes = ByteArray(length)
        repeat(length) { index ->
            val n = randCharAz()
            val ch = 'a' + n
            val valid = filter.check(ch)
            if (!valid) {
                return null
            }
            bytes[index] = ch.toByte()
        }
        return String(bytes)
    }
}