package org.incava.mmonkeys.trials.rand

import kotlin.random.Random

object Chars {
    const val NUM_ALPHA_CHARS = 26
    const val NUM_ALL_CHARS = NUM_ALPHA_CHARS + 1

    fun randCharAzSpace() = Random.nextInt(NUM_ALL_CHARS)
    fun randCharAz() = Random.nextInt(NUM_ALPHA_CHARS)
}