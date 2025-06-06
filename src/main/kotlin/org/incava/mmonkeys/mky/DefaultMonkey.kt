package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.DefaultTypewriter
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Words

open class DefaultMonkey(id: Int, private val strategy: TypeStrategy, private val typewriter: DefaultTypewriter) : Monkey(id) {
    private fun typeWord(): String {
        return strategy.typeWord()
    }

    override fun findMatches(): Words {
        val attempt = runAttempt()
        return if (attempt.word == null) Words(emptyList()) else Words(listOf(attempt.word))
    }

    fun runAttempt(): Attempt {
        val word = typeWord()
        return typewriter.addAttempt(this, word)
    }
}