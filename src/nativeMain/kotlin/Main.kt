import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.toKString
import platform.posix.strcpy
import kotlin.experimental.ExperimentalNativeApi

/**
 * Called only once when Arma 3 loads the extension.
 *
 * @param output The output buffer to be written to the RPT logs
 * @param outputSize The maximum size of bytes that can be returned (always 32 for this particular function!)
 */
@ExperimentalForeignApi
@ExperimentalNativeApi
@CName(externName = "RVExtensionVersion")
@Suppress("UNUSED", "UNUSED_PARAMETER")
fun rvExtensionVersion(output: CPointer<ByteVar>, outputSize: Int) {
    strcpy(output, "TestExtension v1.0")
}

/**
 * The entry point for the default `callExtension` command.
 *
 * @param output The output buffer to be returned from `callExtension`
 * @param outputSize The maximum size of bytes that can be returned (usually 20MB?)
 * @param function The function identifier passed in `callExtension`
 */
@ExperimentalForeignApi
@ExperimentalNativeApi
@CName(externName = "RVExtension")
@Suppress("UNUSED", "UNUSED_PARAMETER")
fun rvExtension(output: CPointer<ByteVar>, outputSize: Int, function: CPointer<ByteVar>) {
    strcpy(output, "RVExtension: " + function.toKString())
}

/**
 * The entry point for the `callExtensionArgs` command.
 *
 * @param output The output buffer to be returned from `callExtension`
 * @param outputSize The maximum size of bytes that can be returned (usually 20MB?)
 * @param function The function identifier passed in `callExtension`
 * @param argv The args passed to `callExtension` as a string array
 * @param argc Number of elements in `argv`
 */
@ExperimentalForeignApi
@ExperimentalNativeApi
@CName(externName = "RVExtensionArgs")
@Suppress("UNUSED", "UNUSED_PARAMETER")
fun rvExtensionArgs(
    output: CPointer<ByteVar>,
    outputSize: Int,
    function: CPointer<ByteVar>,
    argv: CPointer<CPointerVar<ByteVar>>,
    argc: Int
) {
    val args = mutableListOf<String>()
    val size = argc - 1
    for (i in 0..size) {
        args.add(argv[i]!!.toKString())
    }

    val result = args.joinToString(",")
    strcpy(output, "RVExtensionArgs: $result")
}
