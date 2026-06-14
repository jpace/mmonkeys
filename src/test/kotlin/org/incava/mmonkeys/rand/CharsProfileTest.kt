package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.math.roundToInt

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CharsProfileTest {
    lateinit var obj: CharsProfile

    @BeforeAll
    fun setUp() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val sequences = SequencesFactory.createFromWords(words)
        val threes = sequences.threes
        obj = CharsProfile(threes)
    }

    fun getSlot(slots: Map<Char, Double>, ch: Char): Int = ((slots[ch] ?: 0.0) * 100).roundToInt()

    @Disabled("only for manual inspection")
    @Test
    fun listSlotsFirsts() {
        Qlog.info("obj", obj)
        Qlog.info("obj.firsts.slots", obj.firsts.slots)
    }

    @Test
    fun getSlotsFirsts() {
        val slots1 = obj.firsts.slots
        // 't' around 7% of the time
        val t = assertSlot(slots1, 't', 7)
        // 'h' is 5%
        val h = assertSlot(slots1, 'h', 12)
        assertEquals(5, h - t)
        // 'e' is 10%
        val e = assertSlot(slots1, 'e', 22)
        assertEquals(10, e - h)
        // space is 19%
        val space = assertSlot(slots1, ' ', 41)
        assertEquals(19, space - e)
    }

    @Disabled("only for manual inspection")
    @Test
    fun listSlotsSeconds() {
        val t = obj.seconds['t']!!
        Qlog.info("t", t)
        Qlog.info("t.slots", t.slots)
    }

    @Test
    fun getSlotSeconds() {
        val t = obj.seconds['t']!!
        val h = assertSlot(t.slots, 'h', 35)
        val s = assertSlot(t.slots, 's', 36)
        assertEquals(1, s - h)
        val space = assertSlot(t.slots, ' ', 66)
        assertEquals(30, space - s)
    }

    @Disabled("only for manual inspection")
    @Test
    fun listSlotsThirds() {
        val t = obj.thirds['t']!!
        Qlog.info("t", t)
    }

    fun assertSlot(slots: Map<Char, Double>, ch: Char, expected: Int): Int {
        return getSlot(slots, ch).also { assertEquals(expected, it) }
    }

    @Test
    fun getSlotThirds() {
        val t = obj.thirds['t']!!
        val h = t['h']!!
        val e = assertSlot(h.slots, 'e', 46)
        val a = assertSlot(h.slots, 'a', 59)
        assertEquals(13, a - e)
        val o = assertSlot(h.slots, 'o', 66)
        assertEquals(7, o - a)
    }
}