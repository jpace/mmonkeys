package org.incava.mmonkeys.mky.corpus.dc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts

class AttemptedTypewriter(val manager: MonkeyMonitor) : Typewriter() {
    fun addAttempt(monkey: Monkey, attempt: Attempt) {
        manager.update(monkey, attempt)
    }

    fun addAttempts(monkey: WordsGeneratorMonkey,  attempts: Attempts) {
        manager.update(monkey, attempts)
    }
}