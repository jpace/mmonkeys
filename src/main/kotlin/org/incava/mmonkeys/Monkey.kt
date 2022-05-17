package org.incava.mmonkeys

class Monkey(val id: Int, private val typewriter: Typewriter) {
    private fun nextChar(): Char {
        return typewriter.nextCharacter()
    }

    fun nextWord(): Word {
        val word = Word()
        while (true) {
            val ch = nextChar()
            if (ch == ' ') {
                return word
            } else {
                word += ch
            }
        }
    }

    fun nextString(): String {
        val builder = StringBuilder()
        while (true) {
            val ch = nextChar()
            if (ch == ' ') {
                return builder.toString()
            } else {
                builder.append(ch)
            }
        }
    }

    override fun toString(): String {
        return "Monkey(id=$id, typewriter=$typewriter)"
    }
}
