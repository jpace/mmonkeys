package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS

class StrJdk : StrRand() {
    private val random = java.util.Random()

    override fun randInt(limit: Int) = random.nextInt(limit)

    override fun get(): String {
        val sb = StringBuilder()
        while (true) {
            val n = randCharAzSpace()
            if (n == NUM_CHARS) {
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
            if (n == NUM_CHARS) {
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