package org.incava.confile

import org.incava.time.Durations
import java.time.Duration

data class ProfileSimulation(val function: () -> Unit, val durations: MutableList<Duration>) {
    fun getAverageTime() = Durations.average(durations).toMillis()
}