package org.incava.mmonkeys.corpus.impl

import org.incava.mmonkeys.corpus.WordCorpus

class LinkedHashMapCorpus(words: List<String>) : WordCorpus(words) {
    private val hashMap = LinkedHashMap<String, MutableList<Int>>()

    init {
        words.withIndex()
            .forEach { entry -> hashMap.computeIfAbsent(entry.value) { mutableListOf() }.also { it.add(entry.index) } }
    }

    override fun findMatch(word: String): Int? {
        val indices = hashMap[word] ?: return null
        return indices.find { !matches.isMatched(it) }
    }
}