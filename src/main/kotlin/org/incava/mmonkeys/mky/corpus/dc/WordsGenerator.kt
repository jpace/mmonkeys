package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Word
import org.incava.rando.RandIntsFactory
import org.incava.rando.RndSlots

class WordsGenerator(
    corpus: DualCorpus,
    private val slots: RndSlots,
    private val indicesSupplier: (RandIntsFactory) -> IntArray,
    filterSupplier: (Int) -> LengthFilter,
) {
    private val intsFactory = RandIntsFactory()
    private val maxToSpace = corpus.maxLength + 1
    private val minToSpace = 2
    private val wordGenerator = WordGenerator(corpus, filterSupplier)

    fun runAttempts(): Attempts {
        val slotIndices = indicesSupplier(intsFactory)
        val matches = mutableListOf<Word>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to (through) a space:
            val toSpace = slots.slotValue(slotIndex)
            if (toSpace in minToSpace..maxToSpace) {
                val numChars = toSpace - 1
                val word = wordGenerator.findMatch(numChars)
                if (word != null) {
                    matches += word
                }
            }
            keystrokes += toSpace
        }
        return if (matches.isEmpty()) {
            AttemptFactory.failed(keystrokes, slotIndices.size)
        } else {
            AttemptFactory.succeeded(matches, keystrokes, slotIndices.size)
        }
    }
}
