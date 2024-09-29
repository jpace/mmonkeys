package org.incava.rando

import kotlin.random.Random

object RandGenerator {
    fun generate(size: Int, numTrials: Int): List<Int> {
        val random = Random.Default
        // @todo - reimplement this to use a map of number -> count
        return (0 until numTrials).map {
            val num = (1 until Int.MAX_VALUE).find { random.nextInt(size) == 0 }
            num ?: throw RuntimeException("not generated")
        }.sorted()
    }
}
