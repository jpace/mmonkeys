package org.incava.mmonkeys.trials.mky.mind

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.util.ResourceUtil

class ThreesDistributedStrategyTrial {
    fun typeWordProfile() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val obj = ThreesDistributedStrategy(words)
        val results = mutableListOf<String>()
        repeat(1000) {
            val result = obj.getChars()
            results += result
        }
        Qlog.info("results", results)
    }
}

private fun main() {
    val obj = ThreesDistributedStrategyTrial()
    obj.typeWordProfile()
}