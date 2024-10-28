package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.type.Chars
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandIntsFactory
import org.incava.rando.RndSlots

class WordsGeneratorV2(
    val mapCorpus: MapCorpus,
    numberedCorpus: NumberedCorpus,
    val slots: RndSlots,
    private val indicesSupplier: (RandIntsFactory) -> IntArray,
    private val filterSupplier: (Int) -> LengthFiltering,
) {

    private val intsFactory = RandIntsFactory()
    private val maxLength = 27 + 1 // "honorificabilitudinitatibus"
    private val encodedGenerator = EncodedGenerator(numberedCorpus)

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
            if (filter.checkLength()) {
                val word = getWord(numChars, filter)
                if (word != null) {
                    mapCorpus.match(word)
                    strings += word
                }
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

    private fun getWord(numChars: Int, filter: Filtering): String? {
        val bytes = ByteArray(numChars)
        repeat(numChars) { index ->
            val n = Chars.randCharAz()
            val ch = 'a' + n
            val valid = filter.check(ch)
            if (!valid) {
                return null
            }
            bytes[index] = ch.toByte()
        }
        return String(bytes)
    }
}
