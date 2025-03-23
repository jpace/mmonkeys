package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.mind.Context
import org.incava.mmonkeys.mky.mind.ThreesStrategyFactory
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.rand.DistributedRandom
import org.incava.mmonkeys.type.Keys
import kotlin.random.Random

object StrategyFactory {
    fun createStrategy(whenEmpty: () -> Char, withContext: (Context) -> Char): ContextStrategy {
        return ContextStrategy(whenEmpty, withContext)
    }

    fun weighted(words: List<String>): () -> Char {
        val byChar = CorpusTraits(words).characterCounts()
        val rnd = DistributedRandom(byChar)
        return {
            rnd.nextRandom()
        }
    }

    fun twoRandom(sequences: Sequences): (Context) -> Char {
        return { context ->
            val lastChar = context.currentChars.last()
            val possible = sequences.presentTwos.getValue(lastChar)
            possible.random()
        }
    }

    fun twosDistributed(sequences: Sequences): (Context) -> Char {
        val randomByChar = sequences.presentTwosCounted.mapValues { (_, second) ->
            DistributedRandom(second)
        }
        return { context ->
            randomByChar.getValue(context.currentChars.last()).nextRandom()
        }
    }

    fun threesRandom(sequences: Sequences): (Context) -> Char {
        return ThreesStrategyFactory.threesRandom(sequences)
    }

    fun random(chars: List<Char> = Keys.fullList()): () -> Char {
        val numChars: Int = chars.size
        return {
            val idx = Random.nextInt(numChars)
            chars[idx]
        }
    }

    fun fullRandom(): TypeStrategy {
        val rnd = random()
        return ContextStrategy(rnd::invoke) { rnd() }
    }

    fun weightedStrategy(words: List<String>): TypeStrategy {
        val x = weighted(words)
        val y: (Context) -> Char = { x() }
        return ContextStrategy(x::invoke, y::invoke)
    }
}