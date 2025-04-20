package org.incava.mmonkeys.mky.corpus.sc.map

open class MapCorpusUpdater(open val corpus: MapCorpus) {
    open fun wordMatched(word: String, length: Int) : Int  {
        // this is the index into sought
        return corpus.indexedCorpus.setMatched(word, length).also { index -> corpus.setMatched(index) }
    }
}
