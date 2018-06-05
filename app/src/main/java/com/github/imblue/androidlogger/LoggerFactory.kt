package com.github.imblue.androidlogger

import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.util.*

private const val LOGGING_TAG = "LoggerFactory"
private const val PROPERTY_FILE_NAME = "android-logger.properties"
private val DEFAULT_LOG_LEVEL = LogLevel.DEBUG

/**
 * Factory-Class for Logger-Implementation.
 *
 * Supports setting the Log-Level.
 *
 * @author Christian Schuhmacher
 */
class LoggerFactory {
    companion object {

        internal var presetLogLevel: LogLevel = DEFAULT_LOG_LEVEL

        /**
         * Instance a new Logger-Implementation with the Class as Tag.
         */
        @JvmStatic()
        fun getLogger(clazz: Class<out Any>): Logger {
            val tag = getTag(clazz)
            return LoggerImpl(tag)
        }

        /**
         * Set the min. Log-Level with the Enum `LogLevel`
         */
        @JvmStatic()
        fun setLogLevel(logLevel: LogLevel) {
            presetLogLevel = logLevel
        }

        /**
         * Set the min. Log-Level via Property-Configuration.
         * Reads the Assets-Directory for a File named `android-logger.properties`.
         * The Android-Context is needed to load the Assets-Directory
         *
         * Property-Key for Log-Level: `log-level`
         *
         * Possible Property-Values:
         * - TRACE
         * - DEBUG
         * - INFO
         * - WARN
         * - ERROR
         * - NONE
         */
        @JvmStatic()
        fun loadLogLevelFromProperties(context: Context) {
            presetLogLevel = getLogLevelFromProperties(context)
        }
    }
}

/**
 * Enum to configure the min. Log-Level.
 */
enum class LogLevel(val level: Int) {
    TRACE(0),
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4),
    NONE(5);
}

private fun getTag(clazz: Class<out Any>): String {
    return clazz.name
}

/**
 * Searches for a Property-File named `android-logger.properties` and returns the Property `log-level`'s value.
 * If no valid File or Property is found, the default-value `com.github.imblue.androidlogger.LoggerFactoryKt.PROPERTY_FILE_NAME`
 * will be used.
 */
private fun getLogLevelFromProperties(context: Context): LogLevel {
    val properties = Properties()

    var inputStream: InputStream? = null
    try {
        inputStream = findPropertyFile(context, "")

        if (inputStream == null) {
            Log.w(LOGGING_TAG, "No Property-File named '$PROPERTY_FILE_NAME' found, using Default-LogLevel '$DEFAULT_LOG_LEVEL'")
            return DEFAULT_LOG_LEVEL
        }

        properties.load(inputStream)
    } catch (e: IOException) {
        Log.e(LOGGING_TAG, "Error opening File '$PROPERTY_FILE_NAME', using Default-LogLevel '$DEFAULT_LOG_LEVEL'", e)
        return DEFAULT_LOG_LEVEL
    } finally {
        inputStream?.close()
    }

    if (properties.containsKey("log-level")) {
        val level = properties["log-level"]
        if (level is String) {
            try {
                return LogLevel.valueOf(level)
            } catch (e: IllegalArgumentException) {
                Log.w(LOGGING_TAG, "No valid LogLevel for '$level' found, using Default-LogLevel '$DEFAULT_LOG_LEVEL'", e)
            }
        }
    }

    return DEFAULT_LOG_LEVEL
}

private fun findPropertyFile(context: Context, path: String): InputStream? {
    val list = context.assets.list(path)
    if (list.contains(PROPERTY_FILE_NAME)) {
        return context.assets.open(if (path.isEmpty()) PROPERTY_FILE_NAME else "$path/$PROPERTY_FILE_NAME")
    }

    for (s in list) {
        val file = findPropertyFile(context, if (path.isEmpty()) s else "$path/$s")
        if (file != null) {
            return file
        }
    }

    return null
}

/**
 * Implementation of com.github.imblue.androidlogger.Logger with Log-Level checks.
 */
private class LoggerImpl(val tag: String) : Logger {

    override fun trace(msg: String) {
        if (isLogEnabled(LogLevel.TRACE)) {
            Log.v(tag, msg)
        }
    }

    override fun trace(msg: String, arg: Any) {
        if (isLogEnabled(LogLevel.TRACE)) {
            Log.v(tag, format(msg, arrayOf(arg)))
        }
    }

