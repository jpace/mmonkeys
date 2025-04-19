package org.incava.mmonkeys.type

object TypewriterFactory {
    fun create() : Typewriter {
        return Typewriter(Keys.fullList())
    }

    fun create(observer: (List<Char>) -> Unit) : ObservedTypewriter {
        return ObservedTypewriter(Keys.fullList(), observer)
    }
}