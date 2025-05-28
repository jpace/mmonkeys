package org.incava.mmonkeys.corpus

import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder
import kotlin.random.Random

class RandomsProvider(val words: List<String>) {
    val chars = ('a'..'z').toList()

    fun getRandoms(maxLength: Int): List<String> {
        val numTotal = 1_000_000
        val numValid = 1_000
        return (0..numTotal).fold(mutableListOf<String>()) { list, _ ->
            val length = Random.Default.nextInt(maxLength)
            val word = (0..length).fold(StringBuilder()) { sb, _ -> sb.append(chars.random()) }.toString()
            list.also { it += word }
        } + (0..numValid).fold(mutableListOf()) { list, _ -> list.also { it += words.random() } }
    }

    fun getRandomsEncoded(maxLength: Int): List<Pair<Long, Int>> {
        return getRandoms(maxLength).map { StringEncoder.encodeToLong(it) to it.length }
    }

    fun getRandomsAny(maxLength: Int): List<Pair<Any, Int>> {
        return getRandoms(maxLength).map {
            if (it.length > RandEncoded.Constants.MAX_ENCODED_CHARS) {
                StringEncoder.encodeToLong(it) to it.length
            } else {
                it to it.length
            }
        }
    }
}