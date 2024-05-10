package org.incava.mmonkeys.testutil

import org.incava.time.DurationList

class InvokeTrials<T>(private val block: () -> T) {
    val durations = DurationList()

    fun run(numInvokes: Long) {
        val trial = InvokeTrial(numInvokes, block)
        trial.run()
        durations += trial.duration
    }
}

