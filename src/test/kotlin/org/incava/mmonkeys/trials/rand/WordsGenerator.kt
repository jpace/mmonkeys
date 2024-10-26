package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.type.Chars
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandIntsFactory
import org.incava.rando.RndSlots

interface Filtering {
    fun check(ch: Char): Boolean
}

abstract class LengthFiltering(val length: Int) : Filtering {
    abstract fun checkLength(): Boolean
}

class WordsGenerator(
    val slots: RndSlots,
    private val indicesSupplier: (RandIntsFactory) -> IntArray,
    private val filterSupplier: (Int) -> LengthFiltering
) {
    private val intsFactory = RandIntsFactory()
    private val maxLength = 27 + 1 // "honorificabilitudinitatibus"

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
                val filter = filterSupplier(numChars)
                if (filter.checkLength()) {
                    val word = getWord(numChars, filter)
                    if (word != null) {
                        strings += word
                    }
                }
            }
        }
        return Words(strings, keystrokes)
    }

    fun getWord(numChars: Int, filter: Filtering): String? {
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
