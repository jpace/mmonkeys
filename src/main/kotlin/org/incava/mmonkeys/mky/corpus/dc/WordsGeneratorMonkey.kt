package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

class WordsGeneratorMonkey(id: Int, private val generator: WordsGenerator, typewriter: Typewriter, manager: Manager) : Monkey(id, typewriter, manager) {
    override fun findMatches(): Words {
        val words = generator.attemptMatch()
        recordWords(words)
        return words
    }
}