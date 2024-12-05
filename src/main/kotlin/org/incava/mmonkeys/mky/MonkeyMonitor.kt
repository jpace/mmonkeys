package org.incava.mmonkeys.mky

import org.incava.mmonkeys.words.Words

interface MonkeyMonitor {
    fun update(monkey: Monkey, words: Words)

    fun summarize()

    fun attemptCount(): Long

    fun matchCount(): Int

    fun keystrokesCount(): Long
}

