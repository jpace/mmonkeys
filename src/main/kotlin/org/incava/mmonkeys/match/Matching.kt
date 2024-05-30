package org.incava.mmonkeys.match

interface Matching {
    // @todo - integrate this into Monkey class

    fun check(): MatchData

    fun match(keystrokes: Int, index: Int): MatchData

    fun noMatch(keystrokes: Int): MatchData

    fun randomLength(): Int
}