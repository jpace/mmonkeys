package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusView
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.ViewType
import org.incava.mmonkeys.trials.ui.corpus.MatchView
import org.incava.mmonkeys.trials.ui.corpus.CorpusMonkeyTable
import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

// this used to take the factory to see the overhead in monkey initialization,
// but that's minimal, since it only happens once
class CorpusMonkeyRunner<T : Corpus>(
    private val corpus: T,
    private val monkey: Monkey,
    private val timeLimit: Duration,
    outputInterval: Int = 1,
) {
    private val maxAttempts = 100_000_000_000_000L
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()
    private val matches = mutableListOf<MatchData>()
    private val view = MatchView.createView(ViewType.TABLE, corpus, false)
    private val manager = Manager(corpus, outputInterval)

    init {
        val corpusView = CorpusView(corpus)
        corpusView.show()
        monkey.monitors += manager
    }

    fun run(): PerfResults {
        val durations = mutableListOf<Long>()
        val totalDuration = measureDuration {
            view.init()
            durations += measureTimeMillis {
                runMonkey()
            }
            val monkeyTable = CorpusMonkeyTable(7)
            monkeyTable.write(monkey)
        }
        return PerfResults(corpus, totalDuration.second, durations, iterations, matches)
    }

    private fun runMonkey() {
        Console.info("monkey.class", monkey.javaClass.name)
        while (corpus.hasUnmatched()) {
            var iteration = 0L
            var result: MatchData
            do {
                ++iteration
                result = monkey.check()

            } while (!result.isMatch && iteration < maxAttempts && !corpus.isEmpty())
            view.show(monkey, result)
            if (result.isMatch) {
                matches += result
            }
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