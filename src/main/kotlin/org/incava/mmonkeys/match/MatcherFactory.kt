package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.number.NumberIntMatcher
import org.incava.mmonkeys.match.number.NumberLongMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.LengthStringMatcher
import org.incava.mmonkeys.match.string.PartialStringMatcher

typealias MatcherCtor<T> = (Monkey, T) -> Matcher

class MatcherFactory {
    fun createMatcherCtor(type: String, sought: String): MatcherCtor<String> {
        return when (type) {
            "length" -> ::LengthStringMatcher
            "eq" -> ::EqStringMatcher
            "partial" -> ::PartialStringMatcher
            "number" -> if (sought.length > 6) ::NumberLongMatcher else ::NumberIntMatcher
            else -> ::LengthStringMatcher
        }
    }
}