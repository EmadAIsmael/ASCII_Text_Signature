package signature

import java.io.File
import java.util.Scanner
import kotlin.math.abs
import kotlin.math.max


class Character(val charWidth: Int, val repr: Array<String>)


open class Font(private val fontFile: String) {

    private val font = mutableMapOf<Char, Character>()
    private var charSize = 0
    private var fontSize: Int = 0

    fun read() {
        val scanner = Scanner(File(fontFile))

        this.charSize = scanner.nextInt()        // char height/num lines
        this.fontSize = scanner.nextInt()        // num chars per font

        for (i in 0 until fontSize) {

            val c = scanner.next().first()      // char symbol
            val charWidth = scanner.nextInt()   // char width

            scanner.nextLine()                  // dummy read to skip current line
            val repr = Array(charSize) { "" }
            for (j in 0 until charSize)
                repr[j] = scanner.nextLine()

            val char = Character(charWidth, repr)
            font[c] = char
        }
        // Add space character
        val spaceRepr = Array(charSize) { " ".repeat(font['a']?.charWidth ?: 0) }

        font[' '] = Character(
            font['a']?.charWidth ?: 0,
            spaceRepr
        )
    }

    private fun printScanLine(i: Int, c: Char) = print("${font[c]?.repr?.get(i)}")

    fun printText(text: String, prefix: String, suffix: String) {
        for (i in 0 until charSize) {
            print(prefix)
            for (c in text)
                printScanLine(i, c)

            print(suffix)
            println()
        }
    }

    fun scanLineCharLen(text: String): Int {
        var sum = 0
        for (c in text)
            sum += font[c]?.charWidth ?: 0
        return sum
    }

    /**
     * @param lineLen: length of printing line
     * @param strLen: length of string to center within 'lineLen'
     */
    fun center(lineLen: Int, strLen: Int): MutableMap<String, Int> {
        val diff = abs(lineLen - strLen)
        val prefix = diff / 2
        val suffix = prefix + if (diff % 2 == 0) 0 else 1

        return mutableMapOf("prefix" to prefix, "suffix" to suffix)
    }
}

fun nameTag(name: String, status: String) {

    val mediumFontFile =
        "/home/emad/Documents/Development/LEARN/LearnKotlin/hyperskill/kotlin/ASCII Text Signature/ASCII Text Signature/task/src/signature/medium.txt"
    val romanFontFile =
        "/home/emad/Documents/Development/LEARN/LearnKotlin/hyperskill/kotlin/ASCII Text Signature/ASCII Text Signature/task/src/signature/roman.txt"
    val medium = Font(mediumFontFile)
    val roman = Font(romanFontFile)

    medium.read()
    roman.read()

    val top = "8"
    val bottom = "8"
    val space = " "

    val nameLineLen = roman.scanLineCharLen(name)
    val statusLineLen = medium.scanLineCharLen(status)

    val outputLineLen =
        if (nameLineLen > statusLineLen)
            nameLineLen
        else statusLineLen

    val nameLinePrefix: String
    val nameLineSuffix: String
    val statusLinePrefix: String
    val statusLineSuffix: String

    val namePaddingLen = roman.center(outputLineLen, nameLineLen)
    val statusPaddingLen = medium.center(outputLineLen, statusLineLen)

    nameLinePrefix = "88  " + space.repeat(max(0, namePaddingLen["prefix"]!!))
    nameLineSuffix = space.repeat(max(0, namePaddingLen["suffix"]!!)) + "  88"

    statusLinePrefix = "88  " + space.repeat(max(0, statusPaddingLen["prefix"]!!))
    statusLineSuffix = space.repeat(max(0, statusPaddingLen["suffix"]!!))+ "  88"

    // two '8' chars for border + two spaces
    // 2 spaces + two '8' chars
    // total extra chars = 8
    val extraBorder = 8
    println(top.repeat(outputLineLen + extraBorder))
    roman.printText(name, nameLinePrefix, nameLineSuffix)
    medium.printText(status, statusLinePrefix, statusLineSuffix)
    println(bottom.repeat(outputLineLen + extraBorder))
}

fun main() {

    val scanner = Scanner(System.`in`)

    print("Enter name and surname: ")
    val name = scanner.nextLine()

    print("Enter person's status: ")
    val status = scanner.nextLine()

    nameTag(name, status)
}
