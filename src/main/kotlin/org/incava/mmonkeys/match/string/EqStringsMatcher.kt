package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.util.Console
import java.time.ZonedDateTime

class EqStringsMatcher(monkey: Monkey, sought: Corpus) : CorpusMatcher(monkey, sought) {
    // constructor(monkey: Monkey, sought: List<String>) : this(monkey, Corpus(sought))

    override fun check(): MatchData {
        val word = monkey.nextString()
        val match = sought.hasWord(word)
        return if (match.first) {
            Console.info("now", ZonedDateTime.now())
            Console.info("sought", sought)
            sought.removeAt(match.second)
            match(word.length, match.second)
        } else
            noMatch(word.length)
    }
}