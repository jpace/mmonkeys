package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.type.Chars
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory
import org.incava.rando.RndSlots

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

class WordsGenerator(
    corpus: DualCorpus,
    private val slots: RndSlots,
    private val indicesSupplier: (RandIntsFactory) -> IntArray,
    private val filterSupplier: (Int) -> LengthFilter,
) {
    private val intsFactory = RandIntsFactory()
    private val encodedGenerator = EncodedGenerator(corpus)
    private val filteringGenerator = FilteringGenerator(corpus)
    private val maxToSpace = corpus.maxLength + 1
    private val minToSpace = 2

    private fun findMatch(numChars: Int, words: MutableList<Word>) {
        findMatch(numChars)?.also { word ->
            words += word
        }
    }

    private fun findMatch(numChars: Int): Word? {
        if (numChars <= RandEncoded.Constants.MAX_ENCODED_CHARS) {
            // use long/encoded, convert back to string
            return encodedGenerator.getWord(numChars)
        } else {
            // use "legacy"
            val filter = filterSupplier(numChars)
            return filteringGenerator.getWord(numChars, filter)
        }
    }

    fun findMatches(): Words {
        val slotIndices = indicesSupplier(intsFactory)
        val matches = mutableListOf<Word>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to (through) a space:
            val toSpace = slots.slotValue(slotIndex)
            keystrokes += toSpace
            if (toSpace in minToSpace..maxToSpace) {
                val numChars = toSpace - 1
                findMatch(numChars, matches)
            }
        }
        return Words(matches, keystrokes, slotIndices.size)
    }
}
