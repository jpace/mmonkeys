package org.incava.mmonkeys.exec

import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus

class CorpusSimulationParams(numMonkeys: Int, sought: Corpus, monkeyFactory: MonkeyFactory) :
    SimulationParams<Corpus>(numMonkeys, monkeyFactory, sought)