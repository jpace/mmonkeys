package org.incava.mmonkeys.corpus.impl

data class Node(val char: Char, val parent: Node?, val children: MutableList<Node>, val indices: MutableList<Int>) {
    override fun toString(): String {
        return "Node(char=$char, parent.char=${parent?.char}, children=$children, indices=$indices)"
    }

    fun toWord(): String {
        val str = char.toString()
        return if (parent == null) str else parent.toWord() + str
    }
}

class DagCorpus(words: List<String>) {
    val nodes: MutableList<Node> = mutableListOf()

    init {
        words.withIndex().forEach { (index, word) ->
            val firstChar = word[0]
            var current = nodes.find { it.char == firstChar }
            if (current == null) {
                current = Node(firstChar, null, mutableListOf(), mutableListOf())
                nodes += current
            }
            word.substring(1).forEach { char ->
                var nextNode = current!!.children.find { node -> node.char == char }
                if (nextNode == null) {
                    nextNode = Node(char, current, mutableListOf(), mutableListOf())
                    current!!.children += nextNode
                }
                current = nextNode
            }
            current!!.indices += index
        }
    }

    fun toWords(): Map<Int, String> {
        val indexed = mutableMapOf<Int, String>()
        nodes.forEach { indexed += toWords(it) }
        return indexed
    }

    fun findNode_orig(string: String): Node? {
        return findNode(string, nodes)
    }

    fun findNode(string: String): Node? {
        return findNode(string.toCharArray(), nodes)
    }

    private fun toWords(node: Node): Map<Int, String> {
        val words = node.children.fold(mutableMapOf<Int, String>()) { acc, n ->
            acc += toWords(n)
            acc
        }
        if (node.indices.isNotEmpty()) {
            val word = node.toWord()
            words += node.indices.map { index -> index to word }
        }
        return words
    }

    private fun findNode(string: String, subnodes: List<Node>): Node? {
        val firstChar = string[0]
        val n = subnodes.find { it.char == firstChar } ?: return null
        return if (string.length > 1) {
            val nextChars = string.substring(1)
            findNode(nextChars, n.children)
        } else {
            n
        }
    }

    private fun findNode(chars: CharArray, subnodes: List<Node>): Node? {
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