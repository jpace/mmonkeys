package org.incava.mmonkeys.mky.mgr

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.mky.corpus.Corpus

class Manager(val corpus: Corpus, outputInterval: Int = 1) : MonkeyMonitor {
    var totalKeystrokes: Long = 0L
    private var count: Long = 0L
    private val matchKeystrokes = mutableMapOf<Int, Int>()
    private val view = ManagerView(corpus, this, outputInterval)

    override fun add(monkey: Monkey, matchData: MatchData) {
        // add 1 to account for the space after the match/mismatch
        totalKeystrokes += (matchData.keystrokes + 1)
        count++
        if (matchData.isMatch) {
            view.addMatch(monkey, matchData)
            matchKeystrokes.merge(matchData.keystrokes, 1) { prev, _ -> prev + 1 }
        }
    }

    override fun summarize() {
        Console.info("#keystrokes", totalKeystrokes)
        Console.info("count", count)
    }

    override fun attemptCount(): Long = count

    override fun matchCount(): Int = matchKeystrokes.values.sum()

    override fun keystrokesCount() = totalKeystrokes
}
