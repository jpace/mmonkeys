package org.incava.mmonkeys

class Monkey(val id: Int, private val typewriter: Typewriter) {
    fun run(text: String, maxIterations: Long = Long.MAX_VALUE): Long {
        var current = 0
        for (iteration in 0 until maxIterations) {
            val ch = nextChar()
            if (text[current] == ch) {
                ++current
                if (current == text.length) {
                    return iteration
                }
            } else {
                current = 0
            }
        }
        return -1L
    }

    private fun nextChar(): Char {
        return typewriter.randomCharacter()
    }

    fun nextWord(): Word {
        val word = Word()
        var ch = nextChar()
        while (ch != ' ') {
            word += ch
            ch = nextChar()
        }
        return word
    }
}
