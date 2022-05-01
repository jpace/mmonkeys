package org.incava.mmonkeys.strategy

import org.incava.mmonkeys.Console.log
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Word

class WordMatcher(private val monkey: Monkey) {
    fun run(sought: Word): Long? {
        log("sought", sought)
        log("#sought", sought.length())
        var iteration = 0L
        while (iteration < 1_000L) {
            val word = monkey.nextWord()
            log("word", word)
            if (word == sought) {
                log("word", word)
                log("iteration", iteration)
                return iteration
            } else {
                ++iteration
            }
        }
        return null
    }
}