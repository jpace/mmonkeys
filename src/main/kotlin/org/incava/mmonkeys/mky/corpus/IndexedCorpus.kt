package org.incava.mmonkeys.mky.corpus

class IndexedCorpus<T>(words: List<String>, supplier: (String) -> T) {
    private val delegate = SingleCorpus<T>()
    val elements = delegate.elements

    init {
        words.withIndex().forEach { word ->
            delegate.add(supplier(word.value), word.value.length, word.index)
        }
    }

    fun matched(item: T, length: Int): Int = delegate.matched(item, length)
}