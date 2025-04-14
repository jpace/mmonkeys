package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Qlog
import org.incava.ikdk.io.Qlog.printf
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MonkeyFactory
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.mky.mind.TwosDistributedStrategy
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.mind.WeightedStrategy
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.time.Durations

private class StrategiesProfile(minLength: Int, val matchGoal: Long) {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE) { it.length >= minLength }

    fun addScenario(
        scenarios: MutableList<Pair<String, () -> Unit>>, name: String, monkey: Monkey) {
        scenarios.add(Pair(name) { matchWords(monkey) })
    }

    fun profile() {
        Qlog.info("words.#", words.size)
        var id = 1

        val scenarios = mutableListOf<Pair<String, () -> Unit>>()

        run {
            val corpus = Corpus(words)
            val strategy = RandomStrategy(Keys.fullList())
            val monkey = MonkeyFactory.createMonkey(id++, corpus, strategy)
            addScenario(scenarios, "random", monkey)
        }

        run {
            val corpus = Corpus(words)
            val strategy = WeightedStrategy(corpus.words)
            val monkey = MonkeyFactory.createMonkey(id++, corpus, strategy)
            addScenario(scenarios, "weighted", monkey)
        }

        run {
            val corpus = Corpus(words)
            val strategy = TwosRandomStrategy(words)
            val monkey = MonkeyFactory.createMonkey(id++, corpus, strategy)
            addScenario(scenarios, "2s random", monkey)
        }

        run {
            val corpus = Corpus(words)
            val strategy = TwosDistributedStrategy(words)
            val monkey = MonkeyFactory.createMonkey(id++, corpus, strategy)
            addScenario(scenarios, "2s distributed", monkey)
        }

        run {
            val corpus = Corpus(words)
            val strategy = ThreesRandomStrategy(words)
            val monkey = MonkeyFactory.createMonkey(id++, corpus, strategy)
            addScenario(scenarios, "3s random", monkey)
        }

        run {
            val corpus = Corpus(words)
            val strategy = ThreesDistributedStrategy(words)
            val monkey = MonkeyFactory.createMonkey(id++, corpus, strategy)
            addScenario(scenarios, "3s distributed", monkey)
        }

        scenarios.forEach { (name, block) ->
            println(name)
            block()
        }
    }

    fun matchWords(monkey: Monkey) {
        val duration = Durations.measureDuration {
            var matches = 0L
            var attempts = 0L
            while (matches < matchGoal) {
                val result = monkey.findMatches()
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
    val obj = StrategiesProfile(minLength = 3, matchGoal = 5L)
    obj.profile()
}