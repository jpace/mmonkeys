package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.words.Words

open class DefaultMonkey(id: Int, corpus: Corpus, override val strategy: TypeStrategy) : SingleCorpusMonkey(id, corpus) {
    override fun findMatches(): Words {
        val word = typeWord()
        val match = corpus.findMatch(word) ?: return toNonMatch(word)
        // keystrokes here are only through the word, not the trailing space
        return toWordsMatch(corpus.words[match], match)
    }
}