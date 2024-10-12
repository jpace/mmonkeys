package org.incava.confile

import org.incava.time.Durations
import java.time.Duration

class Simulation(val name: String, val function: () -> Unit) {
    val durations = mutableListOf<Duration>()

    fun average() = Durations.average(durations)
}