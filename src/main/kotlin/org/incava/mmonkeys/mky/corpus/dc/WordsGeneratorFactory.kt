package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.type.Chars
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory

object WordsGeneratorFactory {
    object Defaults {
        val slots = RandSlotsFactory.calcArray(Chars.NUM_ALL_CHARS, 128, 100_000)
        val indicesSupplier: (RandIntsFactory) -> IntArray = RandIntsFactory::nextInts2
    }

    fun createWithDefaults(corpus: DualCorpus): WordsGenerator {
        return WordsGenerator(corpus, Defaults.slots, Defaults.indicesSupplier) { length ->
            val candidates = corpus.stringsForLength(length)?.keys
            LengthFilter(candidates)
        }
    }
}