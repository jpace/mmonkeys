package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.sc.SequenceMonkey
import org.incava.mmonkeys.mky.corpus.sc.EqMonkey
import org.incava.mmonkeys.mky.corpus.sc.map.MapCorpus
import org.incava.mmonkeys.mky.corpus.sc.map.MapGenMonkey
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Words
import kotlin.test.Test

private class EqSequenceMonkeyTest {
    // limiting to 13 for numbers monkey
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE) { it.length in 1..13 }
    val matchGoal = 100L

    @Test
    fun eqMonkey() {
        val corpus = Corpus(words)
        val monkey = EqMonkey(2, Typewriter(), corpus)
        matchWords("eq") { monkey.findMatches() }
    }


    @Test
    fun sequenceMonkey() {
        val corpus = Corpus(words)
        val monkey = SequenceMonkey(2, Typewriter(), corpus)
        matchWords("dyno") { monkey.findMatches() }
    }

    @Test
    fun mapMonkey() {
        val corpus = MapCorpus(words)
        val monkey = MapGenMonkey(2, Typewriter(), corpus)
        matchWords("gen map") { monkey.findMatches() }
    }

    fun matchWords(name: String, generator: () -> Words) {
        Qlog.info("name", name)
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
}
