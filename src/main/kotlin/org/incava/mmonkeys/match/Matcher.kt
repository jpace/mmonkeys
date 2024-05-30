package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class Matcher(val monkey: Monkey) : Matching {
    // @todo - integrate this into Monkey class

    override fun match(keystrokes: Int, index: Int): MatchData = monkey.match(keystrokes, index)

    override fun noMatch(keystrokes: Int): MatchData = monkey.noMatch(keystrokes)

    override fun randomLength() = monkey.randomLength()
}