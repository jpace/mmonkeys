package org.incava.mmonkeys.mky

interface MonkeyMonitor {
    fun add(monkey: Monkey, matchData: MatchData)

    fun summarize()

    fun attemptCount(): Long

    fun matchCount(): Int

    fun keystrokesCount(): Long
}

