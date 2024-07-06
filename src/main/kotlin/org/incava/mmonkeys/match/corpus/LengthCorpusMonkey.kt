package org.incava.mmonkeys.match.corpus

import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.type.Typewriter

class LengthCorpusMonkey(override val corpus: LengthCorpus, id: Int, typewriter: Typewriter) : CorpusMonkey(corpus, id, typewriter) {
    override fun check(): MatchData {
        // @todo - fix the index here, which is into the array for the length,
        // not the index in the corpus (which needs to be indexed by length and shared among monkeys).
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string
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
        return noMatch(toEndOfWord)
    }
}
