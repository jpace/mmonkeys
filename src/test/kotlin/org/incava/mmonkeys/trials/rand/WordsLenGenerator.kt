package org.incava.mmonkeys.trials.rand

import org.incava.rando.RandIntsFactory
import org.incava.rando.RndSlots
import kotlin.random.Random

class WordsLenGenerator(val slots: RndSlots, private val filterSupplier: (Int) -> GenLenFilter) {
    private val intsFactory = RandIntsFactory()
    private val maxLength = 27 + 1 // "honorificabilitudinitatibus"

    fun generate(): Words {
        return generate { intsFactory.nextInts2() }
    }

    fun generate4(): Words {
        return generate { intsFactory.nextInts4() }
    }

    fun generate(supplier: () -> IntArray): Words {
        val slotIndices = supplier()
        val matches = mutableListOf<String>()
        var keystrokes = 0L
        slotIndices.forEach { slotIndex ->
            // number of keystrokes to a space:
            val length = slots.slotValue(slotIndex)
            keystrokes += length
            if (length in 2..maxLength) {
                val numChars = length - 1
                val filter = filterSupplier(numChars)
                if (filter.checkLength(numChars)) {
                    val word = getWord(numChars, filter)
                    if (word != null) {
                        matches += word
                    }
                }
            }
        }
        return Words(matches, keystrokes)
    }

    fun randCharAz() = Random.nextInt(StrRand.Constants.NUM_CHARS)

    fun getWord(length: Int, filter: GenFilter): String? {
        val bytes = ByteArray(length)
        repeat(length) { index ->
            val n = randCharAz()
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