    override fun trace(msg: String, vararg args: Any) {
        if (isLogEnabled(LogLevel.TRACE)) {
            if (args.isEmpty()) {
                Log.v(tag, msg)
                return
            }

            val last = args.last()
            when (last) {
                is Throwable -> Log.v(tag, format(msg, args.dropLast(1).toTypedArray()), last)
                else -> Log.v(tag, format(msg, args))
            }
        }
    }

    override fun trace(msg: String, throwable: Throwable) {
        if (isLogEnabled(LogLevel.TRACE)) {
            Log.v(tag, msg, throwable)
        }
    }

    override fun debug(msg: String) {
        if (isLogEnabled(LogLevel.DEBUG)) {
            Log.d(tag, msg)
        }
    }

    override fun debug(msg: String, arg: Any) {
        if (isLogEnabled(LogLevel.DEBUG)) {
            Log.d(tag, format(msg, arrayOf(arg)))
        }
    }

    override fun debug(msg: String, vararg args: Any) {
        if (isLogEnabled(LogLevel.DEBUG)) {
            if (args.isEmpty()) {
                Log.d(tag, msg)
                return
            }

            val last = args.last()
            when (last) {
                is Throwable -> Log.d(tag, format(msg, args.dropLast(1).toTypedArray()), last)
                else -> Log.d(tag, format(msg, args))
            }
        }
    }

    override fun debug(msg: String, throwable: Throwable) {
        if (isLogEnabled(LogLevel.DEBUG)) {
            Log.d(tag, msg, throwable)
        }
    }

    override fun info(msg: String) {
        if (isLogEnabled(LogLevel.INFO)) {
            Log.i(tag, msg)
        }
    }

    override fun info(msg: String, arg: Any) {
        if (isLogEnabled(LogLevel.INFO)) {
            Log.i(tag, format(msg, arrayOf(arg)))
        }
    }

    override fun info(msg: String, vararg args: Any) {
        if (isLogEnabled(LogLevel.INFO)) {
            if (args.isEmpty()) {
                Log.i(tag, msg)
                return
            }

            val last = args.last()
            when (last) {
                is Throwable -> Log.i(tag, format(msg, args.dropLast(1).toTypedArray()), last)
                else -> Log.i(tag, format(msg, args))
            }
        }
    }

    override fun info(msg: String, throwable: Throwable) {
        if (isLogEnabled(LogLevel.INFO)) {
            Log.i(tag, msg, throwable)
        }
    }

    override fun warn(msg: String) {
        if (isLogEnabled(LogLevel.WARN)) {
            Log.w(tag, msg)
        }
    }

    override fun warn(msg: String, arg: Any) {
        if (isLogEnabled(LogLevel.WARN)) {
            Log.w(tag, format(msg, arrayOf(arg)))
        }
    }

    override fun warn(msg: String, vararg args: Any) {
        if (isLogEnabled(LogLevel.WARN)) {
            if (args.isEmpty()) {
                Log.w(tag, msg)
                return
            }

            val last = args.last()
            when (last) {
                is Throwable -> Log.w(tag, format(msg, args.dropLast(1).toTypedArray()), last)
                else -> Log.w(tag, format(msg, args))
            }
        }
    }

    override fun warn(msg: String, throwable: Throwable) {
        if (isLogEnabled(LogLevel.WARN)) {
            Log.w(tag, msg, throwable)
        }
    }

    override fun error(msg: String) {
        if (isLogEnabled(LogLevel.ERROR)) {
            Log.e(tag, msg)
        }
    }

    override fun error(msg: String, arg: Any) {
        if (isLogEnabled(LogLevel.ERROR)) {
            Log.e(tag, format(msg, arrayOf(arg)))
        }
    }

    override fun error(msg: String, vararg args: Any) {
        if (isLogEnabled(LogLevel.ERROR)) {
            if (args.isEmpty()) {
                Log.e(tag, msg)
                return
            }

            val last = args.last()
            when (last) {
                is Throwable -> Log.e(tag, format(msg, args.dropLast(1).toTypedArray()), last)
                else -> Log.e(tag, format(msg, args))
            }
        }
    }

    override fun error(msg: String, throwable: Throwable) {
        if (isLogEnabled(LogLevel.ERROR)) {
            Log.e(tag, msg, throwable)
        }
    }

    private fun isLogEnabled(logLevel: LogLevel): Boolean {
        return logLevel.level >= LoggerFactory.presetLogLevel.level
    }
}