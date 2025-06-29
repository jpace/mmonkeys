package org.incava.mmonkeys.mky.mgr

import org.incava.mmonkeys.corpus.CorpusStatsView
import org.incava.mmonkeys.mky.Monkey

class ManagerView(private val simMatchView: SimMatchView, private val statsView: CorpusStatsView, private val perfView: SimPerfView) {
    fun update(monkey: Monkey, matchingIndices: List<Int>, matchCount: Int, totalKeystrokes: Long) {
        simMatchView.update(monkey, matchingIndices, matchCount, totalKeystrokes)
        statsView.update(matchCount, totalKeystrokes)
        perfView.update(matchCount, totalKeystrokes)
    }
}
