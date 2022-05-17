package org.incava.mmonkeys.match

open class MatcherTest {
    internal fun charList(first: Char, last: Char): List<Char> {
        return (first..last).toList() + ' '
    }
}