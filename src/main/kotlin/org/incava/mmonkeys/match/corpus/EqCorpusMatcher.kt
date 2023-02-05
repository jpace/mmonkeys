package org.incava.mmonkeys.match.corpus

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.ikdk.io.Console

class EqCorpusMatcher(monkey: Monkey, sought: Corpus) : CorpusMatcher(monkey, sought) {
    override fun check(): MatchData {
        val word = monkey.nextString()
        Console.info("word", word)
        val result = sought.hasWord(word)
        Console.info("result", result)
        return if (result.first)
            match(word.length, 0)
        else
            noMatch(word.length)
    }
}