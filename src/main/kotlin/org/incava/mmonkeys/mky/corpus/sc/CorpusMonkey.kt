package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.words.Words

open class CorpusMonkey(id: Int, val corpus: Corpus, private val strategy: TypeStrategy) : Monkey(id) {
    fun typeWord(): String {
        return strategy.typeWord()
    }

    override fun findMatches(): Words {
        val word = typeWord()
        val words = toWords(word)
        recordWords(words)
        return words
    }

    fun toWords(word: String): Words {
        val match = corpus.findMatch(word)
        return if (match == null) {
            MatchResults.toNonMatch(word)
        } else {
            // keystrokes here are only through the word, not the trailing space
            MatchResults.toWordsMatch(corpus, word, match)
        }
    }
}