package org.incava.mmonkeys.trials.base

import org.incava.mesa.DoubleColumn
import org.incava.mesa.DurationColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.time.Durations
import java.time.Duration
import java.time.Duration.ofMillis


typealias MapSorter = (Map<String, List<Duration>>) -> Map<String, List<Duration>>

data class RunStats(
    val durations: List<Duration>,
    val numInvokes: Long,
    val overallAvg: Double,
) {
    fun average() = Durations.average(durations)
    fun totalDuration(): Duration = ofMillis(durations.sumOf { it.toMillis() })
    fun minimumDuration(): Duration = ofMillis(durations.minOf { it.toMillis() })
    fun maximumDuration(): Duration = ofMillis(durations.maxOf { it.toMillis() })
    fun variance(): Duration {
        return ofMillis(maximumDuration().toMillis() - minimumDuration().toMillis())
    }
    fun variancePct(): Double {
        return 100.0 * variance().toMillis() / average().toMillis()
    }
}

enum class SortType {
    BY_INSERTION,
    BY_NAME,
    BY_DURATION
}

class ProfileTable : Table(
    listOf(
        StringColumn("name", 32, true),
        LongColumn("iterations", 16),
        LongColumn("iters/ms", 10),
        DurationColumn("avg dur", 10),
        DurationColumn("total dur", 10),
        DoubleColumn("relative %", 10, 0),
        DurationColumn("variance", 10),
        DoubleColumn("variance %", 10, 1),
        DurationColumn("dur low", 10),
        DurationColumn("dur high", 10),
    ),
    breakInterval = 5
) {
    fun byInsertion(runs: Map<String, List<Duration>>) = runs

    fun byName(runs: Map<String, List<Duration>>) = runs.toSortedMap()

    fun byDuration(runs: Map<String, List<Duration>>): Map<String, List<Duration>> {
        // a bit of silliness because map.sortedBy { x } will include only the first element matching x
        val byAverage = runs.keys.sortedBy {
            val durs = runs.getOrElse(it) { listOf() }
            Durations.average(durs)
        }
        return byAverage.associateWith { runs.getOrElse(it) { listOf() } }
    }

    fun typeToFunction(type: SortType): MapSorter {
        return when (type) {
            SortType.BY_DURATION -> ::byDuration
            SortType.BY_NAME -> ::byName
            SortType.BY_INSERTION -> ::byInsertion
        }
    }

    fun show(runs: Map<String, List<Duration>>, trialInvokes: Int, numInvokes: Long, sortType: SortType) {
        val overallAvg = runs.values.map { Durations.average(it).toMillis() }.average()
        println("overallAvg: ${overallAvg.toLong()}")
        println("trialInvokes: $trialInvokes")
        writeHeader()
        val sorter = typeToFunction(sortType)
        val ordered = sorter(runs)
        ordered.forEach { (name, durations) ->
            val stats = RunStats(durations, numInvokes, overallAvg)
            showRun(name, stats, numInvokes, overallAvg)
        }
        writeBreak('=')
        val stats = RunStats(runs.values.map { Durations.average(it) }, numInvokes, overallAvg)
        showRun("average", stats, numInvokes, overallAvg)
    }

    fun showRun(name: String, stats: RunStats, numInvokes: Long, overallAvg: Double) {
        val avg = stats.average().toMillis()
        writeRow(
            name,
            numInvokes,
            if (avg > 0) numInvokes / avg else -1,
            stats.average(),
            stats.totalDuration(),
            100.0 * stats.average().toMillis() / overallAvg,
            stats.variance(),
            stats.variancePct(),
            stats.minimumDuration(),
            stats.maximumDuration()
        )
    }
}