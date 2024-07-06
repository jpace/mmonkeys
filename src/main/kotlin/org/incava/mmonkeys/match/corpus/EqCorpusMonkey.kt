package org.incava.mmonkeys.match.corpus

import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.type.Typewriter

class EqCorpusMonkey(sought: Corpus, id: Int, typewriter: Typewriter) : CorpusMonkey(sought, id, typewriter) {
    override fun check(): MatchData {
        val word = nextString()
        val index = corpus.match(word)
        return if (index >= 0) {
            corpus.removeAt(index)
            match(word.length, index)
        } else
            noMatch(word.length)
    }
}