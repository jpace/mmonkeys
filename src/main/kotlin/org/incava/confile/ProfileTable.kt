package org.incava.confile

import org.incava.mesa.DoubleColumn
import org.incava.mesa.DurationColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.time.Durations
import java.time.Duration


typealias MapSorter = (Map<String, List<Duration>>) -> Map<String, List<Duration>>

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
    private fun byInsertion(runs: Map<String, List<Duration>>) = runs

    private fun byName(runs: Map<String, List<Duration>>) = runs.toSortedMap()

    private fun byDuration(runs: Map<String, List<Duration>>): Map<String, List<Duration>> {
        // a bit of silliness because map.sortedBy { x } will include only the first element matching x
        val byAverage = runs.keys.sortedBy {
            val durs = runs.getOrElse(it) { listOf() }
            Durations.average(durs)
        }
        return byAverage.associateWith { runs.getOrElse(it) { listOf() } }
    }

    private fun typeToFunction(type: SortType): MapSorter {
        return when (type) {
            SortType.BY_DURATION -> ::byDuration
            SortType.BY_NAME -> ::byName
            SortType.BY_INSERTION -> ::byInsertion
        }
    }

    fun show(runs: Map<String, List<Duration>>, trialInvokes: Int, numInvokes: Long, sortType: SortType) {
        val overallAvg = runs.values.map { Durations.average(it).toMillis() }.average()
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

    private fun showRun(name: String, stats: RunStats, numInvokes: Long, overallAvg: Double) {
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