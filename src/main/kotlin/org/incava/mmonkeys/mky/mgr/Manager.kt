package org.incava.mmonkeys.mky.mgr

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Word

class Manager(val corpus: Corpus, private val view: ManagerView?) {
    val stats = ManagerStats()

    fun hasUnmatched(): Boolean {
        return corpus.matches.count() < corpus.numWords()
    }

    fun update(observed: Monkey, attempt: Attempt) {
        val words = if (attempt.word == null) emptyList() else listOf(attempt.word)
        update(observed, words, attempt.totalKeyStrokes, 1)
    }

    private fun update(monkey: Monkey, words: List<Word>, totalKeystrokes: Long, numAttempts: Int) {
        stats.update(words, totalKeystrokes, numAttempts)
        val indices = words.map { it.index }
        view?.update(monkey, indices, stats.matchCount, totalKeystrokes)
    }

    fun summarize() {
        Console.info("#keystrokes", stats.totalKeystrokes)
        Console.info("count", stats.count)
    }

    fun attemptCount(): Long = stats.count

    fun matchCount(): Int = stats.matchCount

    fun keystrokesCount() = stats.totalKeystrokes

    fun matchesByLength(): Map<Int, Int> = stats.matchesByLength

    fun iterations() = stats.iterations

    fun count() = stats.count
}
