package org.incava.mmonkeys.exec

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.MonkeyFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mgr.ManagerFactory
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.mky.mind.TwosDistributedStrategy
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.mky.mind.WeightedStrategy
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil

object CorpusSimulationFactory {
    val numMonkeys = 10_000
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length > 1 }
    val sequences = SequencesFactory.createFromWords(words)
    val randomStrategy = RandomStrategy(Keys.fullList())
    val twosRandomStrategy = TwosRandomStrategy(sequences)
    val twosDistributedStrategy = TwosDistributedStrategy(sequences)
    val threesRandomStrategy = ThreesRandomStrategy(sequences)
    val threesDistributedStrategy = ThreesDistributedStrategy(sequences)
    val weightedStrategy = WeightedStrategy(words)

    fun create(strategy: TypeStrategy, toFind: Int): CorpusSimulation {
        val corpus = WordCorpus(words)
        val manager = ManagerFactory.createWithView(corpus, 1)
        val factory = MonkeyFactory(manager, corpus)
        val monkeys = (0 until numMonkeys).map { _ ->
            factory.createMonkey(strategy)
        }
        return CorpusSimulation(corpus, manager, monkeys, toFind)
    }

    fun create(corpus: WordCorpus, manager: Manager, factory: MonkeyFactory, strategy: TypeStrategy, toFind: Int): CorpusSimulation {
        val monkeys = (0 until numMonkeys).map { _ ->
            factory.createMonkey(strategy)
        }
        return CorpusSimulation(corpus, manager, monkeys, toFind)
    }
}
