package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Qlog
import org.incava.ikdk.io.Qlog.printf
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.sc.CorpusMonkey
import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.mky.mind.TwosDistributedStrategy
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.mind.WeightedStrategy
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.time.Durations

private class StrategiesProfile {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE) { it.length > 3 }
    val matchGoal = 10L

    fun addMonkey2(
        scenarios: MutableList<Pair<String, () -> Unit>>, name: String, monkey: CorpusMonkey) {
        scenarios.add(Pair(name) { matchWords(monkey) })
    }

    fun profile() {
        Qlog.info("words.#", words.size)
        var id = 1

        val scenarios = mutableListOf<Pair<String, () -> Unit>>()

        run {
            val corpus = Corpus(words)
            val strategy = RandomStrategy(Keys.fullList())
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey2(scenarios, "random", monkey)
        }

        run {
            val corpus = Corpus(words)
            val strategy = WeightedStrategy(corpus.words)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey2(scenarios, "weighted", monkey)
        }

        run {
            val corpus = Corpus(words)
            val sequences = Sequences(corpus.words)
            val strategy = TwosRandomStrategy(sequences)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey2(scenarios, "2s random", monkey)
        }

        run {
            val corpus = Corpus(words)
            val sequences = Sequences(corpus.words)
            val strategy = TwosDistributedStrategy(sequences)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey2(scenarios, "2s distributed", monkey)
        }

        run {
            val corpus = Corpus(words)
            val sequences = Sequences(corpus.words)
            val strategy = ThreesRandomStrategy(sequences)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey2(scenarios, "3s random", monkey)
        }

        run {
            val corpus = Corpus(words)
            val sequences = Sequences(corpus.words)
            val strategy = ThreesDistributedStrategy(sequences)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey2(scenarios, "3s distributed", monkey)
        }

        scenarios.shuffled().forEach { (name, block) ->
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
                    printf("%5d - %s", attempts, result.words.map { it.string })
                }
            }
            println("attempts: $attempts")
            println("matches: $matches")
        }
        println("duration: ${duration.second}")
        println()
    }
}

fun main() {
    val obj = StrategiesProfile()
    obj.profile()
}