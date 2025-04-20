package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.words.Words
import org.incava.mmonkeys.words.WordsFactory

class MapWordChecker(override val corpus: MapCorpus, private val corpusUpdater: MapCorpusUpdater = MapCorpusUpdater(corpus)) : WordChecker(corpus) {
    override fun processWord(word: String): Words {
        val forLength = corpus.forLength(word.length) ?: return WordsFactory.toWordsNonMatch(word)
        val indices = forLength[word]
        return if (indices == null) {
            WordsFactory.toWordsNonMatch(word)
        } else {
            val index = corpusUpdater.wordMatched(word, word.length)
            WordsFactory.toWordsMatch(word, index)
        }
    }
}
