package com.github.imblue.androidlogger

private const val argsIdentifier = "{}"

/**
 * Internal formatter for adding parameters to the original message.
 *
 * @author Christian Schuhmacher
 */
internal fun format(message: String?, args: Array<out Any>): String {
    if (message == null) {
        return ""
    }

    if (!message.contains(argsIdentifier)) {
        return message
    }

    val builder = StringBuilder()
    var lastArgIndex = 0
    var first = true

    for (arg in args) {
        val sinceLastRun = message.substring(lastArgIndex + (if (first) 0 else argsIdentifier.length))
        lastArgIndex = sinceLastRun.indexOf(argsIdentifier)

        if (lastArgIndex == -1) {
            break
        }

        builder.append(sinceLastRun.substring(0, lastArgIndex))
        builder.append(arg.toString())
        first = false
    }

    val lastSubstringIndex = message.lastIndexOf(argsIdentifier) + 2
    builder.append(message.substring(lastSubstringIndex))

    return builder.toString()
}