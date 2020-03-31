package signature

import java.util.Scanner
import kotlin.math.abs
import kotlin.math.max

enum class Character(val character: Char, val scanLines: Array<String>) {
    A('A', arrayOf("____", "|__|", "|  |")),
    B('B', arrayOf("___ ", "|__]", "|__]")),
    C('C', arrayOf("____", "|   ", "|___")),
    D('D', arrayOf("___ ", "|  \\", "|__/")),
    E('E', arrayOf("____", "|___", "|___")),
    F('F', arrayOf("____", "|___", "|   ")),
    G('G', arrayOf("____", "| __", "|__]")),
    H('H', arrayOf("_  _", "|__|", "|  |")),
    I('I', arrayOf("_", "|", "|")),
    J('J', arrayOf(" _", " |", "_|")),
    K('K', arrayOf("_  _", "|_/ ", "| \\_")),
    L('L', arrayOf("_   ", "|   ", "|___")),
    M('M', arrayOf("_  _", "|\\/|", "|  |")),
    N('N', arrayOf("_  _", "|\\ |", "| \\|")),
    O('O', arrayOf("____", "|  |", "|__|")),
    P('P', arrayOf("___ ", "|__]", "|   ")),
    Q('Q', arrayOf("____", "|  |", "|_\\|")),
    R('R', arrayOf("____", "|__/", "|  \\")),
    S('S', arrayOf("____", "[__ ", "___]")),
    T('T', arrayOf("___", " | ", " | ")),
    U('U', arrayOf("_  _", "|  |", "|__|")),
    V('V', arrayOf("_  _", "|  |", " \\/ ")),
    W('W', arrayOf("_ _ _", "| | |", "|_|_|")),
    X('X', arrayOf("_  _", " \\/ ", "_/\\_")),
    Y('Y', arrayOf("_   _", " \\_/ ", "  |  ")),
    Z('Z', arrayOf("___ ", "  / ", " /__"));

    companion object {

        fun charWidth(c: Char): Int {
            return when (c) {
                'I' -> 1
                'J' -> 2
                'T' -> 3
                'W' -> 5
                'Y' -> 5
                else -> 4
            }
        }

    }
}

fun printScanLine(c: Char, i: Int) {
    if (c == ' ') {
        // four character for space + 1 more for right seperators.
        print(" ".repeat(5))
        return
    }
    for (enum in Character.values())
        if (c == enum.character) {
            print("${enum.scanLines[i]} ")
            break
        }
}

fun printName(name: String, prefix: String, suffix: String) {
    for (i in 0 until 3) {
        print("$prefix  ")
        for (c in name) {
            printScanLine(c, i)
        }
        print(" $suffix")
        println()
    }
}

fun scanLineCharLen(name: String): Int {
    var sum = 0
    for (c in name)
        sum += if (c == ' ')
            4
        else
            Character.charWidth(c)
    return sum
}

fun nameTag(name: String, status: String) {
    val top = "*"
    val bottom = "*"
    val space = " "

    // star + 2 spaces +
    // width(name scan lines) characters +
    // len(name) spaces +
    // 2 spaces + star
    val spaces = name.length - 1                 // one space after each character
    // star + 2 spaces prefix,
    // 1 space + star suffix
    // scanLine contains a trailing space
    val scanLineLen = 3 + scanLineCharLen(name) + spaces + 3

    // prefix: star + 2 spaces = 3
    // suffix: two spaces + star = 3
    val statusLineLen = 3 + status.length + 3

    val len: Int
    val scanLinePrefix: String
    val scanLineSuffix: String
    val statusLinePrefix: String
    val statusLineSuffix: String

    val lenDiff = abs(statusLineLen - scanLineLen)
    val prefixHalfLen = lenDiff / 2
    val suffixHalfLen = prefixHalfLen + if (lenDiff % 2 == 0) 0 else +1

    if (scanLineLen > statusLineLen) {
        len = scanLineLen
        scanLinePrefix = "*"
        scanLineSuffix = "*"

        statusLinePrefix = "*  " + space.repeat(max(0, prefixHalfLen))
        statusLineSuffix = space.repeat(max(0, suffixHalfLen)) + "  *"
    } else {
        len = statusLineLen
        scanLinePrefix = "*" + space.repeat(max(0, prefixHalfLen))
        scanLineSuffix = space.repeat(max(0, suffixHalfLen)) + "*"

        statusLinePrefix = "*  "
        statusLineSuffix = "  *"
    }

    val statusLine = "$statusLinePrefix$status$statusLineSuffix"

    println(top.repeat(len))

    printName(name, scanLinePrefix, scanLineSuffix)

    println(statusLine)

    println(bottom.repeat(len))
}

fun main() {

    val scanner = Scanner(System.`in`)

    val name = scanner.nextLine()
    val status = scanner.nextLine()

    nameTag(name.toUpperCase(), status)
}
