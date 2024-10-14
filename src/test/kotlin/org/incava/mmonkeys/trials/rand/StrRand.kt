package org.incava.mmonkeys.trials.rand

abstract class StrRand : StrRandSupplier {
    object Constants {
        const val NUM_CHARS : Int = 26
    }

    fun randCharAzSpace() = randInt(Constants.NUM_CHARS + 1)

    fun randCharAz() = randInt(Constants.NUM_CHARS)

    abstract fun randInt(limit: Int) : Int

    override fun doGet(length: Int): Any = 1
}