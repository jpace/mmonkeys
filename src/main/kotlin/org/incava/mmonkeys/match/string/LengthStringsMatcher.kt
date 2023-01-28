package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher

class LengthStringsMatcher(monkey: Monkey, sought: Corpus) : CorpusMatcher(monkey, sought) {
    val soughtByLength: MutableMap<Int, MutableList<String>> = mutableMapOf()

    init {
        sought.words.withIndex().forEach { word ->
            val length = word.value.length
            soughtByLength.computeIfAbsent(length) { mutableListOf() }.also { it.add(word.value) }
        }
    }

    override fun check(): MatchData {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string
        val toEndOfWord = rand.nextRand()
        val length = toEndOfWord - 1
        val forLength = soughtByLength[length]
        if (forLength != null) {
            val word = monkey.nextWordChars(length)
            val index = forLength.indexOf(word)
            if (index >= 0) {
                sought.remove(word)
                forLength.removeAt(index)
                if (forLength.isEmpty()) {
                    soughtByLength.remove(length)
                }
                showUnmatched()
                return match(length, index)
            }
        }
        return noMatch(toEndOfWord)
    }

    fun showUnmatched() {
        val total = soughtByLength.entries.fold(0) { sum, entry -> sum + entry.value.size }
        val str = soughtByLength.entries
            .sortedBy { (key, _) -> key }
            .joinToString(", ") { it.key.toString() + ": " + it.value.size }
        println("total: $total; $str")
        sought.write()
    }
}
