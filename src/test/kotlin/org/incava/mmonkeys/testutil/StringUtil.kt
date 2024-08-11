package org.incava.mmonkeys.testutil

object StringUtil {
    fun succ(str: String): String {
        return succ(str, 1)
    }

    fun succ(str: String, offset: Int): String {
        val chars = str.toByteArray().reversed().toMutableList()
        chars[0] = (chars[0] + offset).toByte()
        (0..chars.lastIndex).forEach { index ->
            val diff = chars[index] - 'z'.toByte()
            if (diff > 0) {
                var newChar = 'a' - 1 + diff
                chars[index] = newChar.toByte()
                if (index == chars.lastIndex) {
                    chars.add('a'.toByte())
                } else {
                    chars[index + 1]++
                }
            }
        }
        return String(chars.reversed().toByteArray())
    }

    fun camelCaseToWords(string: String): String {
        val regex = Regex("([a-z])([A-Z])")
        return string
            .replace("Monkey", "")
            .replace(regex) {
                (it.groups[1]?.value ?: "") + " " + (it.groups[2]?.value ?: "")
            }
            .toLowerCase()
    }
}