package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.corpus.CorpusFilter

class RepeatCharFilter(val filter: CorpusFilter) : GenFilter {
    // one character back:
    var prev1: Char? = null

    // two characters back:
    var prev2: Char? = null

    override fun check(ch: Char): Boolean {
        if (prev1 != null) {
            if (filter.missingTwos[prev1]?.contains(ch) == true) {
                return false
            }
            if (prev2 != null) {
                if (filter.missingThrees[prev2]?.get(prev1)?.contains(ch) == true) {
                    return false
                }
            }
        }
        prev2 = prev1
        prev1 = ch
        return true
    }
}
