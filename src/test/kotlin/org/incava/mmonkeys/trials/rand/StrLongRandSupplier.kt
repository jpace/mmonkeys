package org.incava.mmonkeys.trials.rand

interface StrLongRandSupplier {
    fun get(): String

    fun get(filter: Int): String

    fun doGet(length: Int): Any

    fun doGet(length: Int, filter: GenFilter): Any?
}