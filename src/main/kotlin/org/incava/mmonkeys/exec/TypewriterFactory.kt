package org.incava.mmonkeys.exec

import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.type.Typewriter

class TypewriterFactory(
    private val toChar: Char = 'z',
    private val typewriterType: TypewriterCtor = ::StandardTypewriter,
    private val charList: List<Char> = Keys.keyList(toChar)
) {
    fun typewriter(): Typewriter {
        return typewriterType(charList)
    }

    override fun toString(): String {
        return "TypewriterFactory(toChar=$toChar, typewriterType=$typewriterType)"
    }
}