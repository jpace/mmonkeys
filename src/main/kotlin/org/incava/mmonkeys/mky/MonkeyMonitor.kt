package org.incava.mmonkeys.mky

import org.incava.mmonkeys.words.Attempt

interface MonkeyMonitor {
    fun update(monkey: Monkey, words: Attempt)

    fun summarize()

    fun attemptCount(): Long

    fun matchCount(): Int

    fun keystrokesCount(): Long

    fun matchesByLength(): Map<Int, Int>
}

