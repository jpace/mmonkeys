package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusSummaryTable
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.ViewType
import org.incava.mmonkeys.trials.ui.corpus.MatchView
import org.incava.mmonkeys.trials.ui.corpus.MonkeyTable
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words
import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

// this used to take the factory to see the overhead in monkey initialization,
// but that's minimal, since it only happens once
class MonkeyRunner(private val manager: Manager, private val monkey: Monkey, private val timeLimit: Duration) {
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()
    private var matchCount = 0
    private val view = MatchView.createView(ViewType.TABLE, manager.corpus, false)

    init {
        val table = CorpusSummaryTable(manager.corpus)
        table.show()
        Qlog.info("monkey", monkey)
    }

    fun run(): PerfResults {
        val durations = mutableListOf<Long>()
        val totalDuration = measureDuration {
            view.init()
            durations += measureTimeMillis {
                runMonkey()
            }
            val monkeyTable = MonkeyTable(7)
            monkeyTable.write(monkey, manager)
        }
        return PerfResults(manager.corpus, totalDuration.second, durations, iterations, matchCount)
    }

    private fun runMonkey() {
        while (manager.hasUnmatched()) {
            var iteration = 0L
            var result: Words
            do {
                ++iteration
                result = monkey.findMatches()
            } while (result.words.isEmpty() && manager.hasUnmatched())
            processAttempt(result.words, iteration)
            val now = ZonedDateTime.now()
            val elapsed = Duration.between(start, now)
            if (elapsed > timeLimit) {
                Console.info("stopping", elapsed)
                return
            }
        }
    }

    private fun processAttempt(words: List<Word>, iteration: Long) {
        words.forEach {
            view.show(monkey, manager, it.index)
        }
        matchCount += words.size
        iterations += iteration
//        Qlog.info("words", words)
//        Qlog.info("iteration", iteration)
//        Qlog.info("iterations", iterations.sum())
    }
}