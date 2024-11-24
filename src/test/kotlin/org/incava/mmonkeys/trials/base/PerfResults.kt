package org.incava.mmonkeys.trials.base

import org.incava.mmonkeys.mky.corpus.Corpus
import java.time.Duration

data class PerfResults(
    val corpus: Corpus,
    val duration: Duration,
    val durations: MutableList<Long>,
    val iterations: MutableList<Long>,
    val matches: Int,
)