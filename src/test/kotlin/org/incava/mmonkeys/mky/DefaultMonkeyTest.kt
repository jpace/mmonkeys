package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Test

class DefaultMonkeyTest {
    @Test
    fun type() {
        val corpus = CorpusFactory.defaultCorpus()
        val strategy = RandomStrategy(Keys.fullList())
        val manager = Manager(corpus, 1)
        val mgr = DefaultMonkeyManager(manager, corpus)
        val obj = mgr.createMonkey(strategy)
        repeat(100) {
            obj.type()
        }
        Qlog.info("manager.iterations", manager.iterations)
        Qlog.info("manager.matchesByLength", manager.matchesByLength)
        Qlog.info("manager.totalKeystrokes", manager.totalKeystrokes)
    }
}