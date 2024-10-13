package org.incava.mmonkeys.trials.rand

import org.incava.rando.RndSlots
import kotlin.random.Random

// generates an encoded value (not a string), but fails if the synthesize length is > 13
class StrRandEncoded(slots: RndSlots) : StrLenRand(slots) {
    var overruns = 0L
    val delegate = RandEncoded()

    override fun randInt(limit: Int) = Random.nextInt(limit)

    override fun getString(length: Int): String {
        val encoded = delegate.getEncoded(length)
        // same as StrCalcLongDecode, but no decode
        return ""
    }

    override fun get(): String {
        val len = randomLength()
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = randomLength()
        return if (len > filter) "" else getString(len)
    }
}