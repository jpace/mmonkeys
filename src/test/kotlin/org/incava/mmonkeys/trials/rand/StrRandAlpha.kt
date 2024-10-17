package org.incava.mmonkeys.trials.rand

abstract class StrRandAlpha : StrLongRandSupplier {
    object Constants {
        const val NUM_CHARS : Int = 26
    }

    fun randCharAzSpace() = randInt(Constants.NUM_CHARS + 1)

    fun randCharAz() = randInt(Constants.NUM_CHARS)

    abstract fun randInt(limit: Int) : Int
}