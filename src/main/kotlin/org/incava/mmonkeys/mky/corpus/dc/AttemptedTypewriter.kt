package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts

class AttemptedTypewriter(val manager: Manager) {
    fun addAttempt(monkey: Monkey, attempt: Attempt) {
        manager.update(monkey, attempt)
    }

    fun addAttempts(monkey: WordsGeneratorMonkey, attempts: Attempts) {
        manager.update(monkey, attempts)
    }
}