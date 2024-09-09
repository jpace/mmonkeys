package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.trials.base.SortType
import kotlin.random.Random


class StrFactoryProfile(numInvokes: Long, trialInvokes: Int) {
    private val profiler = Profiler(numInvokes, trialInvokes)
    private val range = 100

    fun randInt(limit: Int) = Random.nextInt(limit)
    fun randCharAz() = randInt(StrRand.Constants.NUM_CHARS)

    fun viaStringBuilderUnsized() : String {
        val length = randInt(range)
        val sb = StringBuilder()
        repeat(length) {
            val n = randCharAz()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    fun viaStringBuilderSized() : String {
        val length = randInt(range)
        val sb = StringBuilder(length)
        repeat(length) {
            val n = randCharAz()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    fun viaStringBufferUnsized() : String {
        val length = randInt(range)
        val sb = StringBuffer()
        repeat(length) {
            val n = randCharAz()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    fun viaStringBufferSized() : String {
        val length = randInt(range)
        val sb = StringBuffer(length)
        repeat(length) {
            val n = randCharAz()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    fun viaByteArray() : String {
        val length = randInt(range)
        val bytes = ByteArray(length)
        repeat(length) { index ->
            val n = randCharAz()
            bytes[index] = ('a' + n).toByte()
        }
        return String(bytes)
    }

    private fun addFilters(name: String, generator: () -> String) {
        profiler.add(name) { generator() }
    }

    fun profile() {
        addFilters("builder sized", ::viaStringBuilderSized)
        addFilters("builder unsized", ::viaStringBuilderUnsized)
        addFilters("buffer sized", ::viaStringBufferSized)
        addFilters("buffer unsized", ::viaStringBufferUnsized)
        addFilters("byte array", ::viaByteArray)
        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = StrFactoryProfile(50_000_000L, 3)
    obj.profile()
}