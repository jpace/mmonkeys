package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.corpus.dc.WordsGenerator
import org.incava.mmonkeys.mky.Monkey

class WordsGeneratorMonkey(id: Int, private val generator: WordsGenerator, private val typewriter: AttemptedTypewriter): Monkey(id) {
    override fun type() {
        val attempts = generator.runAttempts()
        typewriter.addAttempts(this, attempts)
    }
}