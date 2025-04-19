package org.incava.mmonkeys.type

import org.incava.ikdk.io.Qlog

open class ObservedTypewriter(chars: List<Char>, val observer: (List<Char>) -> Unit) : Typewriter(chars) {
    override fun typeChars(chars: List<Char>) {
        Qlog.info("chars", chars)
        observer(chars)
    }
}