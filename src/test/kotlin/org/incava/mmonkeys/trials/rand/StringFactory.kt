package org.incava.mmonkeys.trials.rand

object StringFactory {
    fun buildString(length: Int, charProvider: () -> Int): String {
        val sb = StringBuilder()
        repeat(length) {
            val ch = charProvider()
            sb.append('a' + ch)
        }
        return sb.toString()
    }

    fun bufferString(length: Int, charProvider: () -> Int): String {
        val sb = StringBuffer(length)
        repeat(length) {
            val n = charProvider()
            sb.append('a' + n)
        }
        return sb.toString()
    }

    fun assembleString(length: Int, charProvider: () -> Int): String {
        val bytes = ByteArray(length)
        repeat(length) { index ->
            val n = charProvider()
            bytes[index] = ('a' + n).toByte()
        }
        return String(bytes)
    }
}