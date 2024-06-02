package org.incava.mmonkeys.match.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData

class LengthCorpusMatcher(monkey: Monkey, sought: Corpus) : CorpusMatcher(monkey, sought) {
    val soughtByLength: MutableMap<Int, MutableList<String>> = mutableMapOf()

    init {
        Console.info("creating corpus")
        Console.info("sought.words.#", sought.words.size)
        sought.words.withIndex().forEach { word ->
            val length = word.value.length
            soughtByLength.computeIfAbsent(length) { mutableListOf() }.also { it.add(word.value) }
        }
    }

    override fun check(): MatchData {
        val verbose = false

        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string
        val toEndOfWord = randomLength()
        val length = toEndOfWord - 1
        val forLength = soughtByLength[length]
        if (forLength != null) {
            val word = monkey.nextChars(length)
            val index = forLength.indexOf(word)
            if (index >= 0) {
                sought.remove(word)
                forLength.removeAt(index)
                if (forLength.isEmpty()) {
                    soughtByLength.remove(length)
                }
//                if (verbose) {
//                    println("not showing matched words")
//                }
                // showUnmatched()
                return match(length, index)
            }
        }
        return noMatch(toEndOfWord)
    }

    private fun showUnmatched() {
        val total = soughtByLength.values.sumOf { it.size }
        val str = soughtByLength.entries
            .sortedBy { (key, _) -> key }
            .joinToString(", ") { it.key.toString() + ": " + it.value.size }
        print("\u001b[H\u001b[2J")
        println("unmatched: $total; $str")
        // @todo - make this toggleable
        // sought.write()
    }
}
