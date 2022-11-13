package org.incava.mmonkeys.testutil

import org.incava.time.DurationList

class InvokeTrials<T>(val block: () -> T) {
    val durations = DurationList()

    fun run(count: Long) {
        val trial = InvokeUnitTrial(block)
        trial.run(count)
        durations += trial.duration
    }
}

