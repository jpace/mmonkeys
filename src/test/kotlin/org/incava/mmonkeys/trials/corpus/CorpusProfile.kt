package org.incava.mmonkeys.trials.corpus

import org.incava.confile.Profiler
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.HashMapCorpus
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.type.Typewriter
import org.incava.rando.RandSlotsFactory

class CorpusProfile(val numInvokes: Long, private val trialInvokes: Int) {
    private val numChars = 27
    private val typewriter = Typewriter()

    fun profile() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1).filter { it.length > 2 }
        val linkedHashMapCorpus = MapCorpus(words.toList())
        val hashMapCorpus = HashMapCorpus(words.toList())
        val profiler = Profiler(numInvokes, trialInvokes)
        val calc1 = RandSlotsFactory.calcMap(numChars, 100, 10000)
        var mapMatches = 0
        run {
            profiler.add("linked hash map") {
                val length = calc1.nextInt()
                val forLength = linkedHashMapCorpus.lengthToStringsToIndices[length]
                if (forLength != null) {
                    val word = getWord(length)
                    val x = forLength[word]
                    if (x != null) {
                        ++mapMatches
                        linkedHashMapCorpus.matched(word, length)
                    }
                }
            }
        }
        var hashMapMatches = 0
        run {
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
        }

        profiler.runAll()

        Console.info("linked hash map #", mapMatches)
        Console.info("linked hash map %", 100.0 * mapMatches / (numInvokes * trialInvokes))
        Console.info("hash map #", hashMapMatches)
        Console.info("hash map %", 100.0 * hashMapMatches / (numInvokes * trialInvokes))

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