package org.incava.mmonkeys.trials.rand

import org.incava.rando.RndSlots
import kotlin.random.Random

abstract class StrLenRand(private val slots: RndSlots) : StrRand() {
    var filtered: Long = 0L

    fun randomLength() = slots.nextInt()

    override fun randInt(limit: Int) = Random.nextInt(limit)

    abstract fun getString(length: Int): String

    override fun get(): String {
        val len = randomLength()
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = randomLength()
        return if (len > filter) "" else getString(len)
    }
}