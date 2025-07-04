package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.corpus.dc.DualCorpus
import org.incava.mmonkeys.corpus.dc.WordsGenerator
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.type.Chars
import org.incava.rando.RandSlotsFactory

class WordsGeneratorMonkeyFactory(val manager: Manager, val corpus: DualCorpus) {
    var id = 1
    val slots = RandSlotsFactory.calcArray(Chars.NUM_ALL_CHARS, 128, 100_000)

    fun createMonkey(): WordsGeneratorMonkey {
        val generator = WordsGenerator(corpus, slots)
        val typewriter = AttemptedTypewriter(manager)
        return WordsGeneratorMonkey(id++, generator, typewriter)
    }
}