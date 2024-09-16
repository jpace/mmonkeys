package org.incava.mmonkeys.mky.corpus

class HashMapCorpus(words: List<String>) : Corpus(words) {
    // HashMap<Int, HashMap<String, List<Int>>>

    // a LinkedHashMap -- we might want to optimize further as just HashMap
    val lengthToStringsToIndices: MutableMap<Int, MutableMap<String, MutableList<Int>>> = HashMap()

    init {
        words.withIndex().forEach { word ->
            lengthToStringsToIndices
                .computeIfAbsent(word.value.length) { HashMap() }
                .computeIfAbsent(word.value) { mutableListOf() }.also { it.add(word.index) }
        }
    }

    fun matched(word: String): Int {
        remove(word)
        // @todo - the monkey already knows the forLength and index into that
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