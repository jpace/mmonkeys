package org.incava.mmonkeys.perf.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.Matcher

class NoOpMatcher(monkey: Monkey, val sought: String) : Matcher(monkey) {
    override fun check(): MatchData {
        return match(0, 0)
    }
}