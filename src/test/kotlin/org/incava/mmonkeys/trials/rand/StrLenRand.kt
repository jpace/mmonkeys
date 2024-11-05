package org.incava.mmonkeys.trials.rand

import org.incava.rando.RndSlots
import kotlin.random.Random

abstract class StrLenRand(private val slots: RndSlots) : StrSupplier {
    private fun randomLength() = slots.nextInt()

    abstract fun getString(length: Int): String

    override fun get(): String {
        val len = randomLength()
        return getString(len)
    }
}