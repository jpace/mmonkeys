package org.incava.mmonkeys.corpus.impl

import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.corpus.dc.ItemsIndicesMap

class MapCorpus(words: List<String>) : WordCorpus(words) {
    val itemIndices: ItemsIndicesMap<String> = ItemsIndicesMap()

    init {
        words.withIndex().forEach { itemIndices.add(it.value.length, it.value, it.index) }
    }

    override fun setMatched(index: Int, word: String) {
        itemIndices.removeItem(word, word.length)
        matches.setMatched(index)
    }

    override fun findMatch(word: String): Int? {
        return itemIndices.itemsForLength(word.length)?.get(word)?.get(0)
    }
}