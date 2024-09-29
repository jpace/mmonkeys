package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RandSlottedGenMap
import kotlin.random.Random

class StrGenMapBytes : StrLenRand() {
    private val lengthRand = RandSlottedGenMap(NUM_CHARS + 1, 100, 10000)
    override fun randomLength() = lengthRand.nextInt()
    override fun randInt(limit: Int) = Random.nextInt(limit)

    override fun getString(length: Int) = StringFactory.assembleString(length, ::randCharAz)
 }