package org.incava.mmonkeys.mky.mgr

import org.incava.mmonkeys.words.Word

data class ManagerStats(
    var totalKeystrokes: Long = 0L,
    var count: Long = 0L,
    var matchCount: Int = 0,
    var matchesByLength: MutableMap<Int, Int> = mutableMapOf(),
    var iterations: MutableList<Long> = mutableListOf(),
) {
    fun update(words: List<Word>, totalKeystrokes: Long, numAttempts: Int) {
        // this includes spaces
        this.totalKeystrokes += totalKeystrokes
        this.iterations += totalKeystrokes
        this.count += numAttempts
        words.forEach { this.matchesByLength.merge(it.string.length, 1) { prev, _ -> prev + 1 } }
        this.matchCount += words.size
    }

    fun updateMatch(word: Word, totalKeystrokes: Long) {
        this.totalKeystrokes += totalKeystrokes
        this.iterations += totalKeystrokes
        this.count++
        this.matchesByLength.merge(word.string.length, 1) { prev, _ -> prev + 1 }
        this.matchCount += 1
    }

    fun updateNoMatch(word: Word, totalKeystrokes: Long) {
        this.totalKeystrokes += totalKeystrokes
        this.iterations += totalKeystrokes
        this.count++
    }
}