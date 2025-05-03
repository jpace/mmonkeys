package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Words

open class DefaultMonkey(id: Int, private val checker: WordChecker, private val strategy: TypeStrategy, typewriter: Typewriter) : Monkey(id, typewriter) {
    private fun typeWord(): String {
        return strategy.typeWord()
    }

    override fun findMatches(): Words {
        val attempt = runAttempt()
        return attempt.toWords()
    }

    fun runAttempt(): Attempt {
        val word = typeWord()
        val attempt = checker.processAttempt(word)
        processAttempt(attempt)
        return attempt
    }
}