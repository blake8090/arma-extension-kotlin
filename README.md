## About

An example extension for Arma 3 written using Kotlin Native.

This unlocks the possibility to write Arma 3 scripts almost entirely in Kotlin instead of C, C++ or C#.

## Getting Started

### Building

1. Clone the repo
2. Run the `linkNative` Gradle task
3. Navigate to one of the output directories:
   ```
   build/bin/native/debugShared
   build/bin/native/releaseShared
   ```
4. Copy `TestExtension_x64.dll` to your root Arma 3 folder

### Example SQF

An example SQF script utilizing the extension can be found in `mission/init.sqf`.
