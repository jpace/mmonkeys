package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Qlog
import org.incava.ikdk.io.Qlog.printf
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.impl.ListCorpus
import org.incava.mmonkeys.mky.DefaultMonkey
import org.incava.mmonkeys.mky.DefaultMonkeyManager
import org.incava.mmonkeys.corpus.impl.MapCorpus
import org.incava.mmonkeys.mky.mgr.Manager
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

    fun addScenarioList(name: String, strategy: TypeStrategy) {
        val corpus = ListCorpus(words)
        val manager = Manager(corpus)
        val mgr = DefaultMonkeyManager(manager, corpus)
        val monkey = mgr.createMonkey(strategy)
        scenarios.add(Pair(name) { matchWords(monkey) })
    }

    fun addScenarioMap(name: String, strategy: TypeStrategy) {
        val corpus = MapCorpus(words)
        val manager = Manager(corpus)
        val mgr = DefaultMonkeyManager(manager, corpus)
        val monkey = mgr.createMonkey(strategy)
        scenarios.add(Pair(name) { matchWords(monkey) })
    }

    fun profile() {
        Qlog.info("words.#", words.size)
        run {
            addScenarioList("random list", RandomStrategy(Keys.fullList()))
            addScenarioMap("random map", RandomStrategy(Keys.fullList()))
        }

        run {
            addScenarioList("weighted list", WeightedStrategy(words))
            addScenarioMap("weighted map", WeightedStrategy(words))
        }

        run {
            addScenarioList("2s random list", TwosRandomStrategy(words))
            addScenarioMap("2s random map", TwosRandomStrategy(words))
        }

        run {
            addScenarioList("2s distributed list", TwosDistributedStrategy(words))
            addScenarioMap("2s distributed map", TwosDistributedStrategy(words))
        }

        run {
            addScenarioList("3s random list", ThreesRandomStrategy(words))
            addScenarioMap("3s random map", ThreesRandomStrategy(words))
        }

        run {
            addScenarioList("3s distributed list", ThreesDistributedStrategy(words))
            addScenarioMap("3s distributed map", ThreesDistributedStrategy(words))
        }

        Qlog.info("scenarios", scenarios.toMap().keys)

        scenarios.forEach { (name, block) ->
            println(name)
            block()
        }
    }

    fun matchWords(monkey: DefaultMonkey) {
        val duration = Durations.measureDuration {
            var matches = 0L
            var attempts = 0L
            while (matches < matchGoal) {
                val result = monkey.runAttempt()
                val word = result.word
                ++attempts
                if (word != null && words.contains(word.string)) {
                    ++matches
                    printf("%,d - %s", attempts, word.string)
                }
            }
            printf("attempts: %,d", attempts)
            println("matches : $matches")
        }
        printf("duration: %,d ms\n", duration.second.toMillis())
        println()
    }
}

private fun main() {
    val obj = StrategiesProfile(minLength = 4, matchGoal = 10L)
    obj.profile()
}