package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.DefaultMonkeyFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequenceMonkeyTest {
    @Test
    fun type() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val corpus = WordCorpus(words)
        val sequences = SequencesFactory.createFromWords(words)
        val strategy = TwosRandomStrategy(sequences)
        val manager = Manager(corpus)
        val mgr = DefaultMonkeyFactory(manager, corpus)
        val obj = mgr.createMonkey(strategy)
        repeat(100) {
            obj.type()
        }
        Qlog.info("manager.iterations", manager.iterations())
        Qlog.info("manager.matchesByLength", manager.matchesByLength())
        Qlog.info("manager.totalKeystrokes", manager.keystrokesCount())
    }
}