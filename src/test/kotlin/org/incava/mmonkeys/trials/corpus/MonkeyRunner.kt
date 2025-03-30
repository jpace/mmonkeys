package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusSummaryTable
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.ViewType
import org.incava.mmonkeys.trials.ui.corpus.MatchView
import org.incava.mmonkeys.trials.ui.corpus.MonkeyTable
import org.incava.mmonkeys.words.Words
import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

// this used to take the factory to see the overhead in monkey initialization,
// but that's minimal, since it only happens once
class MonkeyRunner<T : Corpus>(
    private val corpus: T,
    private val monkey: Monkey,
    private val timeLimit: Duration,
    outputInterval: Int = 1,
) {
    private val maxAttempts = 100_000_000_000_000L
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()
    private var matchCount = 0
    private val view = MatchView.createView(ViewType.TABLE, corpus, false)
    private val manager = Manager(corpus, outputInterval)

    init {
        val table = CorpusSummaryTable(corpus)
        table.show()
        monkey.manager = manager
    }

    fun run(): PerfResults {
        val durations = mutableListOf<Long>()
        val totalDuration = measureDuration {
            view.init()
            durations += measureTimeMillis {
                runMonkey()
            }
            val monkeyTable = MonkeyTable(7)
            monkeyTable.write(monkey)
        }
        return PerfResults(corpus, totalDuration.second, durations, iterations, matchCount)
    }

    private fun runMonkey() {
        Console.info("monkey.class", monkey.javaClass.name)
        while (corpus.hasUnmatched()) {
            var iteration = 0L
            var result: Words
            do {
                ++iteration
                result = monkey.findMatches()

            } while (!result.hasMatch() && iteration < maxAttempts && corpus.hasUnmatched())
            result.words.forEach { view.show(monkey, it.index) }
            matchCount += result.words.size
            iterations += iteration
            val now = ZonedDateTime.now()
            val elapsed = Duration.between(start, now)
            if (elapsed > timeLimit) {
                Console.info("stopping", elapsed)
                return
            }
        }
    }
}