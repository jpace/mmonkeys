package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.corpus.CorpusFilter
import org.incava.rando.RandSlotsFactory
import org.incava.time.Durations.measureDuration
import kotlin.test.Test

interface GenFilter {
    fun check(ch: Char): Boolean
}

interface GenLenFilter : GenFilter {
    fun checkLength(length: Int): Boolean
}

class WordsGeneratorTest {
    @Test
    fun match() {
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
        val generator = StrRandFactory.create(128, StrRandFactory.calcArray, StrRandFactory.assemble)
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, 100).filter { it.length in 1..12 }
        val corpus = Corpus(words)
        val wordsGenerator = WordsGenerator(slots, generator)

        repeat(100) {
            val generated = wordsGenerator.generate()
            Console.info("generated", generated)
            generated.strings.forEach { str ->
                val matches = corpus.match(str)
                if (matches >= 0) {
                    Console.info("str", str)
                    Console.info("matches", matches)
                }
            }
        }
    }

    fun testGenerator(name: String, generator: WordsGenerator, words: Collection<String>) {
        Console.info("name", name)
        var generated = 0L
        var matches = 0
        val duration = measureDuration {
            repeat(100) {
                val result = generator.generate()
//            Console.info("result", result)
                result.strings.forEach { generated++; if (words.contains(it)) matches++ }
            }
        }
        Console.info("matches", matches)
        Console.info("generated", generated)
        println("duration: $duration")
        println()
    }

    fun testGenerator(
        name: String,
        generator: WordsGenerator,
        words: Collection<String>,
        filterSupplier: () -> GenFilter,
    ) {
        Console.info("name", name)
        var matches = 0
        var generated = 0L
        val duration = measureDuration {
            repeat(100) {
                val result = generator.generate(filterSupplier)
//            Console.info("result", result)
                result.strings.forEach { generated++; if (words.contains(it)) matches++ }
            }
        }
        Console.info("matches", matches)
        Console.info("generated", generated)
        println("duration: $duration")
        println()
    }

    fun testGenerator2(
        name: String,
        generator: WordsGenerator,
        words: Collection<String>,
        filterSupplier: (Int) -> GenFilter,
    ) {
        Console.info("name", name)
        var matches = 0L
        var generated = 0L
        val duration = measureDuration {
            repeat(100) {
                val result = generator.generate2(filterSupplier)
//            Console.info("result", result)
                result.strings.forEach { generated++; if (words.contains(it)) matches++ }
            }
        }
        Console.info("matches", matches)
        Console.info("generated", generated)
        println("duration: $duration")
        println()
    }

    fun testGenerator3(
        name: String,
        generator: WordGenerator,
    ) {
        Console.info("name", name)
        var matches = 0L
        var generated = 0L
        val duration = measureDuration {
            repeat(100) {
                val result = generator.generate()
                generated++
                if (result.first >= 0) {
                    matches++
                }
            }
        }
        Console.info("matches", matches)
        Console.info("generated", generated)
        println("duration: $duration")
        println()
    }

    @Test
    fun generateMatches() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)

        val strRand = StrRandFactory.create(128, StrRandFactory.calcArray, StrRandFactory.assemble)
        val randGen = WordsGenerator(slots, strRand)
        testGenerator("calc array assemble", randGen, words)

        val strRandFiltered = StrRandFiltered(slots)
        val filteredGen = WordsGenerator(slots, strRandFiltered)
        testGenerator("filtered", filteredGen, words)

        val filter = CorpusFilter(words)
        testGenerator("repeat 2,3 chars", filteredGen, words) { RepeatCharFilter(filter) }

        val mapCorpus1 = MapCorpus(words)
        testGenerator2("known word", filteredGen, words) { KnownWordFilter(mapCorpus1, it) }
        val mapCorpus2 = MapCorpus(words)
        val wordGen = WordGenerator(mapCorpus2)
        testGenerator3("individual word", wordGen)
    }


    @Test
    fun generateMany() {
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
        val generator3 = StrRandFiltered(slots)
        val wordsGenerator3 = WordsGenerator(slots, generator3)
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
        val mapCorpus = MapCorpus(words)
        repeat(100) {
            val result = wordsGenerator3.generate2 { KnownWordFilter(mapCorpus, it) }
            // Console.info("result", result)
            Console.info("result.strings", result.strings)
        }
    }
}
