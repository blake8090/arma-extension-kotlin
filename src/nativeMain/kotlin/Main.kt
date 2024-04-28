import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cstr
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.toKString
import platform.posix.strcpy
import kotlin.experimental.ExperimentalNativeApi

@ExperimentalForeignApi
typealias CallbackPointer = CPointer<CFunction<(CValuesRef<ByteVar>, CValuesRef<ByteVar>, CValuesRef<ByteVar>) -> Int>>

@ExperimentalForeignApi
var callbackFunction: CallbackPointer? = null

/**
 * Called only once when Arma 3 loads the extension.
 *
 * @param pointer Pointer to Arma 3's callback function
 */
@ExperimentalForeignApi
@ExperimentalNativeApi
@CName(externName = "RVExtensionRegisterCallback")
@Suppress("UNUSED")
fun rvExtensionRegisterCallback(pointer: CallbackPointer) {
    callbackFunction = pointer
}

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
 * @param outputSize The maximum size of bytes that can be returned (usually 20KB?)
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
 * @param outputSize The maximum size of bytes that can be returned (usually 20KB?)
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
    val result = buildArrayString(argv, argc)
    strcpy(output, "RVExtensionArgs: $result")
    callbackFunction?.invoke("TestExtension".cstr, "fncToExecute_1".cstr, result.cstr)
}

@ExperimentalForeignApi
private fun buildArrayString(array: CPointer<CPointerVar<ByteVar>>, size: Int): String {
    val builder = StringBuilder()

    val strings = mutableListOf<String>()
    for (i in 0..<size) {
        strings.add(array[i]!!.toKString())
    }

    builder.append("[")
    builder.append(strings.joinToString(","))
    builder.append("]")

    return builder.toString()
}
