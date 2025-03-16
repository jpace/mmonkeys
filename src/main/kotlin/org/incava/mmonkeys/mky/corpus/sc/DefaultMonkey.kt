package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.type.TypeStrategy
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

open class DefaultMonkey(id: Int, typewriter: Typewriter, corpus: Corpus, private val strategy: TypeStrategy = RandomStrategy(typewriter.chars)) : SingleCorpusMonkey(id, typewriter, corpus) {
    override fun findMatches(): Words {
        val typed = strategy.typeWord()
        val match = corpus.words.indices.find { index ->
            // not sure which condition is faster:
            corpus.words[index] == typed && !corpus.isMatched(index)
        } ?: return toNonMatch(typed)
        // keystrokes here are only through the word, not the trailing space
        return toWordsMatch(corpus.words[match], match)
    }
}