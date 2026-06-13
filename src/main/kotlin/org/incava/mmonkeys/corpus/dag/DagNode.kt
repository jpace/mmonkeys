package org.incava.mmonkeys.corpus.dag

data class DagNode(val char: Char, val parent: DagNode?, val children: MutableList<DagNode>, val indices: MutableList<Int>) {
    override fun toString(): String {
        return "Node(char=$char, parent.char=${parent?.char}, children.#=${children.size}, indices.#=${indices.size})"
    }

    fun toWord(): String {
        val str = char.toString()
        return if (parent == null) str else parent.toWord() + str
    }

    fun toWords(): Map<String, List<Int>> {
        val words = mutableMapOf<String, List<Int>>()
        val word = toWord()
        indices.forEach { index -> words[word] = (words[word] ?: emptyList()) + index }
        children.forEach { words += it.toWords() }
        return words
    }
}