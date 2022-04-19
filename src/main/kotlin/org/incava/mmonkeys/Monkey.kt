package org.incava.mmonkeys

class Monkey(val id: Int, private val typewriter: Typewriter) {
    fun run(text: String, maxIterations: Long): Long {
        var matched = -1
        for (iteration in 0 until maxIterations) {
            val ch = typewriter.randomCharacter()
            if (text[matched + 1] == ch) {
                matched += 1
                if (matched + 1 == text.length) {
                    return iteration
                }
            } else {
                matched = -1
            }
        }
        return -1L
    }
}
