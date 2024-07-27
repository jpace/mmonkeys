package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.corpus.CorpusView
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.ViewType
import org.incava.mmonkeys.trials.ui.corpus.CorpusMatchDataView
import org.incava.mmonkeys.trials.ui.corpus.CorpusMonkeyTable
import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

class CorpusMonkeyRunner<T : Corpus>(
    private val corpus: T,
    monkeyFactory: CorpusMonkeyFactory<T>,
    private val timeLimit: Duration,
    private val tickSize: Int = 20000,
) {
    val results: PerfResults
    private val maxAttempts = 100_000_000_000_000L
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()
    private val matches = mutableListOf<MatchData>()
    private val verbose = true
    private val view = CorpusMatchDataView.createView(ViewType.CONSOLE, corpus, false)

    init {
        val corpusView = CorpusView(corpus)
        corpusView.show()
        val durations = mutableListOf<Long>()
        val totalDuration = measureDuration {
            val monkey = monkeyFactory.createMonkey(corpus)
            view.init()
            durations += measureTimeMillis {
                runMonkey(monkey)
            }
            val monkeyTable = CorpusMonkeyTable(7)
            monkeyTable.write(monkey)
        }
        results = PerfResults(corpus, totalDuration.second, durations, iterations, matches)
    }

    private fun runMonkey(monkey: Monkey) {
        Console.info("monkey.class", monkey.javaClass.name)
        while (corpus.hasUnmatched()) {
            var iteration = 0L
            var result: MatchData
            do {
                ++iteration
                result = monkey.check()

            } while (!result.isMatch && iteration < maxAttempts && !corpus.isEmpty())
            if (verbose) {
                view.show(monkey, result)
            }
            if (result.isMatch) {
                matches += result
            }
            iterations += iteration
            if (iterations.size % tickSize == 0) {
                Console.info("iterations.#", iterations.size)
            }
            val now = ZonedDateTime.now()
            val elapsed = Duration.between(start, now)
            if (elapsed > timeLimit) {
                Console.info("stopping", elapsed)
                return
            }
        }
    }
}