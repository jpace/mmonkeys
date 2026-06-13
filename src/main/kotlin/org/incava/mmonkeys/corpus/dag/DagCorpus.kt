package org.incava.mmonkeys.corpus.dag

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.Corpus

class DagCorpus(words: List<String>) : Corpus(words) {
    val nodes: MutableList<DagNode> = mutableListOf()

    init {
        words.withIndex().forEach { (index, word) ->
            val firstChar = word[0]
            var current = nodes.find { it.char == firstChar }
            if (current == null) {
                current = DagNode(firstChar, null, mutableListOf(), mutableListOf())
                nodes += current
            }
            word.substring(1).forEach { char ->
                var nextNode = current!!.children.find { node -> node.char == char }
                if (nextNode == null) {
                    nextNode = DagNode(char, current, mutableListOf(), mutableListOf())
                    current!!.children += nextNode
                }
                current = nextNode
            }
            current!!.indices += index
        }
    }

    fun toWords(): Map<Int, String> {
        val indexed = mutableMapOf<Int, String>()
        nodes.forEach { node ->
            val words = node.toWords()
            words.forEach { (word, indices) ->
                indices.forEach { index -> indexed[index] = word }
            }
        }
        return indexed
    }

    fun toWordsMap(): Map<String, List<Int>> {
        val map = mutableMapOf<String, MutableList<Int>>()
        nodes.forEach { node ->
            val words = node.toWords()
            words.forEach { (word, indices) ->
                map.computeIfAbsent(word) { mutableListOf() }.addAll(indices)
            }
        }
        return map
    }

    fun findNode(string: String): DagNode? {
        return findNode(string.toCharArray(), nodes)
    }

    override fun findMatch(word: String): Int? {
        val node = findNode(word) ?: return null
        return node.indices.find { !matches.isMatched(it) }
    }

    fun toWords(node: DagNode): Map<Int, String> {
        val words = node.children.fold(mutableMapOf<Int, String>()) { acc, n ->
            acc.also { it += toWords(n) }
        }
        if (node.indices.isNotEmpty()) {
            val word = node.toWord()
            words += node.indices.map { index -> index to word }
        }
        return words
    }

    fun findNode(chars: CharArray, subnodes: List<DagNode>): DagNode? {
        val firstChar = chars[0]
        val n = subnodes.find { it.char == firstChar } ?: return null
        return if (chars.size > 1) {
            val nextChars = CharArray(chars.size - 1) { chars[it + 1] }
            findNode(nextChars, n.children)
        } else {
            n
        }
    }
}