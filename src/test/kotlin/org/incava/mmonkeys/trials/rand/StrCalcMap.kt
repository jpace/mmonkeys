package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RandCalculated
import kotlin.random.Random

class StrCalcMap : StrRand() {
    private val lengthRand = RandCalculated(NUM_CHARS + 1, 10000)

    override fun randInt(limit: Int) = Random.nextInt(limit)

    private fun getString(length: Int) : String {
        val sb = StringBuilder()
        repeat(length) {
            val n = Random.nextInt(NUM_CHARS)
            sb.append('a' + n)
        }
        return sb.toString()
    }

    override fun get(): String {
        val len = lengthRand.nextRand()
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = lengthRand.nextRand()
        return if (len > filter) "" else getString(len)
    }
}