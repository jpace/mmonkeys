package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusSummaryTable
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.ViewType
import org.incava.mmonkeys.trials.ui.corpus.MatchView
import org.incava.mmonkeys.trials.ui.corpus.MonkeyTable
import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

// this used to take the factory to see the overhead in monkey initialization,
// but that's minimal, since it only happens once
class MonkeyRunner(private val manager: Manager, private val monkey: Monkey, private val timeLimit: Duration) {
    private val start = ZonedDateTime.now()
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
        return PerfResults(manager, totalDuration.second, durations)
    }

    private fun runMonkey() {
        Qlog.info("runner", this)
        repeat(10) {
            repeat(100) {
                monkey.type()
            }
            if (!manager.hasUnmatched()) {
                Qlog.info("manager.matchCount", manager.matchCount())
                return
            }
            val now = ZonedDateTime.now()
            val elapsed = Duration.between(start, now)
            if (elapsed > timeLimit) {
                Qlog.info("elapsed", elapsed)
                return
            }
        }
        Qlog.info("runner", this)
    }
}