package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import org.incava.rando.RandCalcMap
import kotlin.random.Random

class StrCalcMap : StrRand() {
    private val lengthRand = RandCalcMap(NUM_CHARS + 1, 10000)

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
        val len = lengthRand.nextInt()
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = lengthRand.nextInt()
        return if (len > filter) "" else getString(len)
    }
}