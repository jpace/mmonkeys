package org.incava.confile

import kotlin.math.pow
import kotlin.math.sqrt

class ProfileStats(val durations: Map<String, Long>) {
    fun average() = durations.values.average()
    fun mean() = average()
    fun variance() = durations.values.map { (it - mean()).pow(2) }.average()
    fun deviation() = sqrt(variance())
    fun filter(): Map<String, Long> {
        return durations.filter { (_, duration) ->
            (duration - mean()) / deviation() < 1.5
        }
    }
}