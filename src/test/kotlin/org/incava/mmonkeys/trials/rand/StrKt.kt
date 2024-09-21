package org.incava.mmonkeys.trials.rand

import kotlin.random.Random

class StrKt : StrRand() {
    override fun randInt(limit: Int) = Random.nextInt(limit)

    override fun get(): String {
        val sb = StringBuilder()
        while (true) {
            val n = randCharAzSpace()
            if (n == Constants.NUM_CHARS) {
                return sb.toString()
            } else {
                sb.append('a' + n)
            }
        }
    }

    override fun get(filter: Int): String {
        val sb = StringBuilder()
        while (true) {
            val n = randCharAzSpace()
            if (n == Constants.NUM_CHARS) {
                return sb.toString()
            } else {
                sb.append('a' + n)
                if (sb.length > filter) {
                    return ""
                }
            }
        }
    }
}