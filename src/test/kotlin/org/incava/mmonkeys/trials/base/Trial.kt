package org.incava.mmonkeys.trials.base

import org.incava.ikdk.io.Console
import org.incava.mesa.DurationColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.time.Durations

class Trial(private val trialInvokes: Int, vararg val options: InvokeTrial) {
    val verbose = true

    fun run() {
        val trialsList = options.toList()
        Console.info("trialInvokes", trialInvokes)
        repeat(trialInvokes) { invoke ->
            showProgress(invoke)
            trialsList.shuffled().forEach { it.run() }
        }
    }

    fun logSummarize() {
        options.forEach {
            Console.info("${it.name} - average", it.average())
            Console.info("${it.name} - durations", it.durations.map { duration -> Durations.formatted(duration) })
        }
    }

    private fun showProgress(invoke: Int) {
        if (verbose) {
            println("$invoke / $trialInvokes")
        }
    }

    fun tableSummarize() {
        val table = Table(
            listOf(
                StringColumn("name", 32, true),
                DurationColumn("duration", 8)
            )
        )
        table.writeHeader()
        options.forEach {
            table.writeRow(it.name, it.average())
        }
    }
}
