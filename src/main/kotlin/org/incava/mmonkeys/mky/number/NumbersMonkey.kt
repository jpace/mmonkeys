package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.mky.corpus.dc.AttemptedTypewriter
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.rand.RandomFactory
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandInt

class NumbersMonkey(id: Int, private val checker: NumbersChecker, private val typewriter: AttemptedTypewriter, manager: MonkeyMonitor) : Monkey(id, manager) {
    val rand: RandInt = RandomFactory.getCalculated(Keys.fullList().size)

    override fun findMatches(): Words {
        val attempt = runAttempt()
        val word = attempt.word
        return if (word == null) Words(emptyList()) else Words(listOf(word))
    }

    fun runAttempt(): Attempt {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string, 8 means we had 7 (hypothetical) characters,
        // and so on and so forth.
        val length = rand.nextInt()
        val numChars = length - 1
        // if null, we must be called with the wrong (> 13) length:
        return if (checker.hasForLength(numChars)) {
            val encoded = RandEncoded.random(numChars)
            checker.toAttempt(numChars, encoded)
        } else {
            AttemptFactory.failed(length + 1)
        // }.also { typewriter.addAttempt(it) }
        }.also { processAttempt(it) }
    }
}