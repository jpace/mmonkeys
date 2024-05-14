package org.incava.mmonkeys.exec

import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

typealias TypewriterCtor = (List<Char>) -> Typewriter

class TypewriterFactory(toChar: Char = 'z') {
    private val charList = Keys.keyList(toChar)
    private val ctor: TypewriterCtor = ::Typewriter

    fun create(): Typewriter {
        return ctor(charList)
    }
}