package org.incava.rando

import kotlin.random.Random

abstract class RandInt {
    val random = Random.Default

    /**
     * Returns a pseudo-distribution around size.
     */
    abstract fun nextInt(): Int
}