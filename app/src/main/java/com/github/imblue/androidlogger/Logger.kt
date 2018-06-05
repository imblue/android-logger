package com.github.imblue.androidlogger

/**
 * Interface with basic Logging-Functions.
 *
 * Instance Logger-Implementation via com.github.imblue.androidlogger.LoggerFactory.getLogger
 *
 * The Log-Level is customizable with:
 * - com.github.imblue.androidlogger.LoggerFactory.setLogLevel
 * - com.github.imblue.androidlogger.LoggerFactory.loadLogLevelFromProperties
 *
 * Sample Logger-Creation: `Logger logger = LoggerFactory.getLogger(MyClass.class);`
 *
 * Sample-Logs:
 * - logger.debug("Starting Authentication-Process")
 * - logger.info("Invalid Login-Form, Username '{}' contains invalid symbols", username)
 * - logger.error("Server-Error authenticating User '{}', http-code: {}", username, httpCode, exception)
 *
 * @author Christian Schuhmacher
 */
interface Logger {

    fun trace(msg: String)
    fun trace(msg: String, arg: Any)
    fun trace(msg: String, vararg args: Any)
    fun trace(msg: String, throwable: Throwable)

    fun debug(msg: String)
    fun debug(msg: String, arg: Any)
    fun debug(msg: String, vararg args: Any)
    fun debug(msg: String, throwable: Throwable)

    fun info(msg: String)
    fun info(msg: String, arg: Any)
    fun info(msg: String, vararg args: Any)
    fun info(msg: String, throwable: Throwable)

    fun warn(msg: String)
    fun warn(msg: String, arg: Any)
    fun warn(msg: String, vararg args: Any)
    fun warn(msg: String, throwable: Throwable)

    fun error(msg: String)
    fun error(msg: String, arg: Any)
    fun error(msg: String, vararg args: Any)
    fun error(msg: String, throwable: Throwable)
}
