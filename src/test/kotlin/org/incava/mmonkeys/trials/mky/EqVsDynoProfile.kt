package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.DualCorpusMonkey
import org.incava.mmonkeys.mky.corpus.sc.DefaultMonkey
import org.incava.mmonkeys.mky.corpus.sc.SequenceMonkey
import org.incava.mmonkeys.mky.corpus.sc.WeightedMonkey
import org.incava.mmonkeys.mky.corpus.sc.map.MapCorpus
import org.incava.mmonkeys.mky.corpus.sc.map.MapGenMonkey
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Words
import org.incava.time.Durations

private class EqVsDynoProfile {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE) { it.length > 1 }
    val matchGoal = 20L

    fun profile() {
        Qlog.info("words.#", words.size)
        run {
            val corpus = DualCorpus(words)
            val monkey = DualCorpusMonkey(1, corpus)
            matchWords("dual") { monkey.findMatches() }
        }

        run {
            val corpus = MapCorpus(words)
            val monkey = MapGenMonkey(4, Typewriter(), corpus)
            matchWords("gen map") { monkey.findMatches() }
        }

        run {
            val corpus = Corpus(words)
            val typewriter = Typewriter()
            val monkey = DefaultMonkey(2, typewriter, corpus)
            matchWords("random") { monkey.findMatches() }
        }

        run {
            val corpus = Corpus(words)
            val typewriter = Typewriter()
            val monkey = SequenceMonkey(3, typewriter, corpus)
            matchWords("sequence") { monkey.findMatches() }
        }

        run {
            val corpus = Corpus(words)
            val typewriter = Typewriter()
            val monkey = WeightedMonkey(5, typewriter, corpus)
            matchWords("weighted") { monkey.findMatches() }
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