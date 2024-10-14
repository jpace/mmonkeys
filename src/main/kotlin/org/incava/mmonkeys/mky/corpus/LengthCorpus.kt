package org.incava.mmonkeys.mky.corpus

// @todo - replace this with one of the MapCorpi
class LengthCorpus(words: List<String>) : Corpus(words) {
    val soughtByLength = words.groupByTo(mutableMapOf(), { it.length }, { it })

    fun matched(word: String, index: Int, length: Int) {
        super.remove(word)
        soughtByLength[length]?.removeAt(index)
        if (soughtByLength[length]?.isEmpty() == true) {
            soughtByLength.remove(length)
        }
    }

    fun matched(word: String, length: Int, forLength: MutableList<String>) {
        super.remove(word)
        val index = forLength.indexOf(word)
        forLength.removeAt(index)
        if (forLength.isEmpty()) {
            soughtByLength.remove(length)
        }
    }

    fun matched(word: String, length: Int, index: Int, forLength: MutableList<String>) {
        super.remove(word)
        forLength.removeAt(index)
        if (forLength.isEmpty()) {
            soughtByLength.remove(length)
        }
    }
}