package org.incava.mmonkeys.mky.corpus.sc

class CorpusTraits(var words: List<String>) {
    fun characterCounts(): Map<Char, Int> {
        val byChar: MutableMap<Char, Int> = mutableMapOf()
        val numSpaces = words.size - 1
        byChar[' '] = numSpaces
        words.forEach { word ->
            word.forEach { ch -> byChar[ch] = (byChar[ch] ?: 0) + 1 }
        }
        return byChar
    }
}