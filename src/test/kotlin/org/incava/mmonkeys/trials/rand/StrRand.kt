package org.incava.mmonkeys.trials.rand

abstract class StrRand {
    object Constants {
        const val NUM_CHARS : Int = 26
    }

    fun randCharAzSpace() = randInt(Constants.NUM_CHARS + 1)
    fun randCharAz() = randInt(Constants.NUM_CHARS)

    abstract fun randInt(limit: Int) : Int

    abstract fun get(): String

    abstract fun get(filter: Int): String
}