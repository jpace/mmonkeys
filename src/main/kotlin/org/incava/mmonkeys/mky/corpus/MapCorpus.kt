package org.incava.mmonkeys.mky.corpus

class MapCorpus(words: List<String>) : Corpus(words) {
    val lengthToStringsToIndices: MutableMap<Int, MutableMap<String, MutableList<Int>>> = mutableMapOf()

    init {
        words.withIndex().forEach { word ->
            lengthToStringsToIndices
                .computeIfAbsent(word.value.length) { mutableMapOf() }
                .computeIfAbsent(word.value) { mutableListOf() }.also { it.add(word.index) }
        }
    }

    fun matched(word: String, length: Int): Int {
        remove(word)
        // @todo - the monkey already knows the forLength and index into that
        val forLength = lengthToStringsToIndices[length] ?: return -1
        val forWord = forLength[word] ?: return -1
        val index = forWord.removeAt(0)
        // this is the index into sought
        if (forWord.isEmpty()) {
            forLength.remove(word)
        }
        if (forLength.isEmpty()) {
            lengthToStringsToIndices.remove(length)
        }
        return index
    }
}