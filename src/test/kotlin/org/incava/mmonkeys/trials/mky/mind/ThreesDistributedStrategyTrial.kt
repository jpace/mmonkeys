package org.incava.mmonkeys.trials.mky.mind

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.util.ResourceUtil

class ThreesDistributedStrategyTrial {
    fun typeWord() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val obj = ThreesDistributedStrategy(words)
        val results = mutableMapOf<String, Int>()
        repeat(1000) {
            val result = obj.typeWord()
            Qlog.info("result", "<$result>")
            results.compute(result) { _, count -> (count ?: 0) + 1 }
        }
        Qlog.info("results", results.toSortedMap())
    }
}

fun main() {
    val obj = ThreesDistributedStrategyTrial()
    obj.typeWord()
}