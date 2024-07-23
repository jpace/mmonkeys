package org.incava.mmonkeys.mky.corpus

class LengthCorpus(words: List<String>) : Corpus(words) {
    

    val soughtByLength = words.groupByTo(mutableMapOf(), { it.length }, { it })

    fun matched(word: String, index: Int, length: Int) {
        super.remove(word)
        soughtByLength[length]?.removeAt(index)
        if (soughtByLength[length]?.isEmpty() == true) {
            soughtByLength.remove(length)
        }
    }
}