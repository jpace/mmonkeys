package org.incava.mmonkeys.testutil

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

object MonkeyUtils {
    fun createMatcher(
        corpus: Corpus,
        corpusMatcher: (monkey: Monkey, corpus: Corpus) -> CorpusMatcher,
        chars: List<Char> = Keys.fullList(),
        typewriterCtor: (chars: List<Char>) -> Typewriter = ::DeterministicTypewriter
    ): Pair<Monkey, CorpusMatcher> {
        val typewriter = typewriterCtor(chars)
        val monkeyFactory = MonkeyFactory({ typewriter }, corpusMatcher = corpusMatcher, chars = chars)
        return monkeyFactory.createCorpusMatcher(corpus)
    }

    fun runTest(monkey: Monkey, maxAttempts: Long = 100_000_000_000_000L) : Long {
        var iteration = 0L
        while (iteration < maxAttempts) {
            val result = monkey.check()
            if (result.isMatch) {
                return iteration
            }
            ++iteration
        }
        println("failing after $iteration iterations")
        throw RuntimeException("failed after $iteration iterations")
    }
}