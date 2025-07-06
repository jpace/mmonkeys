package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.mind.WeightedStrategy
import org.incava.mmonkeys.rand.CharsRandom
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.util.ResourceUtil

class SequencesTrial {
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)

    fun twos() {
        val obj = SequencesFactory.createFromWords(words)
        val result = obj.twos
        result.toSortedMap().forEach { (a, bs) ->
            bs.toSortedMap().forEach { (b, count) ->
                Console.info("$a$b: $count")
            }
        }
        val countToString = result.map { (a, bs) ->
            bs.map { (b, count) ->
                count to "$a$b"
            }
        }.flatten()
        countToString.sortedBy { it.first }.forEach { (count, str) ->
            Console.info("$count: $str")
        }
    }

    fun presentTwosCountedRandom() {
        val obj = SequencesFactory.createFromWords(words)
        val result = obj.twos
        result.keys.toSortedSet().forEach { first ->
            Qlog.info("first: '$first'", first)
            result.getValue(first).toSortedMap().forEach { (second, count) ->
                Console.info("$first -> $second", count)
            }
            val random = CharsRandom(result.getValue(first))
            Qlog.info("random.nextRandom", random.nextDistributedRandom())
            Qlog.info("random.nextRandom", random.nextDistributedRandom())
            Qlog.info("random.nextRandom", random.nextDistributedRandom())
            Qlog.info("random.nextRandom", random.nextDistributedRandom())
        }
        // this is probably the same as weighted ... maybe
        val firstToCount = result.mapValues { it.value.values.sum() }
        val total = firstToCount.values.sum()
        firstToCount.toSortedMap().forEach { (ch, count) ->
            val pct = 100.0 * count / total
            Console.info("firstToCount[$ch]", count)
            Console.info("firstToCount[$ch]", pct)
        }
    }

    fun presentThreesCounted() {
        val obj = SequencesFactory.createFromWords(words)
        val result = obj.threes
        result.map.toSortedMap().forEach { (a, bs) ->
            bs.toSortedMap().forEach { (b, cs) ->
                cs.toSortedMap().forEach { (c, count) ->
                    Console.info("$a$b$c: $count")
                }
            }
        }
        val countToString = result.map.map { (a, bs) ->
            bs.map { (b, cs) ->
                cs.map { (c, count) ->
                    count to "$a$b$c"
                }
            }
        }.flatten().flatten()
        countToString.sortedBy { it.first }.forEach { (count, str) ->
            Console.info("$count: <$str>")
        }
    }

    fun getNext() {
        val sequences = SequencesFactory.createFromWords(words)
        val weightedStrategy = WeightedStrategy(words)
        Qlog.info("weighted", weightedStrategy)
        repeat(10) {
            val word = weightedStrategy.getChars()
            Qlog.info("word", word)
        }

        val sequenceStrategy1 = TwosRandomStrategy(sequences)
        Qlog.info("sequence", sequenceStrategy1)
        repeat(10) {
            val word = sequenceStrategy1.getChars()
            Qlog.info("word", word)
        }
    }
}

fun main() {
    val obj = SequencesTrial()
    obj.presentTwosCountedRandom()
    obj.twos()
    obj.presentThreesCounted()
    obj.getNext()
}