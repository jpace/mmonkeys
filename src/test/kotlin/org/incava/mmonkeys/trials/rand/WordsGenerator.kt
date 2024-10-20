package org.incava.mmonkeys.trials.rand

import org.incava.rando.RandIntsFactory
import org.incava.rando.RndSlots

data class Words(val strings: List<String>, val totalKeyStrokes: Long)

data class WordsLongs(val strings: List<String>, val encoded: List<Long>, val totalKeyStrokes: Long)

class WordsGenerator(val slots: RndSlots, private val generator: StrLongRandSupplier) {
    private val intsFactory = RandIntsFactory()
    private val maxLength = 27 + 1 // "honorificabilitudinitatibus"

    fun doIt(block: (Int) -> Any?): WordsLongs {
        val slotIndices = intsFactory.nextInts2()
        val strings = mutableListOf<String>()
        val encoded = mutableListOf<Long>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to a space:
            val length = slots.slotValue(slotIndex)
            keystrokes += length
            if (length in 2..maxLength) {
                block(length)
                val word = block(length)
                if (word is String) {
                    strings += word
                } else if (word is Long) {
                    encoded += word
                }
            }
        }
        return WordsLongs(strings, encoded, keystrokes)
    }

    fun generate(): WordsLongs {
        return doIt { length ->
            val numChars = length - 1
            generator.doGet(numChars)
        }
    }

    fun generate(filterSupplier: () -> GenFilter): WordsLongs {
        return doIt { length ->
            val numChars = length - 1
            val filter = filterSupplier()
            generator.doGet(numChars, filter)
        }
    }

    fun generate2(filterSupplier: (Int) -> GenFilter): WordsLongs {
        return doIt { length ->
            val numChars = length - 1
            val filter = filterSupplier(numChars)
            generator.doGet(numChars, filter)
        }
    }
}