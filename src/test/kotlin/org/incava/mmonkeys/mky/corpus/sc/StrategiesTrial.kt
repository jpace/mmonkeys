package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.mky.mind.TwosDistributedStrategy
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.mind.WeightedStrategy
import org.incava.mmonkeys.util.ResourceUtil

class StrategiesTrial {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)

    fun runIt(name: String, function: () -> String) {
        Qlog.info("name", name)
        val iterations = 10
        repeat(iterations) {
            val word = function()
            Qlog.info("word", word)
        }
        println()
    }

    fun twosRandom(): () -> String {
        val sequences = Sequences(words)
        val obj = TwosRandomStrategy(sequences)
        return { obj.typeWord() }
    }

    fun twosDistributed(): () -> String {
        val sequences = Sequences(words)
        val obj = TwosDistributedStrategy(sequences)
        return { obj.typeWord() }
    }

    fun threesRandom(): () -> String {
        val sequences = Sequences(words)
        val obj = ThreesRandomStrategy(sequences)
        return { obj.typeWord() }
    }

    fun threesDistributed(): () -> String {
        val sequences = Sequences(words)
        val obj = ThreesDistributedStrategy(sequences)
        return { obj.typeWord() }
    }

    fun weighted(): () -> String {
        val obj = WeightedStrategy(words)
        return { obj.typeWord() }
    }
}

fun main() {
    val obj = StrategiesTrial()
    obj.runIt("twos random", obj.twosRandom())
    obj.runIt("twos distributed", obj.twosDistributed())
    obj.runIt("threes random", obj.threesRandom())
    obj.runIt("threes distributed", obj.threesDistributed())
    obj.runIt("weighted", obj.weighted())
}