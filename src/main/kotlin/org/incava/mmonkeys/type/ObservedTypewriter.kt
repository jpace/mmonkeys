package org.incava.mmonkeys.type

import org.incava.ikdk.io.Qlog

open class ObservedTypewriter(private val observer: TypewriterObserver) : Typewriter() {
    override fun typeChars(chars: List<Char>) {
        Qlog.info("chars", chars)
        super.typeChars(chars)
        observer.charsTyped(chars)
    }

    override fun typeChars(numChars: Int) {
        Qlog.info("numChars", numChars)
        super.typeChars(numChars)
        observer.numCharsTyped(numChars)
    }
}