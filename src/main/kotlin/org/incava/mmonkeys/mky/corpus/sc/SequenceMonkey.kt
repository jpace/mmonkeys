package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.type.Typewriter

class SequenceMonkey(id: Int, typewriter: Typewriter, corpus: Corpus) : DefaultMonkey(id, typewriter, corpus) {
    private val sequenceStrategy: SequenceStrategy
    private val randomStrategy = RandomStrategy(typewriter.chars)

    init {
        val sequences = Sequences(corpus.words)
        sequenceStrategy = SequenceStrategy(sequences, randomStrategy::typeCharacter)
    }
}