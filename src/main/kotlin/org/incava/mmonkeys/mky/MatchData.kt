package org.incava.mmonkeys.mky

open class Attempt(val keystrokes: Int)

class MatchData(val isMatch: Boolean, keystrokes: Int, val index: Int) : Attempt(keystrokes) {
    override fun toString(): String {
        return "MatchData(isMatch=$isMatch, index=$index, keystrokes=$keystrokes)"
    }
}
