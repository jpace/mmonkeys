package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words

open class CorpusMonkey(id: Int, corpus: Corpus, private val strategy: TypeStrategy) : Monkey(id, corpus) {
    fun typeWord(): String {
        return strategy.typeWord()
    }

    override fun findMatches(): Words {
        val word = typeWord()
        val match = corpus.findMatch(word)
        if (match == null) {
            return MatchResults.toNonMatch(word)
        } else {
            // keystrokes here are only through the word, not the trailing space
            return MatchResults.toWordsMatch(corpus, word, match)
        }
    }
}