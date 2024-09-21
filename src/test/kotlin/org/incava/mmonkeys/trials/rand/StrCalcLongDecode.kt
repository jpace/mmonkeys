package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.number.StringEncoderV3
import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RandCalcList
import kotlin.random.Random

class StrCalcLongDecode : StrLenRand() {
    var overruns = 0L
    private val lengthRand = RandCalcList(NUM_CHARS + 1, 100, 10000)
    private val rangesEncoded = (1..13).associateWith { length ->
        val encoded = StringEncoderV3.encodeToLong("a".repeat(length))
        encoded to (encoded + 1) * 26
    }

    override fun randomLength() = lengthRand.nextInt()

    override fun randInt(limit: Int) = Random.nextInt(limit)

    fun randLong(limit: Long) = Random.nextLong(limit)

    override fun getString(length: Int): String {
        val rangeEncoded = rangesEncoded[length] ?: return "??!"
        val range = rangeEncoded.first * 25 + 26
        val randInRange = randLong(range)
        val encoded = rangeEncoded.first + randInRange
        return StringEncoderV3.decode(encoded)
    }

    override fun get(): String {
        val len = lengthRand.nextInt()
        if (len > 13) {
            ++overruns
        }
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = lengthRand.nextInt()
        if (len > 13) {
            ++overruns
        }
        return if (len > filter) { ++filtered; ""}  else getString(len)
    }
}