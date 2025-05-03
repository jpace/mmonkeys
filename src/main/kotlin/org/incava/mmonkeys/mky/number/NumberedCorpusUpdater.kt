package org.incava.mmonkeys.mky.number

class NumberedCorpusUpdater(val corpus: NumberedCorpus) {
    fun numberMatched(encoded: Long, numChars: Int): Int {
        return corpus.setMatched(encoded, numChars)
    }
}
