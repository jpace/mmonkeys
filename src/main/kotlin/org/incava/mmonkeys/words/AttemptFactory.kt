package org.incava.mmonkeys.words

object AttemptFactory {
    fun failed(keyStrokes: Int) = Attempt(keyStrokes.toLong())
    fun failed(totalKeyStrokes: Long, numAttempts: Int) = Attempts(emptyList(), totalKeyStrokes, numAttempts)
    fun failed(str: String) = Attempt(str.length.toLong() + 1)
    fun succeeded(word: Word) = Attempt(word, word.string.length.toLong() + 1)
    fun succeeded(words: List<Word>, totalKeyStrokes: Long, numAttempts: Int) = Attempts(words, totalKeyStrokes, numAttempts)
}