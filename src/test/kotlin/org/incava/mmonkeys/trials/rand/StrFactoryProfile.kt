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

    fun viaByteArrayUnsized() : String {
        val length = randInt(range)
        val bytes = mutableListOf<Byte>()
        repeat(length) { index ->
            val n = randCharAz()
            bytes += ('a' + n).toByte()
        }
        return String(bytes.toByteArray())
    }

    fun viaByteArraySized() : String {
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
        addFilters("byte array sized", ::viaByteArraySized)
        addFilters("byte array unsized", ::viaByteArrayUnsized)
        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = StrFactoryProfile(10_000_000L, 3)
    obj.profile()
}