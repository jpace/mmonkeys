package org.incava.mmonkeys.mky

class MatchData(val isMatch: Boolean, val keystrokes: Int, val index: Int?) {
    constructor(keystrokes: Int, index: Int) : this(true, keystrokes, index)
}