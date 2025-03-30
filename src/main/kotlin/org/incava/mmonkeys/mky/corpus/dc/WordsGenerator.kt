package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.words.Attempt
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

    fun attemptMatch(): Attempt {
        val slotIndices = indicesSupplier(intsFactory)
        val matches = LinkedHashMap<Long, Word>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to (through) a space:
            val toSpace = slots.slotValue(slotIndex)
            if (toSpace in minToSpace..maxToSpace) {
                val numChars = toSpace - 1
                val word = wordGenerator.findMatch(numChars)
                if (word != null) {
                    matches[keystrokes] = word
                }
            }
            keystrokes += toSpace
        }
        return Attempt(matches, keystrokes, slotIndices.size)
    }
}
