package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.rando.RndSlots

// generates an encoded value (not a string), but fails if the synthesize length is > 13
class StrRandEncoded(slots: RndSlots) : StrLenRand(slots) {
    private val delegate = RandEncoded

    override fun getString(length: Int): String {
        val encoded = delegate.random(length)
        // same as StrCalcLongDecode, but no decode
        return ""
    }
}