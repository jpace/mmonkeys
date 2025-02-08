package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.type.Typewriter

class SequenceMonkey(id: Int, typewriter: Typewriter, corpus: Corpus) : DefaultMonkey(id, typewriter, corpus) {
    private val sequenceStrategy: SequenceStrategy
    private val randomStrategy = RandomStrategy(typewriter.chars)

    init {
        val finder = SequenceFinder(corpus.words)
        sequenceStrategy = SequenceStrategy(finder.presentTwos, randomStrategy::nextCharacter)
    }

    override fun typeWord(): String {
        return sequenceStrategy.typeWord()
    }
}