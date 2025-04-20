package org.incava.mmonkeys.type

object TypewriterFactory {
    fun create() : Typewriter {
        return Typewriter()
    }

    fun create(observer: TypewriterObserver) : ObservedTypewriter {
        return ObservedTypewriter(observer)
    }
}