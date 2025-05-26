package org.incava.mmonkeys.trials.mky

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts

class CountingMonitor : MonkeyMonitor {
    var attempts = 0L
    var matches = 0
    val byKeystrokes = mutableMapOf<Long, Int>()

    override fun update(monkey: Monkey, attempt: Attempt) {
        if (attempt.hasMatch()) {
            ++matches
        }
        ++attempts
        byKeystrokes.compute(attempt.totalKeyStrokes) { _, value -> if (value == null) 1 else value + 1 }
    }

    override fun update(monkey: Monkey, attempts: Attempts) {
        matches += attempts.words.size
        ++this.attempts
    }

    override fun summarize() {
    }

    override fun attemptCount(): Long {
        return attempts
    }

    override fun matchCount(): Int {
        return matches
    }

    override fun keystrokesCount(): Long {
        return 0L
    }

    override fun matchesByLength(): Map<Int, Int> {
        return emptyMap()
    }
}

