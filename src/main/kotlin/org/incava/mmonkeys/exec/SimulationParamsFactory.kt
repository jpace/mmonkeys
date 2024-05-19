package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.string.StringMatcher

object SimulationParamsFactory {
    fun createCorpusParams(
        numMonkeys: Int,
        sought: Corpus,
        matcher: (Monkey, Corpus) -> CorpusMatcher,
        typewriterFactory: TypewriterFactory = TypewriterFactory(),
    ): CorpusSimulationParams {
        val monkeyFactory = MonkeyFactory({ typewriterFactory.create() }, corpusMatcher = matcher)
        return CorpusSimulationParams(numMonkeys, sought, monkeyFactory)
    }

    fun createStringParams(
        numMonkeys: Int,
        sought: String,
        matcher: (Monkey, String) -> StringMatcher,
        typewriterFactory: TypewriterFactory = TypewriterFactory(),
    ): StringSimulationParams {
        val monkeyFactory = MonkeyFactory({ typewriterFactory.create() }, stringMatcher = matcher)
        return StringSimulationParams(numMonkeys, sought, monkeyFactory)
    }
}