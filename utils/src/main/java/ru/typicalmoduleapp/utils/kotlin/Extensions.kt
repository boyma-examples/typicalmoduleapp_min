package ru.typicalmoduleapp.utils.kotlin

// https://stackoverflow.com/a/44383076
/**
 * Make sealed when-STATEMENT as safe as sealed when-EXPRESSION
 * when (value) {
 *      is SealedClass.First -> doSomething()
 *      is SealedClass.Second -> doSomethingElse()
 * }.forceExhaustive
 */
@Suppress("unused") // suppress "Receiver parameter is never used" warning
inline val Unit?.forceExhaustive
    get() = Unit

// https://stackoverflow.com/a/47379745
@Suppress("unused") // suppress "Receiver parameter is never used" warning
inline val Any?.toUnit
    get() = Unit

// same as above. just more convenient name
@Suppress("unused") // suppress "Receiver parameter is never used" warning
inline val Any?.ignoreValue
    get() = Unit
