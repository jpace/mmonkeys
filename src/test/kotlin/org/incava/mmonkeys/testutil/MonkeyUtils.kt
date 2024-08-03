package org.incava.mmonkeys.testutil

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusMonkey
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyCtor
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

object MonkeyUtils {
    fun <T : Corpus> createMonkey(
        corpus: T,
        corpusMonkeyCtor: CorpusMonkeyCtor<T>,
        chars: List<Char> = Keys.fullList(),
        typewriterCtor: (chars: List<Char>) -> Typewriter = ::DeterministicTypewriter,
    ): CorpusMonkey {
        val typewriter = typewriterCtor(chars)
        val monkeyFactory = CorpusMonkeyFactory({ typewriter }, monkeyCtor = corpusMonkeyCtor, charsCtor = chars)
        return monkeyFactory.createMonkey(corpus)
    }

    fun runTest(monkey: Monkey, maxAttempts: Long = 100_000_000_000_000L): Long {
        var iteration = 0L
        while (iteration < maxAttempts) {
            val result = monkey.check()
            if (result.isMatch) {
                return iteration
            }
            ++iteration
        }
        throw RuntimeException("failed after $iteration iterations")
    }
}