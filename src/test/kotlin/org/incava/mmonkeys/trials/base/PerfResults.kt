package org.incava.mmonkeys.trials.base

import org.incava.mmonkeys.mky.mgr.Manager
import java.time.Duration

data class PerfResults(
    val manager: Manager,
    val duration: Duration,
    val durations: MutableList<Long>
)