package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.rand.RandomFactory
import org.incava.mmonkeys.type.Typewriter
import org.incava.rando.RandInt

abstract class Monkey(val id: Int, val typewriter: Typewriter, open val corpus: Corpus) {
    val monitors = mutableListOf<MonkeyMonitor>()
    val rand: RandInt = RandomFactory.getCalculated(typewriter.numChars())
    val matchesByLength = mutableMapOf<Int, Int>()

    fun nextChar(): Char = typewriter.nextCharacter()

    override fun toString(): String = "Monkey(id=$id)"

    abstract fun check(): MatchData

    fun findMatch(): Int? {
        val match = check()
        return if (match.isMatch) match.index else null
    }

    open fun match(keystrokes: Int, index: Int): MatchData {
        return MatchData(keystrokes, index).also {
            val length = corpus.words[index].length
            matchesByLength.merge(length, 1) { prev, _ -> prev + 1 }
            notifyMonitors(it)
        }
    }

    // keystroke value is the number of character *before* the space, i.e.,
    // the length of the non-matching word.
    fun noMatch(keystrokes: Int): MatchData {
        return MatchData(false, keystrokes, null).also { notifyMonitors(it) }
    }

    // number of keystrokes at which we'll hit the end-of-word character
    // thus length == 1 means we'll hit at the first invocation, with
    // an empty string, 8 means we had 7 (hypothetical) characters,
    // and so on and so forth.
    fun randomLength() = rand.nextInt()

    private fun notifyMonitors(matchData: MatchData) {
        monitors.forEach { it.add(this, matchData) }
    }
}