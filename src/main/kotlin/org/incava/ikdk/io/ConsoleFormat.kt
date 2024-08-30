package org.incava.ikdk.io

object ConsoleFormat {
    fun format(msg: String, obj: Any?) = String.format("%-24s: %s", msg, obj)

    fun format(msg: String, value: Long) = String.format("%-24s: %,d", msg, value)

    fun format(msg: String, value: Int) : String {
        return if (value >= 10_000) {
            format(msg, value.toLong())
        } else {
            String.format("%-24s: %d", msg, value)
        }
    }

    fun format(frame: StackTraceElement?, msg: String): String {
        val className = frame?.className?.split(".")?.last() ?: "<?>"
        val methodName = frame?.methodName ?: "<?>"
        val lineNumber = frame?.lineNumber ?: -1
        return String.format("%-25.25s . %-15.15s # %4d | %s", className, methodName, lineNumber, msg)
    }
}