package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.number.RandEncoded

class EncodedGenerator {
    fun getRandomEncoded(numChars: Int): Long {
        return RandEncoded.random(numChars)
    }
}
