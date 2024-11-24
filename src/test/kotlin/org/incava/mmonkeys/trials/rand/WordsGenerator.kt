package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.DualCorpus
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

    fun createWithDefaults(corpus: DualCorpus) : WordsGenerator {
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

    private fun checkWord(numChars: Int, words: MutableList<Word>) {
        if (numChars <= RandEncoded.Constants.MAX_ENCODED_CHARS) {
            // use long/encoded, convert back to string
            val word = encodedGenerator.getWord(numChars)
            if (word != null) {
                words += word
            }
        } else {
            // use "legacy"
            val filter = filterSupplier(numChars)
            val word = filteringGenerator.getWord(numChars, filter)
            if (word != null) {
                words += word
            }
        }
    }

    fun getWords(): Words {
        val slotIndices = indicesSupplier(intsFactory)
        val matches = mutableListOf<Word>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to (through) a space:
            val toSpace = slots.slotValue(slotIndex)
            keystrokes += toSpace
            if (toSpace in minToSpace..maxToSpace) {
                val numChars = toSpace - 1
                checkWord(numChars, matches)
            }
        }
        return Words(matches, keystrokes)
    }
}
