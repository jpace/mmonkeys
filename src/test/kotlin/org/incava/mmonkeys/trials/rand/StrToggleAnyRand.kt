package org.incava.mmonkeys.trials.rand

import org.incava.rando.RndSlots

// RandEncoded, but StrRand if length > 13
class StrToggleAnyRand(slots: RndSlots) : StrLenRand(slots) {
    var overruns = 0L
    val littleGen = RandEncoded()
    val bigGen = StrRandFactory.create(StrRandFactory.calcArray, StrRandFactory.assemble) as StrLenRand

    override fun getString(length: Int): String {
        val encoded = littleGen.getEncoded(length)
        // no decoding; return encoded as Any
        // val result = encoded
        return ""
    }

    override fun get(): String {
        val len = randomLength()
        return if (len > 13) {
            ++overruns
            bigGen.getString(len)
        } else {
            getString(len)
        }
    }

    override fun get(filter: Int): String {
        val len = randomLength()
        return if (len > filter) {
            ""
        } else if (len > 13) {
            bigGen.getString(len)
        } else {
            getString(len)
        }
    }

    override fun doGet(length: Int): Any {
        return if (length > 13) {
            bigGen.getString(length)
        } else {
            littleGen.getEncoded(length)
        }
    }
}