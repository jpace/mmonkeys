package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher

class CorpusSimulationParams(numMonkeys: Int, val sought: Corpus, monkeyFactory: MonkeyFactory) :
    SimulationParams(numMonkeys, monkeyFactory) {
    override fun matcher(monkey: Monkey): Matcher {
        return monkeyFactory.corpusMatcher(monkey, sought)
    }
}