package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher

class CorpusSimulationParams(
    numMonkeys: Int,
    val sought: Corpus,
    matcher: (Monkey, Corpus) -> CorpusMatcher,
    typewriterFactory: TypewriterFactory = TypewriterFactory(),
) : SimulationParams(numMonkeys, MonkeyFactory({ typewriterFactory.create() }, corpusMatcher = matcher)) {
    override fun matcher(monkey: Monkey): Matcher {
        return monkeyFactory.corpusMatcher(monkey, sought)
    }
}