package org.incava.mmonkeys.mky.number

import org.incava.ikdk.math.Maths
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.string.StringMonkey
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