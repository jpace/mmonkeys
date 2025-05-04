package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.DefaultTypewriter
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Words

open class DefaultMonkey(id: Int, private val strategy: TypeStrategy, typewriter: DefaultTypewriter) : Monkey(id, typewriter) {
    private val defaultTypewriter = typewriter

    private fun typeWord(): String {
        return strategy.typeWord()
    }

    override fun findMatches(): Words {
        val attempt = runAttempt()
        return Words(attempt.words)
    }

    fun runAttempt(): Attempt {
        val word = typeWord()
        val attempt = defaultTypewriter.toAttempt(word)
        processAttempt(attempt)
        return attempt
    }
}