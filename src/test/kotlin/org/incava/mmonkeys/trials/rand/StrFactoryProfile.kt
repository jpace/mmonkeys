package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.mmonkeys.type.Chars
import kotlin.random.Random

class StrFactoryProfile(numInvokes: Long, trialInvokes: Int) {
    private val profiler = Profiler(numInvokes, trialInvokes)
    private val range = 100

    fun randInt(limit: Int) = Random.nextInt(limit)

    fun `string builder unsized`() : String {
        val length = randInt(range)
        val sb = StringBuilder()
        repeat(length) {
            val n = Chars.randCharAz()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    fun `string builder sized`() : String {
        val length = randInt(range)
        val sb = StringBuilder(length)
        repeat(length) {
            val n = Chars.randCharAz()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    fun `string buffer unsized`() : String {
        val length = randInt(range)
        val sb = StringBuffer()
        repeat(length) {
            val n = Chars.randCharAz()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    fun `string buffer sized`() : String {
        val length = randInt(range)
        val sb = StringBuffer(length)
        repeat(length) {
            val n = Chars.randCharAz()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    fun `byte list`() : String {
        val length = randInt(range)
        val bytes = mutableListOf<Byte>()
        repeat(length) {
            val n = Chars.randCharAz()
            bytes += ('a' + n).toByte()
        }
        return String(bytes.toByteArray())
    }

    fun `byte array`() : String {
        val length = randInt(range)
        val bytes = ByteArray(length)
        repeat(length) { index ->
            val n = Chars.randCharAz()
            bytes[index] = ('a' + n).toByte()
        }
        return String(bytes)
    }

    fun `char list`() : String {
        val length = randInt(range)
        val chars = mutableListOf<Char>()
        repeat(length) {
            val n = Chars.randCharAz()
            chars += 'a' + n
        }
        return chars.joinToString()
    }

    fun `char array`() : String {
        val length = randInt(range)
        val chars = CharArray(length)
        repeat(length) { index ->
            val n = Chars.randCharAz()
            chars[index] = 'a' + n
        }
        return chars.joinToString()
    }

    private fun addFilters(name: String, generator: () -> String) {
        profiler.add(name) { generator() }
    }

    fun profile() {
        addFilters("buffer sized", ::`string buffer sized`)
        addFilters("buffer unsized", ::`string buffer unsized`)
        addFilters("builder sized", ::`string builder sized`)
        addFilters("builder unsized", ::`string builder unsized`)
        addFilters("byte array", ::`byte array`)
        addFilters("byte list", ::`byte list`)
        addFilters("char array", ::`char array`)
        addFilters("char list", ::`char list`)
        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = StrFactoryProfile(10_000_000L, 3)
    obj.profile()
}