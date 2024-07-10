package org.incava.mmonkeys.mky.number

import org.incava.ikdk.math.Maths
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.string.StringMonkey
import org.incava.mmonkeys.type.Typewriter
import kotlin.random.Random

class NumberLongMonkey(sought: String, id: Int, typewriter: Typewriter) : StringMonkey(sought, id, typewriter) {
    private val number = StringEncoderV1.encodeToLong(sought)
    private val soughtLen = sought.length
    private val max = Maths.powerLongRepeat(typewriter.numChars().toLong(), soughtLen) * 2

    override fun check(): MatchData {
        val length = randomLength()
        if (length == soughtLen + 1) {
            val num = Random.nextLong(max)
            if (num == number) {
                return match(length)
            }
        }
        return noMatch(length - 1)
    }
}