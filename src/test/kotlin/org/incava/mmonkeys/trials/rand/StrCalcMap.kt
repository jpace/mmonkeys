package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RandSlottedCalcMap
import kotlin.random.Random

class StrCalcMap : StrLenRand() {
    private val lengthRand = RandSlottedCalcMap(NUM_CHARS + 1, 100, 10000)

    override fun randInt(limit: Int) = Random.nextInt(limit)

    override fun randomLength() = lengthRand.nextInt()

    override fun getString(length: Int) = StringFactory.buildString(length, ::randCharAz)
}