package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.type.Typewriter
import kotlin.random.Random

class WeightedTypewriter(val words: List<String>) : Typewriter() {
    private val slots: Map<Char, Double>

    init {
        val byChar: MutableMap<Char, Int> = mutableMapOf()
        val numSpaces = words.size - 1
        byChar[' '] = numSpaces
        val numChars = words.sumOf { it.length } + numSpaces
        words.forEach { word ->
            word.forEach { ch -> byChar[ch] = (byChar[ch] ?: 0) + 1 }
        }
        var pct = 0.0
        slots = byChar.toSortedMap().map { (ch, count) ->
            val chPct = pct + 100.0 * count / numChars
            pct = chPct
            ch to chPct
        }.toMap()
    }

    override fun nextCharacter(): Char {
        val num = Random.Default.nextDouble(100.0)
        return slots.keys.first { num < slots.getValue(it) }
    }
}