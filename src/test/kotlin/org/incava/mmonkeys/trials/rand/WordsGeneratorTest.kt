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
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val words = CorpusFactory.readFileWords(file, 100).filter { it.length in 1..12 }
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

    @Test
    fun filtered() {
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
        val generator = StrRandFiltered(slots)
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val words = CorpusFactory.readFileWords(file, 100).filter { it.length in 1..12 }
        val wordsGenerator = WordsGenerator(slots, generator)
        val noTwos = listOf('j', 'k', 'q', 'v', 'w', 'x', 'y')
        val filter = object : GenFilter {
            var prev: Char? = null

            override fun check(ch: Char): Boolean {
                Console.info("prev", prev)
                Console.info("ch", ch)

                if (ch == prev && noTwos.contains(ch)) {
                    Console.info("stopping!")
                    return false
                } else {
                    prev = ch
                    return true
                }
            }
        }
        val generated = wordsGenerator.generateWord(12, filter)
        Console.info("generated", generated)
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
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
        // val generator1 = StrToggleAnyRand(slots)
        val strRand = StrRandFactory.create(128, StrRandFactory.calcArray, StrRandFactory.assemble)
        val strRandFiltered = StrRandFiltered(slots)
        // val wordsGenerator1 = WordsGenerator(slots, generator1)
        val randGen = WordsGenerator(slots, strRand)
        val filteredGen = WordsGenerator(slots, strRandFiltered)
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val words = CorpusFactory.readFileWords(file, -1)
        val filter = CorpusFilter(words)
        val mapCorpus = MapCorpus(words)
        val wordGen = WordGenerator(mapCorpus)

        // testGenerator("toggle any rand", wordsGenerator1, words)
        testGenerator("calc array assemble", randGen, words)
        testGenerator("filtered", filteredGen, words)
        testGenerator("repeat 2 chars", filteredGen, words, ::RepeatCharFilter)
        testGenerator("repeat 2,3 chars", filteredGen, words) { RepeatCharFilter2(filter) }
        testGenerator2("known word", filteredGen, words) { KnownWordFilter(mapCorpus, it) }
        testGenerator3("individual word", wordGen)
    }


    @Test
    fun generateMany() {
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
        val generator3 = StrRandFiltered(slots)
        val wordsGenerator3 = WordsGenerator(slots, generator3)
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val words = CorpusFactory.readFileWords(file, -1)
        val mapCorpus = MapCorpus(words)
        repeat(100) {
            val result = wordsGenerator3.generate2 { KnownWordFilter(mapCorpus, it) }
            // Console.info("result", result)
            Console.info("result.strings", result.strings)
        }
    }

}
