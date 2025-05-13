package org.incava.mmonkeys.corpus.impl

import org.incava.ikdk.io.Qlog
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DagCorpusTest {
    @Test
    fun findNode() {
        val words = listOf("the", "brown", "dog", "dogs", "the", "three", "blue", "dots")
        val obj = DagCorpus(words)
        val n1 = obj.findNode("dog")
        Qlog.info("n1", n1)
        Qlog.info("n1", n1?.toWord())
        assertEquals("dog", n1!!.toWord())
        assertEquals(listOf(2), n1.indices)
        val n2 = obj.findNode("three")
        Qlog.info("n2", n2)
        Qlog.info("n2", n2?.toWord())
        assertEquals("three", n2!!.toWord())
        assertEquals(listOf(5), n2.indices)
        val n3 = obj.findNode("xjklrfx")
        Qlog.info("n3", n3)
        Qlog.info("n3", n3?.toWord())
        assertNull(n3)
    }

    @Test
    fun toWords() {
        val words = listOf("the", "brown", "dog", "dogs", "the", "three", "blue", "dots")
        val obj = DagCorpus(words)
        val result = obj.toWords().toSortedMap()
        Qlog.info("result", result.toString())
        assertEquals(words, result.values.toList())
    }

    fun expandNode(node: Node, indent: Int) {
        val spaces = " ".repeat(indent * 2)
        println("$spaces${node.char}")
        node.children.forEach { expandNode(it, indent + 1) }
        node.indices.forEach {
            val ispaces = " ".repeat((indent + 1) * 2)
            println("$ispaces$it")
        }
    }

    @Test
    fun expandNode() {
        val words = listOf("the", "brown", "dog", "dogs", "the", "three", "blue", "dots")
        val obj = DagCorpus(words)
        obj.nodes.forEach { expandNode(it, 0) }
    }
}