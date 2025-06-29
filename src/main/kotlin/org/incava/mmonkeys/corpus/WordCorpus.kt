package org.incava.mmonkeys.corpus

class WordCorpus(words: List<String>) : Corpus(words) {
    private val hashMap = LinkedHashMap<String, MutableList<Int>>()

    init {
        words.withIndex()
            .forEach { entry ->
                hashMap.computeIfAbsent(entry.value) { mutableListOf() }
                    .also { it.add(entry.index) }
            }
    }

    override fun findMatch(word: String): Int? {
        val indices = hashMap[word] ?: return null
        return indices.find { !matches.isMatched(it) }
    }
}
