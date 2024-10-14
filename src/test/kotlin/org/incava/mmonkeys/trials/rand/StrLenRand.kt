package org.incava.mmonkeys.trials.rand

import org.incava.rando.RndSlots
import kotlin.random.Random

abstract class StrLenRand(private val slots: RndSlots) : StrRand() {
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

    override fun doGet(length: Int): Any = getString(length)

    override fun doGet(length: Int, filter: GenFilter): Any? {
        return doGet(length)
    }
}