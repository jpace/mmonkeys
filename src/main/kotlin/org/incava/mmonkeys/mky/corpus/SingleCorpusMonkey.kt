package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.rand.RandomFactory
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandInt

abstract class SingleCorpusMonkey(id: Int, val typewriter: Typewriter, corpus: Corpus) : Monkey(id, corpus) {
    val rand: RandInt = RandomFactory.getCalculated(typewriter.numChars())

    abstract fun check(): MatchData

    override fun findMatches(): Words {
        val matchData = check()
        return matchData.toWords(corpus).also { recordWords(it) }
    }

    // number of keystrokes at which we'll hit the end-of-word character
    // thus length == 1 means we'll hit at the first invocation, with
    // an empty string, 8 means we had 7 (hypothetical) characters,
    // and so on and so forth.
    fun randomLength() = rand.nextInt()

    fun match(keystrokes: Int, index: Int): MatchData {
        return MatchData(keystrokes, index)
    }

    // keystroke value is the number of character *before* the space, i.e.,
    // the length of the non-matching word.
    fun noMatch(keystrokes: Int): MatchData {
        return MatchData(keystrokes, null)
    }
}