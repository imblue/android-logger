# Android Logger

Simple Logger for Android in Kotlin with customizable Log-Level

## Getting started

### Configuration

The Log-Level is customizable by either setting the Enum
 ```
 com.github.imblue.androidlogger.LogLevel
 ```
 in
  ```
  com.github.imblue.androidlogger.LoggerFactory.setLogLevel
  ```
   or creating an `android-logger.properties`-File inside the assets-Directory and calling
  ```
  com.github.imblue.androidlogger.LoggerFactory.loadLogLevelFromProperties
  ```
   with an Android-Context.

   Property-Key: `log-level`

   Possible Log-Levels set via Enum or Property:
   ```
   TRACE
   DEBUG
   INFO
   WARN
   ERROR
   NONE
   ```

### Compiling

Build the Project using Gradle-Wrapper

```
./gradlew build
```