package org.incava.mmonkeys.words

class Words(words: List<Word>, totalKeyStrokes: Long, numAttempts: Int) : Attempt(words, totalKeyStrokes, numAttempts) {
    constructor(totalKeyStrokes: Long, numAttempts: Int) : this(emptyList(), totalKeyStrokes, numAttempts)
}
