package org.incava.mmonkeys.mky

import org.incava.mmonkeys.words.Words

interface MonkeyMonitor {
    fun add(monkey: Monkey, matchData: MatchData)

    fun notify(monkey: Monkey, words: Words)

    fun summarize()

    fun attemptCount(): Long

    fun matchCount(): Int

    fun keystrokesCount(): Long
}

