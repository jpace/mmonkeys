package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Word
import org.incava.rando.RandIntsFactory
import org.incava.rando.RndSlots

class WordsGenerator(
    val corpus: DualCorpus,
    private val slots: RndSlots,
    private val indicesSupplier: (RandIntsFactory) -> IntArray,
    filterSupplier: (Int) -> LengthFilter,
) {
    private val intsFactory = RandIntsFactory()
    private val validRange = 2..corpus.maxLength + 1
    private val encodedGenerator = EncodedGenerator()
    private val filteringGenerator = FilteringGenerator(filterSupplier)

    fun runAttempts(): Attempts {
        val slotIndices = indicesSupplier(intsFactory)
        val matches = mutableListOf<Word>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to (through) a space:
            val toSpace = slots.slotValue(slotIndex)
            if (toSpace in validRange) {
                val numChars = toSpace - 1
                val word = runWord(numChars)
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

    fun runWord(numChars: Int): Word? {
        return if (numChars <= RandEncoded.Constants.MAX_ENCODED_CHARS) {
            // use long/encoded, convert back to string
            val encoded = encodedGenerator.getRandomEncoded(numChars)
            val forLength = corpus.longsForLength(numChars) ?: return null
            val match = forLength[encoded]
            return if (match.isNullOrEmpty()) {
                null
            } else {
                val string = StringEncoder.decode(encoded)
                val index = corpus.setMatched(encoded, numChars)
                return Word(string, index)
            }
        } else {
            // use "legacy"
            val string = filteringGenerator.getRandomString(numChars)
            if (string == null)
                null
            else {
                val index = corpus.setMatched(string, numChars)
                Word(string, index)
            }
        }
    }
}
