package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.rand.DistributedRandom
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil

class SequencesTrial {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
    // val words = listOf("this", "is", "a", "test")

    fun presentTwos() {
        val obj = Sequences(words)
        val result = obj.presentTwos
        result.forEach { (a, bs) ->
            bs.forEach { b ->
                Console.info("$a$b")
            }
        }
    }

    fun presentTwosCounted() {
        val obj = Sequences(words)
        val result = obj.presentTwosCounted
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
        val obj = Sequences(words)
        val result = obj.presentTwosCounted
        val paired = result.map { a -> a.value.map { b -> (a.key to b.key) to b.value } }.flatten().toMap()
//        paired.mapKeys { it.key.first.toString() + it.key.second }.toSortedMap().forEach { (chars, count) ->
//            Console.info("'$chars'", count)
//        }
        result.keys.toSortedSet().forEach { first ->
            Qlog.info("first: '$first'", first)
            result.getValue(first).toSortedMap().forEach { (second, count) ->
                Console.info("$first -> $second", count)
            }
            val random = DistributedRandom(result.getValue(first))
            Qlog.info("random.nextRandom", random.nextRandom())
            Qlog.info("random.nextRandom", random.nextRandom())
            Qlog.info("random.nextRandom", random.nextRandom())
            Qlog.info("random.nextRandom", random.nextRandom())
        }
        // this is probably the same as weighted ... maybe
        val firstToCount = result.mapValues { it.value.values.sum() }
        val total = firstToCount.values.sum()
        firstToCount.toSortedMap().forEach { (ch, count) ->
            val pct = 100.0 * count / total
            Console.info("firstToCount[$ch]", count)
            Console.info("firstToCount[$ch]", pct)
        }

        val random = DistributedRandom(paired)

    }

    fun presentThrees() {
        val obj = Sequences(words)
        obj.presentThrees.forEach { (a, bs) ->
            bs.forEach { (b, cs) ->
                cs.forEach { c ->
                    Console.info("$a$b$c")
                }
            }
        }
    }

    fun presentThreesCounted() {
        val obj = Sequences(words)
        val result = obj.presentThreesCounted
        result.toSortedMap().forEach { (a, bs) ->
            bs.toSortedMap().forEach { (b, cs) ->
                cs.toSortedMap().forEach { (c, count) ->
                    Console.info("$a$b$c: $count")
                }
            }
        }
        val countToString = result.map { (a, bs) ->
            bs.map { (b, cs) ->
                cs.map { (c, count) ->
                    count to "$a$b$c"
                }
            }
        }.flatten().flatten()
        countToString.sortedBy { it.first }.forEach { (count, str) ->
            Console.info("$count: $str")
        }
    }

    fun getNext() {
        val sequences = Sequences(words)
        val weightedStrategy = StrategyFactory.weighted(words)
        Qlog.info("weighted", weightedStrategy)
        repeat(10) {
            val word = weightedStrategy()
            Qlog.info("word", word)
        }

        val twosStrategy = StrategyFactory.twoRandom(sequences)
        val sequenceStrategy1 = ContextStrategy(weightedStrategy, twosStrategy)
        Qlog.info("sequence (weighted)", sequenceStrategy1)
        repeat(10) {
            val word = sequenceStrategy1.typeWord()
            Qlog.info("word", word)
        }

        val randomStrategy = StrategyFactory.random(Keys.fullList())
        val sequenceStrategy2 = ContextStrategy(randomStrategy, twosStrategy)
        Qlog.info("sequence (random)", sequenceStrategy2)
        repeat(10) {
            val word = sequenceStrategy2.typeWord()
            Qlog.info("word", word)
        }
    }
}

fun main() {
    val obj = SequencesTrial()
    obj.presentTwos()
    obj.presentTwosCounted()
    obj.presentThrees()
    obj.presentThreesCounted()
    obj.getNext()
}