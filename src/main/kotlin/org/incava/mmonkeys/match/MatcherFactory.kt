package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.number.NumberIntMatcher
import org.incava.mmonkeys.match.number.NumberLongMatcher
import org.incava.mmonkeys.match.number.NumberLongsMatcher
import org.incava.mmonkeys.match.string.*

typealias MatcherCtor<T> = (Monkey, T) -> Matcher

class MatcherFactory {
    fun createCorpusMatcherCtor(type: String, sought: String): MatcherCtor<String> {
        return when (type) {
            "length" -> ::LengthStringMatcher
            "eq" -> ::EqStringMatcher
            "partial" -> ::PartialStringMatcher
            "number" -> if (sought.length > 6) ::NumberLongMatcher else ::NumberIntMatcher
            else -> ::LengthStringMatcher
        }
    }

    fun createCorpusMatcherCtor(type: String): MatcherCtor<Corpus> {
        return when (type) {
            "length" -> ::LengthStringsMatcher
            "eq" -> ::EqStringsMatcher
            // "partial" -> ::PartialStringMatcher
            "number" -> ::NumberLongsMatcher
            else -> ::EqStringsMatcher
        }
    }
}