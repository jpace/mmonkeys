package org.incava.mmonkeys.match.number

import org.incava.ikdk.math.Maths
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.string.StringMonkey
import org.incava.mmonkeys.type.Typewriter
import kotlin.random.Random

/**
 * Handles strings up through length of 6.
 */
class NumberIntMonkey(sought: String, id: Int, typewriter: Typewriter) : StringMonkey(sought, id, typewriter) {
    private val number = StringEncoderV1.encodeToInt(sought)
    private val soughtLen = sought.length
    private val max = Maths.powerIntRepeat(typewriter.numChars(), soughtLen) * 2

    override fun check(): MatchData {
        // thus length == 1 means we'll hit at the first invocation, with
        // number of keystrokes at which we'll hit the end-of-word character
        // an empty string
        val length = randomLength()
        if (length == soughtLen + 1) {
            val num = Random.nextInt(max)
            if (num == number) {
                return match(length)
            }
        }
        return noMatch(length)
    }
}