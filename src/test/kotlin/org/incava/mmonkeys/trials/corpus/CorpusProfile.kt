package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.HashMapCorpus
import org.incava.mmonkeys.mky.corpus.LengthCorpus
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.type.Typewriter
import org.incava.rando.RandCalcMap

class CorpusProfile(val numInvokes: Long, private val trialInvokes: Int) {
    private val numChars = 27
    private val typewriter = Typewriter()

    fun profile() {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val words = CorpusFactory.readFileWords(file, -1).filter { it.length > 2 }
        val mapCorpus = MapCorpus(words.toList())
        val hashMapCorpus = HashMapCorpus(words.toList())
        val lengthCorpus1 = LengthCorpus(words.toList())
        val lengthCorpus2 = LengthCorpus(words.toList())
        val profiler = Profiler(numInvokes, trialInvokes)
        val calc1 = RandCalcMap(numChars, 10000)
        var mapMatches = 0
        profiler.add("map") {
            val length = calc1.nextInt()
            val forLength = mapCorpus.lengthToStringsToIndices[length]
            if (forLength != null) {
                val word = getWord(length)
                val x = forLength[word]
                if (x != null) {
                    ++mapMatches
                    mapCorpus.matched(word, length)
                }
            }
        }
        var hashMapMatches = 0
        profiler.add("hash map") {
            val length = calc1.nextInt()
            val forLength = hashMapCorpus.lengthToStringsToIndices[length]
            if (forLength != null) {
                val word = getWord(length)
                val x = forLength[word]
                if (x != null) {
                    ++hashMapMatches
                    hashMapCorpus.matched(word, length)
                }
            }
        }
        var listMatches = 0
        if (true) {
            profiler.add("list - indexOf") {
                val length = calc1.nextInt()
                val forLength = lengthCorpus1.soughtByLength[length]
                if (forLength != null) {
                    val word = getWord(length)
                    val index = forLength.indexOf(word)
                    if (index >= 0) {
                        ++listMatches
                        lengthCorpus1.matched(word, index, length, forLength)
                    }
                }
            }
        }
        var listContainsMatches = 0
        if (true) {
            profiler.add("list - contains") {
                val length = calc1.nextInt()
                val forLength = lengthCorpus2.soughtByLength[length]
                if (forLength != null) {
                    val word = getWord(length)
                    if (forLength.contains(word)) {
                        ++listContainsMatches
                        lengthCorpus2.matched(word, length, forLength)
                    }
                }
            }
        }

        profiler.runAll()

        Console.info("map #", mapMatches)
        Console.info("map %", 100.0 * mapMatches / (numInvokes * trialInvokes))
        Console.info("hashmap #", hashMapMatches)
        Console.info("hashmap %", 100.0 * hashMapMatches / (numInvokes * trialInvokes))
        Console.info("list - index #", listMatches)
        Console.info("list - index %", 100.0 * listMatches / (numInvokes * trialInvokes))
        Console.info("list - has #", listContainsMatches)
        Console.info("list - has %", 100.0 * listContainsMatches / (numInvokes * trialInvokes))

        profiler.showResults()
    }

    fun getWord(length: Int): String {
        return (0 until length).fold(StringBuilder()) { sb, _ ->
            val ch = typewriter.nextWordCharacter()
            sb.append(ch)
        }.toString()
    }
}

fun main() {
    val obj = CorpusProfile(1_000_000L, 5)
    obj.profile()
}