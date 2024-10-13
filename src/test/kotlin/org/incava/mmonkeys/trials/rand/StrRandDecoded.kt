package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.number.StringEncoderV3
import org.incava.rando.RndSlots
import kotlin.random.Random

class StrRandDecoded(slots: RndSlots) : StrLenRand(slots) {
    var overruns = 0L
    val delegate = RandEncoded()

    override fun randInt(limit: Int) = Random.nextInt(limit)

    override fun getString(length: Int): String {
        val encoded = delegate.getEncoded(length)
        return StringEncoderV3.decode(encoded)
    }

    override fun get(): String {
        val len = randomLength()
        if (len > 13) {
            ++overruns
            if (overruns % 100_000L == 0L) {
                Console.info("overruns", overruns)
            }
        }
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = randomLength()
        if (len > 13) {
            ++overruns
            if (overruns % 100_000L == 0L) {
                Console.info("overruns", overruns)
            }
        }
        return if (len > filter) "" else getString(len)
    }
}