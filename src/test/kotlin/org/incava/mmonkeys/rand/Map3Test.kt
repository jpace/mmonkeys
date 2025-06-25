package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.junit.jupiter.api.Test

class Map3Test {
    @Test
    fun init() {
        val obj = Map3<Char, Int>()
        Qlog.info("obj", obj)
        obj.add('a', 'b', 'c', 14)
        obj.add('a', 'b', 'x', 1)
        obj.add('a', 'j', 'h', 42)
        Qlog.info("obj", obj)
        Qlog.info("obj", obj.fetch('a', 'b', 'c'))
        Qlog.info("obj", obj.fetch('a', 'b', 'x'))
    }
}