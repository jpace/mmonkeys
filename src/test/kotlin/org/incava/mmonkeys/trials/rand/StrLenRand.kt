package org.incava.mmonkeys.trials.rand

import kotlin.random.Random

abstract class StrLenRand : StrRand() {
    var filtered: Long = 0L
    abstract fun randomLength() : Int

    override fun randInt(limit: Int) = Random.nextInt(limit)

    abstract fun getString(length: Int): String

    override fun get(): String {
        val len = randomLength()
        return getString(len)
    }

    override fun get(filter: Int): String {
        val len = randomLength()
        return if (len > filter) { ++filtered; ""} else getString(len)
    }
}