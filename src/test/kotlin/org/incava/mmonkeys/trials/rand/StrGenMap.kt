package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RandGenMap
import org.incava.rando.RandInt
import kotlin.random.Random

class StrGenMap : StrLenRand() {
    private val lengthRand : RandInt = RandGenMap(NUM_CHARS + 1, 100, 10000)

    override fun randomLength() = lengthRand.nextInt()

    override fun randInt(limit: Int) = Random.nextInt(limit)

    override fun getString(length: Int): String = StringFactory.buildString(length, ::randCharAz)
}