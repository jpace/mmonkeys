package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.mky.corpus.sc.CorpusMonkey
import org.incava.mmonkeys.mky.corpus.sc.MatchResults
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.words.Words

class MapMonkey(id: Int, corpus: MapCorpus) : CorpusMonkey(id, WordChecker(corpus), RandomStrategy(Keys.fullList())) {
    val mapCorpus = corpus

    override fun findMatches(): Words {
        val word = typeWord()
        val forLength = mapCorpus.forLength(word.length) ?: return MatchResults.toNonMatch(word)
        val indices = forLength[word]
        return if (indices == null) {
            MatchResults.toNonMatch(word)
        } else {
            // we're always removing/matching the *first* index
            val index = indices.removeAt(0)
            if (indices.isEmpty()) {
                forLength.remove(word)
            }
            // corpus.setMatched(word, numChars)
            MatchResults.toWordsMatch(mapCorpus, word, index)
        }
    }
}
