package org.incava.mmonkeys.trials.mky

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.sc.ContextStrategy
import org.incava.mmonkeys.mky.corpus.sc.CorpusMonkey
import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.mky.corpus.sc.StrategyFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Words
import org.incava.time.Durations

private class StrategiesProfile(private val numInvokes: Long, private val numTrials: Int = 5) {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE) { it.length > 3 }
    val matchGoal = 10L

    fun addMonkey(profiler: Profiler, name: String, monkey: CorpusMonkey) {
        profiler.add(name) { matchWords2(monkey) }
    }

    fun profile() {
        Qlog.info("words.#", words.size)
        var id = 1

        val profiler = Profiler(numInvokes, numTrials)

        run {
            val corpus = Corpus(words)
            val strategy = StrategyFactory.fullRandom()
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey(profiler, "random", monkey)
        }

        run {
            val corpus = Corpus(words)
            val strategy = StrategyFactory.weightedStrategy(corpus.words)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey(profiler, "weighted", monkey)
        }

        run {
            val corpus = Corpus(words)
            val sequences = Sequences(corpus.words)
            val initStrategy = StrategyFactory.random()
            val twos = StrategyFactory.twoRandom(sequences)
            val strategy = ContextStrategy(initStrategy, twos)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey(profiler, "random 2, random init", monkey)
        }

        run {
            val corpus = Corpus(words)
            val sequences = Sequences(corpus.words)
            val initStrategy = StrategyFactory.weighted(words)
            val withContext = StrategyFactory.twoRandom(sequences)
            val strategy = ContextStrategy(initStrategy, withContext)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey(profiler, "random 2, weighted init", monkey)
        }

        run {
            val corpus = Corpus(words)
            val sequences = Sequences(corpus.words)
            val initStrategy = StrategyFactory.random()
            val twos = StrategyFactory.twosDistributed(sequences)
            val strategy = ContextStrategy(initStrategy, twos)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey(profiler, "dist 2, random init", monkey)
        }

        run {
            val corpus = Corpus(words)
            val sequences = Sequences(corpus.words)
            val initStrategy = StrategyFactory.weighted(words)
            val twos = StrategyFactory.twosDistributed(sequences)
            val strategy = ContextStrategy(initStrategy, twos)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey(profiler, "dist 2, weighted init", monkey)
        }

        run {
            val corpus = Corpus(words)
            val sequences = Sequences(corpus.words)
            val initStrategy = StrategyFactory.weighted(words)
            val withContext = StrategyFactory.threesRandom(sequences)
            val strategy = ContextStrategy(initStrategy, withContext)
            val monkey = CorpusMonkey(id++, corpus, strategy)
            addMonkey(profiler, "random 3, weighted init", monkey)
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)
    }

    fun matchWords(generator: () -> Words) {
        val duration = Durations.measureDuration {
            var matches = 0L
            var attempts = 0L
            while (matches < matchGoal) {
                val result = generator()
                val count = result.words.count { words.contains(it.string) }
                matches += count
                attempts += result.numAttempts
//                if (count != 0) {
//                    Qlog.info("result", result)
//                    Qlog.info("attempts", attempts)
//                }
            }
            Qlog.info("attempts", attempts)
            Qlog.info("matches", matches)
        }
        Qlog.info("duration", duration)
        Qlog.blankLine()
    }

    fun matchWords2(monkey: Monkey) {
        val duration = Durations.measureDuration {
            var matches = 0L
            var attempts = 0L
            while (matches < matchGoal) {
                val result = monkey.findMatches()
                val count = result.words.count { words.contains(it.string) }
                matches += count
                attempts += result.numAttempts
                if (count != 0) {
                    Qlog.info("result", result)
                    Qlog.info("attempts", attempts)
                }
            }
            Qlog.info("attempts", attempts)
            Qlog.info("matches", matches)
        }
        Qlog.info("duration", duration)
        Qlog.blankLine()
    }
}

fun main() {
    val obj = StrategiesProfile(1L, 1)
    obj.profile()
}