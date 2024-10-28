package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandIntsFactory
import org.incava.rando.RndSlots

class WordsGeneratorV2(
    val dualCorpus: DualCorpus,
    val slots: RndSlots,
    private val indicesSupplier: (RandIntsFactory) -> IntArray,
    private val filterSupplier: (Int) -> LengthFiltering,
) {
    private val intsFactory = RandIntsFactory()
    private val maxLength = 27 + 1 // "honorificabilitudinitatibus"
    private val encodedGenerator = EncodedGenerator(dualCorpus)
    private val filteringGenerator = FilteringGenerator(dualCorpus)

    fun checkWord(numChars: Int, strings: MutableList<String>) {
        if (numChars <= 13) {
            // use long/encoded, convert back to string
            val word = encodedGenerator.getWord(numChars)
            if (word != null) {
                strings += word
            }
        } else {
            // use "legacy"
            val filter = filterSupplier(numChars)
            val word = filteringGenerator.getWord(numChars, filter)
            if (word != null) {
                strings += word
            }
        }
    }

    fun getWords(): Words {
        val slotIndices = indicesSupplier(intsFactory)
        val strings = mutableListOf<String>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to a space:
            val toSpace = slots.slotValue(slotIndex)
            keystrokes += toSpace
            if (toSpace in 2..maxLength) {
                val numChars = toSpace - 1
                checkWord(numChars, strings)
            }
        }
        return Words(strings, keystrokes)
    }
}
