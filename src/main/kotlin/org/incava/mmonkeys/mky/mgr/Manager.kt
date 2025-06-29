package org.incava.mmonkeys.mky.mgr

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusStatsView
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Word
import java.io.File
import java.io.PrintStream

data class ManagerStats(
    var totalKeystrokes: Long = 0L,
    var count: Long = 0L,
    var matchCount: Int = 0,
    var matchesByLength: MutableMap<Int, Int> = mutableMapOf(),
    var iterations: MutableList<Long> = mutableListOf<Long>(),
) {
    fun update(words: List<Word>, totalKeystrokes: Long) {
        // this includes spaces
        this.totalKeystrokes += totalKeystrokes
        this.iterations += totalKeystrokes
        this.count++
        words.forEach { this.matchesByLength.merge(it.string.length, 1) { prev, _ -> prev + 1 } }
        this.matchCount += words.size
    }
}

class Manager(val corpus: Corpus, outputInterval: Int = 1) : MonkeyMonitor {
    private val view: ManagerView
    val stats = ManagerStats()

    init {
        val simOut = PrintStream(File("/tmp/simulation.out"))
        val simMatchView = SimMatchView(corpus, outputInterval, simOut)
        val statsOut = PrintStream(File("/tmp/corpus-stats.out"))
        val statsView = CorpusStatsView(corpus, 100, statsOut)
        val perfOut = PrintStream(File("/tmp/monkeys-stats.out"))
        val perfView = SimPerfView(corpus, perfOut)
        view = ManagerView(simMatchView, statsView, perfView)
    }

    fun hasUnmatched(): Boolean {
        return corpus.matches.count() < corpus.numWords()
    }

    override fun update(monkey: Monkey, attempt: Attempt) {
        val words = if (attempt.word == null) emptyList() else listOf(attempt.word)
        update(monkey, words, attempt.totalKeyStrokes)
    }

    override fun update(monkey: Monkey, attempts: Attempts) {
        update(monkey, attempts.words, attempts.totalKeyStrokes)
    }

    private fun update(monkey: Monkey, words: List<Word>, totalKeystrokes: Long) {
        stats.update(words, totalKeystrokes)
        val indices = words.map { it.index }
        view.update(monkey, indices, stats.matchCount, totalKeystrokes)
    }

    override fun summarize() {
        Console.info("#keystrokes", stats.totalKeystrokes)
        Console.info("count", stats.count)
    }

    override fun attemptCount(): Long = stats.count

    override fun matchCount(): Int = stats.matchCount

    override fun keystrokesCount() = stats.totalKeystrokes

    override fun matchesByLength(): Map<Int, Int> = stats.matchesByLength

    fun iterations() = stats.iterations

    fun count() = stats.count
}
