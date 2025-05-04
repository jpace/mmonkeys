package org.incava.mmonkeys.mky

import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts

interface MonkeyMonitor {
    fun update(monkey: Monkey, attempt: Attempt)
    fun update(monkey: Monkey, attempts: Attempts)

    fun summarize()

    fun attemptCount(): Long

    fun matchCount(): Int

    fun keystrokesCount(): Long

    fun matchesByLength(): Map<Int, Int>
}

