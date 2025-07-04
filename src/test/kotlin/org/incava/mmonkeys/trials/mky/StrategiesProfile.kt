package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Qlog
import org.incava.ikdk.io.Qlog.printf
import org.incava.mesa.DurationColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.MonkeyFactory
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
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.time.Durations
import java.time.Duration

data class StrategiesProfileResult(val attempts: Long, val matches: Long, val duration: Duration)

private class StrategiesProfile(minLength: Int, val matchGoal: Long, val verbose: Boolean = false) {
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length >= minLength }
    val scenarios = mutableListOf<Pair<String, () -> StrategiesProfileResult>>()

    fun addScenario(name: String, strategy: TypeStrategy) {
        val corpus = WordCorpus(words)
        val manager = ManagerFactory.createWithoutView(corpus)
        val mgr = MonkeyFactory(manager, corpus)
        val monkey = mgr.createMonkey(strategy)
        scenarios.add(Pair(name) { matchWords(manager, monkey) })
    }

    fun profile() {
        Qlog.info("words.#", words.size)
        addScenario("random", RandomStrategy(Keys.fullList()))
        addScenario("weighted", WeightedStrategy(words))
        addScenario("2s random", TwosRandomStrategy(words))
        addScenario("2s distributed", TwosDistributedStrategy(words))
        addScenario("3s random", ThreesRandomStrategy(words))
        addScenario("3s distributed", ThreesDistributedStrategy(words))
        val results = scenarios.associate { (name, block) ->
            println(name)
            val result = block()
            name to result
        }
        val table = Table(
            listOf(
                StringColumn("type", 16, true),
                LongColumn("attempts", 12),
                LongColumn("matches", 8),
                DurationColumn("duration", 8)
            )
        )
        table.writeHeader()
        results.forEach { (name, result) ->
            table.writeRow(name, result.attempts, result.matches, result.duration)
        }
    }

    fun matchWords(manager: Manager, monkey: Monkey): StrategiesProfileResult {
        val duration = Durations.measureDuration {
            while (manager.matchCount() < matchGoal) {
                monkey.type()
            }
            if (verbose) {
                printf("attempts: %,d", manager.attemptCount())
                println("matches : ${manager.matchCount()}")
            }
        }
        if (verbose) {
            printf("duration: %,d ms", duration.second.toMillis())
            println()
        }
        return StrategiesProfileResult(manager.attemptCount(), manager.matchCount().toLong(), duration.second)
    }
}

private fun main() {
    val obj = StrategiesProfile(minLength = 5, matchGoal = 100L)
    obj.profile()
}