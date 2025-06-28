package org.incava.mmonkeys.trials.mky.mind

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.mky.mind.TwosDistributedStrategy
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil

class StrategiesTrial {
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)

    fun runIt(name: String, strategy: TypeStrategy) {
        Qlog.info("name", name)
        val results = mutableMapOf<String, Int>()
        repeat(10) {
            val result = strategy.getChars()
            // Qlog.info("result", "<$result>")
            results.compute(result) { _, count -> (count ?: 0) + 1 }
            Qlog.info("word", result)
        }
        println()
    }

    fun random() {
        val obj = RandomStrategy(Keys.fullList())
        runIt("random", obj)
    }

    fun twosDistributed() {
        val obj = TwosDistributedStrategy(words)
        runIt("2s distributed", obj)
    }

    fun twosRandom() {
        val obj = TwosRandomStrategy(words)
        runIt("2s random", obj)
    }

    fun threesDistributed() {
        val obj = ThreesDistributedStrategy(words)
        runIt("3s distributed", obj)
    }

    fun threesRandom() {
        val obj = ThreesRandomStrategy(words)
        runIt("3s random", obj)
    }
}

fun main() {
    val obj = StrategiesTrial()
    obj.random()
    obj.twosDistributed()
    obj.twosRandom()
    obj.threesDistributed()
    obj.threesRandom()
}