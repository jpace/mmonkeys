package org.incava.mmonkeys.match

open class Attempt(val keystrokes: Int)

class MatchData(val isMatch: Boolean, keystrokes: Int, val index: Int) : Attempt(keystrokes)
