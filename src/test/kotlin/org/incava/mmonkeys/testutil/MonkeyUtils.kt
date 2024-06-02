package org.incava.mmonkeys.testutil

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.string.StringMatcher
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

    fun createMatcher(
        string: String,
        stringMatcher: (monkey: Monkey, string: String) -> StringMatcher,
        chars: List<Char> = Keys.fullList(),
        typewriterCtor: (chars: List<Char>) -> Typewriter = ::DeterministicTypewriter
    ): Pair<Monkey, StringMatcher> {
        val typewriter = typewriterCtor(chars)
        val monkeyFactory = MonkeyFactory({ typewriter }, stringMatcher = stringMatcher, chars = chars)
        return monkeyFactory.createStringMatcher(string)
    }
}