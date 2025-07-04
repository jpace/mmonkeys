package org.incava.mmonkeys.mky.mgr;

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusStatsView
import java.io.File
import java.io.PrintStream

object ManagerFactory {
    fun createWithView(corpus: Corpus, outputInterval: Int): Manager {
        val simOut = PrintStream(File("/tmp/simulation.out"))
        val simMatchView = SimMatchView(corpus, outputInterval, simOut)
        val statsOut = PrintStream(File("/tmp/corpus-stats.out"))
        val statsView = CorpusStatsView(corpus, 100, statsOut)
        val perfOut = PrintStream(File("/tmp/monkeys-stats.out"))
        val perfView = SimPerfView(corpus, perfOut)
        val view = ManagerView(simMatchView, statsView, perfView)
        return Manager(corpus, view)
    }

    fun createWithoutView(corpus: Corpus): Manager {
        return Manager(corpus, null)
    }
}
