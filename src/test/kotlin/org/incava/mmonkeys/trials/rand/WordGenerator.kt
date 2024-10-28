package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.trials.rand.StrRand.Constants.NUM_CHARS
import kotlin.random.Random

/**
 * Generates words without doing a simulated length check.
 */
class WordGenerator(val corpus: MapCorpus) {
    private val maxLen = corpus.lengths.maxOrNull() ?: throw RuntimeException("no max")

    fun generate(): Pair<Int, Long> {
        val current = StringBuilder()
        var keystrokes = 0L
        while (true) {
            ++keystrokes
            val ch = Random.nextInt(NUM_CHARS + 1)
            // that is, a space:
            if (ch == NUM_CHARS) {
                // @todo - fix this -- should return whether a word matches,
                // and look at the MapCorpus (for length), not Corpus
                return corpus.match(current.toString()) to keystrokes
            } else {
                current.append('a' + ch)
                if (!hasCandidate(current)) {
                    return -1 to keystrokes
                }
            }
        }
    }

    private fun hasCandidate(current: StringBuilder) : Boolean {
        val curStr = current.toString()
        (current.length .. maxLen).forEach { length ->
            val forLength = corpus.forLength(length)
            forLength?.keys?.forEach {
                if (it.startsWith(curStr)) {
                    return true
                }
            }
        }
        return false
    }
}