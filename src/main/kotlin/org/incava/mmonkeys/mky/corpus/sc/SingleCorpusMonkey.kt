package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.rand.RandomFactory
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandInt

abstract class SingleCorpusMonkey(id: Int, corpus: Corpus) : Monkey(id, corpus) {
    abstract val strategy: TypeStrategy

    fun typeWord(): String {
        return strategy.typeWord()
    }

    fun toWordsMatch(word: String, index: Int): Words {
        val numAttempts = 1
        corpus.setMatched(index)
        // count the space in the attempt:
        return Words(listOf(Word(word, index)), word.length.toLong() + 1, numAttempts)
    }

    fun toNonMatch(word: String): Words {
        // count the space in the attempt:
        val numAttempts = 1
        return Words(word.length.toLong() + 1, numAttempts)
    }
}