package org.incava.mmonkeys.exec

import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.type.Typewriter

class TypewriterFactory(toChar: Char = 'z') {
    private val charList = Keys.keyList(toChar)
    private val ctor: TypewriterCtor = ::StandardTypewriter

    fun typewriter(): Typewriter {
        return ctor(charList)
    }
}