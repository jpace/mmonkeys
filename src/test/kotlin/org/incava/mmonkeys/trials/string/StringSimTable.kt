package org.incava.mmonkeys.trials.string

import org.incava.mesa.IntStringColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import java.time.Duration
import kotlin.math.max

class StringSimTable(private val word: String) : Table(
    listOf(
        StringColumn("word", 7),
        IntStringColumn("trial", 5),
        StringColumn("type", 12, leftJustified = true),
        LongColumn("iterations", 15),
        LongColumn("duration", 15),
        LongColumn("iters/sec", 15),
    )
) {
    init {
        writeHeader()
        writeBreak('=')
    }

    private fun writeTrialAverage(trial: StringSimulationRunner) {
        val iterations = trial.results.average().toLong()
        val durations = trial.durations.average().toMillis()
        val throughput = 1000L * iterations / max(1, durations)
        writeRow(word, "avg", trial.name, iterations, durations, throughput)
    }

    fun summarize(trials: List<StringSimulationRunner>) {
        writeBreak('=')
        trials.sortedBy { it.name }.forEach { writeTrialAverage(it) }
        writeBreak('=')
    }

    fun addResults(type: String, num: Int, iterations: Long, duration: Long) {
        val rate = 1000L * iterations / max(1, duration)
        writeRow(word, num, type, iterations, duration, rate)
    }

    fun addTrials(num: Int, trials: Map<String, Pair<Long, Duration>>) {
        trials.toSortedMap().forEach { (name, result) ->
            addResults(name, num, result.first, result.second.toMillis())
        }
    }
}
