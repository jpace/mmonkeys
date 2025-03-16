package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.DualCorpusMonkey
import org.incava.mmonkeys.mky.corpus.sc.DefaultMonkey
import org.incava.mmonkeys.mky.corpus.sc.RandomStrategy
import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.mky.corpus.sc.SequenceStrategy
import org.incava.mmonkeys.mky.corpus.sc.WeightedStrategy
import org.incava.mmonkeys.mky.corpus.sc.map.MapCorpus
import org.incava.mmonkeys.mky.corpus.sc.map.MapGenMonkey
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Words
import org.incava.time.Durations

private class EqVsDynoProfile {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE) { it.length > 1 }
    val matchGoal = 10L

    fun profile() {
        Qlog.info("words.#", words.size)
        var id = 1

        if (false) {
            val corpus = DualCorpus(words)
            val monkey = DualCorpusMonkey(id++, corpus)
            matchWords("dual") { monkey.findMatches() }
        }

        if (false) {
            val corpus = MapCorpus(words)
            val monkey = MapGenMonkey(id++, Typewriter(), corpus)
            matchWords("gen map") { monkey.findMatches() }
        }

        run {
            val corpus = Corpus(words)
            val typewriter = Typewriter()
            val strategy = RandomStrategy(typewriter.chars)
            val monkey = DefaultMonkey(id++, typewriter, corpus, strategy)
            matchWords("random") { monkey.findMatches() }
        }

        run {
            val corpus = Corpus(words)
            val typewriter = Typewriter()
            val sequences = Sequences(corpus.words)
            val initStrategy = RandomStrategy(typewriter.chars)
            val strategy = SequenceStrategy(sequences, initStrategy::typeCharacter)
            val monkey = DefaultMonkey(id++, typewriter, corpus, strategy)
            matchWords("sequence, from random init") { monkey.findMatches() }
        }

        run {
            val corpus = Corpus(words)
            val typewriter = Typewriter()
            val strategy = WeightedStrategy(corpus.words)
            val monkey = DefaultMonkey(id++, typewriter, corpus, strategy)
            matchWords("weighted") { monkey.findMatches() }
        }

        run {
            val corpus = Corpus(words)
            val typewriter = Typewriter()
            val sequences = Sequences(corpus.words)
            val initStrategy = WeightedStrategy(corpus.words)
            val strategy = SequenceStrategy(sequences, initStrategy::typeCharacter)
            val monkey = DefaultMonkey(id++, typewriter, corpus, strategy)
            matchWords("sequence, from weighted init") { monkey.findMatches() }
        }
    }

    fun matchWords(name: String, generator: () -> Words) {
        Qlog.info("name", name)
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
}

fun main() {
    val obj = EqVsDynoProfile()
    obj.profile()
}