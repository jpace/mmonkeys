package org.incava.mmonkeys.corpus

import org.incava.mmonkeys.rand.MapCharToCount
import org.incava.mmonkeys.rand.MutableMapCharToCount

class CorpusTraits(var words: List<String>) {
    fun characterCounts(): MapCharToCount {
        val byChar: MutableMapCharToCount = mutableMapOf()
        val numSpaces = words.size - 1
        byChar[' '] = numSpaces
        words.forEach { word ->
            word.forEach { ch -> byChar[ch] = (byChar[ch] ?: 0) + 1 }
        }
        return byChar
    }
}