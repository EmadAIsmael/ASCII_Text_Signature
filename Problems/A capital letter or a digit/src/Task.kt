import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    // write your code here
    val ch = scanner.next().first()

    println(ch in '1'..'9' || ch.isUpperCase())
}
