package org.incava.mmonkeys.trials.rand

interface StrSupplier {
    fun get(): String

    fun get(filter: Int): String
}