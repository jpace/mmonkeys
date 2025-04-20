package org.incava.mmonkeys.type

import org.incava.ikdk.io.Qlog

open class TypewriterObserver {
    open fun charsTyped(chars: List<Char>) {
        Qlog.info("chars", chars)
    }

    open fun numCharsTyped(numChars: Int) {
        Qlog.info("numChars", numChars)
    }
}