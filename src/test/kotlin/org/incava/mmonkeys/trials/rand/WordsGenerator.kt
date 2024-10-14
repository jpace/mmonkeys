package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.rando.RandIntsFactory
import org.incava.rando.RndSlots

data class Words(val strings: List<String>, val encoded: List<Long>, val totalKeyStrokes: Long)

class WordsGenerator(val slots: RndSlots, private val generator: StrRandSupplier) {
    private val intsFactory = RandIntsFactory()
    private val maxLength = 27 + 1 // "honorificabilitudinitatibus"

    fun generate(): Words {
        val slotIndices = intsFactory.nextInts2()
        val strings = mutableListOf<String>()
        val encoded = mutableListOf<Long>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to a space:
            val length = slots.slotValue(slotIndex)
            keystrokes += length
            if (length in 2..maxLength) {
                val word = generateWord(length - 1)
                if (word is String) {
                    strings += word
                } else if (word is Long) {
                    encoded += word
                }
            }
        }
        return Words(strings, encoded, keystrokes)
    }

    fun generate(filterSupplier: () -> GenFilter): Words {
        val slotIndices = intsFactory.nextInts2()
        val strings = mutableListOf<String>()
        val encoded = mutableListOf<Long>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to a space:
            val length = slots.slotValue(slotIndex)
            keystrokes += length
            if (length in 2..maxLength) {
                val filter = filterSupplier()
                val word = generateWord(length - 1, filter)
                if (word is String) {
                    strings += word
                } else if (word is Long) {
                    encoded += word
                }
            }
        }
        return Words(strings, encoded, keystrokes)
    }

    fun generate2(filterSupplier: (Int) -> GenFilter): Words {
        val slotIndices = intsFactory.nextInts2()
        val strings = mutableListOf<String>()
        val encoded = mutableListOf<Long>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to a space:
            val length = slots.slotValue(slotIndex)
            keystrokes += length
            if (length in 2..maxLength) {
                val numChars = length - 1
                val filter = filterSupplier(numChars)
                val word = generateWord(numChars, filter)
                if (word is String) {
                    strings += word
                } else if (word is Long) {
                    encoded += word
                }
            }
        }
        return Words(strings, encoded, keystrokes)
    }

    fun generateWord(length: Int): Any? {
        return generator.doGet(length)
    }

    fun generateWord(length: Int, filter: GenFilter): Any? {
        return generator.doGet(length, filter)
    }
}