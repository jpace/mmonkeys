package org.incava.mmonkeys.exec

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.DefaultMonkeyFactory
import org.incava.mmonkeys.mky.Monkey
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
    val toFind = Int.MAX_VALUE
    val numMonkeys = 1_000_000
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length > 1 }
    val sequences = SequencesFactory.createFromWords(words)
    val randomStrategy = RandomStrategy(Keys.fullList())
    val twosRandomStrategy = TwosRandomStrategy(sequences)
    val twosDistributedStrategy = TwosDistributedStrategy(sequences)
    val threesRandomStrategy = ThreesRandomStrategy(sequences)
    val threesDistributedStrategy = ThreesDistributedStrategy(sequences)
    val weightedStrategy = WeightedStrategy(words)

    fun createWithStrategy(strategy: TypeStrategy, toFind: Int): CorpusSimulation {
        val corpus = WordCorpus(words)
        val manager = ManagerFactory.createWithView(corpus, 1)
        val factory = DefaultMonkeyFactory(manager, corpus)
        val monkeys = (0 until numMonkeys).map { _ ->
            factory.createMonkey(strategy)
        }
        return create(corpus, manager, monkeys, toFind)
    }

    fun create(corpus: Corpus, manager: Manager, monkeys: List<Monkey>, toFind: Int): CorpusSimulation {
        // @todo - change the memory settings here, and the word length, with the Map implementation ...
        return CorpusSimulation(corpus, manager, monkeys, toFind)
    }
}
