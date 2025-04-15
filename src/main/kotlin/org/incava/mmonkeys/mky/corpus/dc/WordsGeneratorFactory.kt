package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.type.Chars
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory

object WordsGeneratorFactory {
    object Defaults {
        val slots = RandSlotsFactory.calcArray(Chars.NUM_ALL_CHARS, 128, 100_000)
        val indicesSupplier: (RandIntsFactory) -> IntArray = RandIntsFactory::nextInts2
        fun filterSupplier(corpus: DualCorpus): ((Int) -> LengthFilter) = { length ->
            corpus.stringsForLength(length)?.keys.let { LengthFilter(it) }
        }
    }

    fun createWithDefaults(corpus: DualCorpus): WordsGenerator {
        return WordsGenerator(corpus, Defaults.slots, Defaults.indicesSupplier, Defaults.filterSupplier(corpus))
    }
}