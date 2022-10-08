package org.incava.mesa

import java.io.PrintStream

class TableX(out: PrintStream = System.out) : Table(out) {
    override fun cells(): List<Triple<String, Class<out Any>, Int>> {
        return listOf(Triple("h1", String::class.java, 4))
    }
}

class TableY : Table() {
    override fun cells(): List<Triple<String, Class<out Any>, Int>> {
        return listOf(
            Triple("elapsed", String::class.java, 7),
            Triple("number", Long::class.java, 14),
            Triple("free", Long::class.java, 6),
            Triple("used", Long::class.java, 6),
            Triple("total", String::class.java, 6),
        )
    }
}
