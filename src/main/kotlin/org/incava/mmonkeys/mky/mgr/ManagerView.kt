package org.incava.mmonkeys.mky.mgr

import org.incava.mmonkeys.mky.Monkey

class ManagerView(private val simMatchView: SimMatchView) {

    fun addMatch(monkey: Monkey, index: Int, matchCount: Int, totalKeystrokes: Long) {
        simMatchView.update(monkey, index, matchCount, totalKeystrokes)
    }
}
