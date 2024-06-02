package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.string.StringMatcher

object SimulationParamsFactory {
    fun createCorpusParams(
        numMonkeys: Int,
        sought: Corpus,
        matcher: (Monkey, Corpus) -> CorpusMatcher,
        typewriterFactory: TypewriterFactory = TypewriterFactory(),
    ): SimulationParams<Corpus> {
        val monkeyFactory = MonkeyFactory({ typewriterFactory.create() }, corpusMatcher = matcher)
        return SimulationParams(numMonkeys, monkeyFactory, sought)
    }

    fun createStringParams(
        numMonkeys: Int,
        sought: String,
        matcher: (Monkey, String) -> StringMatcher,
        typewriterFactory: TypewriterFactory = TypewriterFactory(),
    ): SimulationParams<String> {
        val monkeyFactory = MonkeyFactory({ typewriterFactory.create() }, stringMatcher = matcher)
        return SimulationParams(numMonkeys, monkeyFactory, sought)
    }
}