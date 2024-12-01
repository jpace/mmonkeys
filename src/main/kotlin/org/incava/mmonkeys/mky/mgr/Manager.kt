package org.incava.mmonkeys.mky.mgr

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.words.Words

class Manager(val corpus: Corpus, outputInterval: Int = 1) : MonkeyMonitor {
    var totalKeystrokes: Long = 0L
    private var count: Long = 0L
    private var matchCount = 0
    private val view = ManagerView(corpus, this, outputInterval)

    override fun add(monkey: Monkey, matchData: MatchData) {
        val words = if (matchData.index == null)
            Words(matchData.keystrokes.toLong() + 1, 1)
        else
            Words(matchData.keystrokes.toLong() + 1, matchData.index)
        // add 1 to account for the space after the match/mismatch
        totalKeystrokes += (matchData.keystrokes + 1)
        count++
        if (matchData.index != null) {
            view.addMatch(monkey, matchData.index)
            ++matchCount
        }
    }

    override fun notify(monkey: Monkey, words: Words) {
        // this includes spaces
        totalKeystrokes += words.totalKeyStrokes
        // @todo - reintroduce the number of attempts (count of all words, not just matches):
        count++
        words.words.forEach { word ->
            view.addMatch(monkey, word.index)
            ++matchCount
        }
    }

    override fun summarize() {
        Console.info("#keystrokes", totalKeystrokes)
        Console.info("count", count)
    }

    override fun attemptCount(): Long = count

    override fun matchCount(): Int = matchCount

    override fun keystrokesCount() = totalKeystrokes
}
