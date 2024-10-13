package org.incava.mmonkeys.trials.rand

interface StrRandSupplier {
    object Constants {
        const val NUM_CHARS : Int = 26
    }

    fun get(): String

    fun get(filter: Int): String
}