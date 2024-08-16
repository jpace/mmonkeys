package org.incava.mmonkeys.mky.corpus

class MapCorpus(words: List<String>) : Corpus(words) {
    // HashMap<Int, HashMap<String, List<Int>>>

    // a LinkedHashMap -- we might want to optimize further as just HashMap
    val lengthToStringsToIndices: MutableMap<Int, MutableMap<String, MutableList<Int>>> = mutableMapOf()

    init {
        val encoded = mutableMapOf<String, Long>()
        words.withIndex().forEach { word ->
            lengthToStringsToIndices
                .computeIfAbsent(word.value.length) { mutableMapOf() }
                .computeIfAbsent(word.value) { mutableListOf() }.also { it.add(word.index) }
        }
    }

    fun matched(word: String): Int {
        remove(word)
        val forLength = lengthToStringsToIndices[word.length] ?: return -1
        val forWord = forLength[word] ?: return -1
        val index = forWord.removeAt(0)
        // this is the index into sought
        if (forWord.isEmpty()) {
            forLength.remove(word)
        }
        if (forLength.isEmpty()) {
            lengthToStringsToIndices.remove(word.length)
        }
        return index
    }
}