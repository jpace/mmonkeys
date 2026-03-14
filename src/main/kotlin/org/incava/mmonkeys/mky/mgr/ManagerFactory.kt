package org.incava.mmonkeys.mky.mgr;

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusStatsView
import java.io.File
import java.io.PrintStream
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object ManagerFactory {
    fun createWithView(corpus: Corpus, outputInterval: Int): Manager {
        val timestamp = ZonedDateTime.now()
        val pattern = DateTimeFormatter.ofPattern("yy-MM-dd-HH-mm-ss-SSS")
        val time = pattern.format(timestamp)
        println("writing log files with time: $time")
        val simOut = createLogFile("simulation", time, "simulation")
        val simMatchView = SimMatchView(corpus, outputInterval, simOut)
        val statsOut = createLogFile("corpus statistics", time, "corpus-stats")
        val statsView = CorpusStatsView(corpus, 100, statsOut)
        val perfOut = createLogFile("monkey statistics", time, "monkeys-stats")
        val perfView = SimPerfView(corpus, perfOut)
        val view = ManagerView(simMatchView, statsView, perfView)
        return Manager(corpus, view)
    }

    fun createWithoutView(corpus: Corpus): Manager {
        return Manager(corpus, null)
    }

    private fun createLogFile(type: String, time: String, fileName: String): PrintStream {
        val tmpDirName = System.getProperty("java.io.tmpdir") + File.separator + "mmonkeys" + File.separator + time
        val tmpDir = File(tmpDirName)
        tmpDir.mkdirs()
        return File(tmpDir, "$fileName.out")
            .also { println("$type log file: $it") }
            .let { PrintStream(it) }
    }
}
