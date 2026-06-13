package org.incava.mmonkeys.corpus.dag

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DagCorpusTest {
    fun runFindNode(corpus: DagCorpus, expectedWord: String?, expectedIndices: List<Int>?, word: String) {
        val result = corpus.findNode(word)
        var top = result
        while (top?.parent != null) {
            top = top.parent
        }
        assertEquals(expectedWord, result?.toWord())
        assertEquals(expectedIndices, result?.indices)
    }

    fun runFindNode2(corpus: DagCorpus, expectedWord: String?, expectedIndices: List<Int>?, expectedChildWord: String?, word: String) {
        val result = corpus.findNode(word)
        var top = result
        while (top?.parent != null) {
            top = top.parent
        }
        assertEquals(expectedWord, result?.toWord())
        assertEquals(expectedIndices, result?.indices)
        assertEquals(expectedChildWord, result?.children?.firstOrNull()?.toWord())
    }

    @Test
    fun findNode1() {
        val words = listOf("the", "brown", "dog", "dogs", "the", "three", "blue", "dots")
        val obj = DagCorpus(words)
        val result1 = obj.findNode("dog")
        assertNotNull(result1)
        assertEquals(listOf(2), result1.indices)
        assertEquals(1, result1.children.size)
        assertEquals(listOf(3), result1.children.first().indices)
        assertEquals("dogs", result1.children.first().toWord())

        val result2 = obj.findNode("do")
        assertNotNull(result2)
        assertEquals(listOf(), result2.indices)
        assertEquals(2, result2.children.size)
        assertEquals("dog", result2.children[0].toWord())
        assertEquals(listOf(2), result2.children[0].indices)
        assertEquals("dogs", result2.children[0].children[0].toWord())
        assertEquals(listOf(3), result2.children[0].children[0].indices)
        assertEquals("dot", result2.children[1].toWord())
        assertEquals(listOf(), result2.children[1].indices)
        assertEquals("dots", result2.children[1].children[0].toWord())
        assertEquals(listOf(7), result2.children[1].children[0].indices)
    }

    @Test
    fun findNode() {
        val words = listOf("the", "brown", "dog", "dogs", "the", "three", "blue", "dots")
        val obj = DagCorpus(words)
        runFindNode(obj, "dog", listOf(2), "dog")
        runFindNode2(obj, "dog", listOf(2), "dogs", "dog")
        runFindNode(obj, "dogs", listOf(3), "dogs")
        runFindNode(obj, "three", listOf(5), "three")
        runFindNode(obj, "three", listOf(5), "three")
        runFindNode(obj, null, null, "xjklrfx")
    }

    @Test
    fun toWords() {
        val words = listOf("the", "brown", "dog", "dogs", "the", "three", "blue", "dots")
        val obj = DagCorpus(words)
        val result = obj.toWords().toSortedMap()
       assertEquals(words, result.values.toList())
    }

    @Test
    fun toWordsMap() {
        val words = listOf("the", "brown", "dog", "dogs", "the", "three", "blue", "dots")
        val obj = DagCorpus(words)
        val result = obj.toWordsMap().toSortedMap()
        val expected = mapOf("blue" to listOf(6), "brown" to listOf(1), "dog" to listOf(2), "dogs" to listOf(3), "dots" to listOf(7), "the" to listOf(0, 4), "three" to listOf(5))
        assertEquals(expected, result)
    }

    fun expandNode(node: DagNode, indent: Int, showIndices: Boolean = false) {
        val spaces = " ".repeat(indent * 2)
        println("$spaces -> ${node.char}")
        node.children.forEach { expandNode(it, indent + 1) }
        if (showIndices) {
            node.indices.forEach {
                val ispaces = " ".repeat((indent + 1) * 2)
                println("$ispaces .-> $it")
            }
        }
    }

    @Disabled("only for viewing")
    @Test
    fun expandNode() {
        val words = listOf("the", "brown", "dog", "dogs", "the", "three", "blue", "dots")
        val obj = DagCorpus(words)
        obj.nodes.forEach { expandNode(it, 0, true) }
    }
}