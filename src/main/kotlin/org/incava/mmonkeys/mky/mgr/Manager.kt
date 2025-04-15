package org.incava.mmonkeys.mky.mgr

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusStatsView
import org.incava.mmonkeys.words.Words
import java.io.File
import java.io.PrintStream

class Manager(val corpus: Corpus, outputInterval: Int = 1) : MonkeyMonitor {
    private var totalKeystrokes: Long = 0L
    private var count: Long = 0L
    private var matchCount = 0
    private val managerView: ManagerView
    private val statsView: CorpusStatsView
    private val perfView: SimPerfView
    val matchesByLength = mutableMapOf<Int, Int>()

    init {
        val simOut = PrintStream(File("/tmp/simulation.out"))
        val simMatchView = SimMatchView(corpus, outputInterval, simOut)
        managerView = ManagerView(simMatchView)
        val statsOut = PrintStream(File("/tmp/corpus-stats.out"))
        statsView = CorpusStatsView(corpus, 100, statsOut)
        val perfOut = PrintStream(File("/tmp/monkeys-stats.out"))
        perfView = SimPerfView(corpus, perfOut)
    }

    override fun update(monkey: Monkey, words: Words) {
        // this includes spaces
        totalKeystrokes += words.totalKeyStrokes
        // @todo - reintroduce the number of attempts (count of all words, not just matches):
        count++
        words.words.forEach { word ->
            ++matchCount
            managerView.addMatch(monkey, word.index, matchCount, totalKeystrokes)
            matchesByLength.merge(word.string.length, 1) { prev, _ -> prev + 1 }
        }
        if (words.hasMatch()) {
            statsView.update(matchCount, totalKeystrokes)
        }
        perfView.update(matchCount, totalKeystrokes)
    }

    override fun summarize() {
        Console.info("#keystrokes", totalKeystrokes)
        Console.info("count", count)
    }

    override fun attemptCount(): Long = count

    override fun matchCount(): Int = matchCount

    override fun keystrokesCount() = totalKeystrokes

    override fun matchesByLength(): Map<Int, Int> = matchesByLength
}
