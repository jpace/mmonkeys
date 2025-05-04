package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Qlog
import org.incava.ikdk.io.Qlog.printf
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.DefaultMonkey
import org.incava.mmonkeys.mky.DefaultMonkeyManager
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

private class StrategiesProfile(minLength: Int, val matchGoal: Long) {
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length >= minLength }
    val scenarios = mutableListOf<Pair<String, () -> Unit>>()

    fun addScenario(name: String, strategy: TypeStrategy) {
        val corpus = Corpus(words)
        val mgr = DefaultMonkeyManager(corpus)
        val monkey = mgr.createMonkey(strategy)
        scenarios.add(Pair(name) { matchWords(monkey) })
    }

    fun profile() {
        Qlog.info("words.#", words.size)
        var id = 1

        run {
            addScenario("random", RandomStrategy(Keys.fullList()))
        }

        run {
            addScenario("weighted", WeightedStrategy(words))
        }

        run {
            addScenario("2s random", TwosRandomStrategy(words))
        }

        run {
            addScenario("2s distributed", TwosDistributedStrategy(words))
        }

        run {
            addScenario("3s random", ThreesRandomStrategy(words))
        }

        run {
            addScenario("3s distributed", ThreesDistributedStrategy(words))
        }

        Qlog.info("scenarios", scenarios)

        scenarios.forEach { (name, block) ->
            println(name)
            block()
        }
    }

    fun matchWords(monkey: DefaultMonkey) {
        Qlog.info("monkey", monkey)
        val duration = Durations.measureDuration {
            var matches = 0L
            var attempts = 0L
            while (matches < matchGoal) {
                val result = monkey.runAttempt()
                val count = result.words.count { words.contains(it.string) }
                matches += count
                attempts += result.numAttempts
                if (count != 0) {
                    printf("%,d - %s", attempts, result.words.map { it.string })
                }
            }
            printf("attempts: %,d", attempts)
            println("matches: $matches")
        }
        println("duration: ${Durations.formatted(duration.second)}")
        println()
    }
}

private fun main() {
    val obj = StrategiesProfile(minLength = 3, matchGoal = 10L)
    obj.profile()
}