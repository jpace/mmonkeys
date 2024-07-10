package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.type.Typewriter

class LengthCorpusMonkey(override val corpus: LengthCorpus, id: Int, typewriter: Typewriter) : CorpusMonkey(corpus, id, typewriter) {
    override fun check(): MatchData {
        val toEndOfWord = randomLength()
        val length = toEndOfWord - 1
        val forLength = corpus.soughtByLength[length]
        if (forLength != null) {
            val word = nextChars(length)
            val index = forLength.indexOf(word)
            if (index >= 0) {
                corpus.matched(word, index, length)
                return match(length, index)
            }
        }
        return noMatch(length)
    }
}
