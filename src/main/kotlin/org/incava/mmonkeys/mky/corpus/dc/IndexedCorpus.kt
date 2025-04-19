package org.incava.mmonkeys.mky.corpus.dc

// indexed by length:
class IndexedCorpus<T>(val indexSupplier: (String) -> T) {
    constructor(words: List<String>, indexSupplier: (String) -> T) : this(indexSupplier) {
        words.withIndex().forEach { (index, word) -> add(word, index) }
    }

    // HashMap consumes less memory than the default LinkedHashMap:
    val elements: MutableMap<Int, MutableMap<T, MutableList<Int>>> = hashMapOf()

    fun add(word: String, wordIndex: Int) {
        val index = indexSupplier(word)
        elements.computeIfAbsent(word.length) { hashMapOf() }
            .computeIfAbsent(index) { mutableListOf() }.also { it.add(wordIndex) }
    }

    fun elementsForLength(length: Int) : Map<T, List<Int>>? {
        return elements[length]
    }

    fun setMatched(item: T, length: Int): Int {
        val forLength: MutableMap<T, MutableList<Int>> = elements[length] ?: return -1
        val forEncoded: MutableList<Int> = forLength[item] ?: return -1
        // this is the index into the corpus words
        val index = forEncoded.removeAt(0)
        if (forEncoded.isEmpty()) {
            forLength.remove(item)
        }
        if (forLength.isEmpty()) {
            elements.remove(length)
        }
        return index
    }
}