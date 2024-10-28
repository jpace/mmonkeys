package org.incava.mmonkeys.mky.corpus

class MapCorpus(words: List<String>) : Corpus(words) {
    // HashMap consumes less memory than the default LinkedHashMap:
    val lengthToStringsToIndices: MutableMap<Int, MutableMap<String, MutableList<Int>>> = hashMapOf()
    val indexedCorpus: IndexedCorpus<String> = IndexedCorpus(words) { it }

    init {
        words.withIndex().forEach { word ->
            lengthToStringsToIndices
                .computeIfAbsent(word.value.length) { hashMapOf() }
                .computeIfAbsent(word.value) { mutableListOf() }.also { it.add(word.index) }
        }
    }

    fun matched(word: String, length: Int): Int {
        // @todo - the monkey already knows the forLength and index into that
        // this is the index into sought
        return indexedCorpus.matched(word, length).also { index -> removeAt(index) }
    }
}