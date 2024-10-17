package org.incava.mmonkeys.trials.rand

interface StrLongRandSupplier {
    object Constants {
        const val NUM_CHARS: Int = 26
    }

    fun get(): String

    fun get(filter: Int): String

    fun doGet(length: Int): Any

    fun doGet(length: Int, filter: GenFilter): Any?

    fun doGetString(length: Int, filter: GenFilter): String? {
        return null
    }
}