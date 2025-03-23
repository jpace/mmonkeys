package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.type.Keys
import kotlin.random.Random

object ThreesStrategyFactory {
    private fun threesRandom(sequences: Sequences, firstChar: Char, secondChar: Char): Char {
        val fallback = random()
        val forFirst = sequences.presentThrees[firstChar]
        return if (forFirst == null) {
            fallback()
        } else {
            val forSecond = forFirst[secondChar]
            forSecond?.random() ?: fallback()
        }
    }

    private fun threesRandom(sequences: Sequences, firstChar: Char): Char {
        val fallback = random()
        val forFirst = sequences.presentThrees[firstChar]
        return forFirst?.keys?.random() ?: fallback()
    }

    fun threesRandom(sequences: Sequences): (Context) -> Char {
        return { context ->
            if (context.currentChars.size > 1) {
                val firstChar = context.currentChars[context.currentChars.size - 2]
                val secondChar = context.currentChars[context.currentChars.size - 1]
                threesRandom(sequences, firstChar, secondChar)
            } else {
                val lastChar = context.currentChars[context.currentChars.size - 1]
                threesRandom(sequences, lastChar)
            }
        }
    }

    fun random(chars: List<Char> = Keys.fullList()): () -> Char {
        val numChars: Int = chars.size
        return {
            val idx = Random.nextInt(numChars)
            chars[idx]
        }
    }
}