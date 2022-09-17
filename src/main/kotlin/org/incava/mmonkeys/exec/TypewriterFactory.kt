package org.incava.mmonkeys.exec

import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.type.Typewriter

class TypewriterFactory(
    private val charList: List<Char> = ('a'..'z').toList() + ' ',
    private val typewriterType: TypewriterCtor = ::StandardTypewriter,
) {
    fun typewriter(): Typewriter {
        return typewriterType(charList)
    }

    override fun toString(): String {
        return "TypewriterFactory(charList=$charList, typewriterType=$typewriterType)"
    }
}