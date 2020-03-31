import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    // write your code here
    val input = Array<Char>(4) { scanner.next().first() }
    val output = Array(4) { "" }
    for (i in input.indices)
        output[i] = input[i].isDigit().toString()
    println(output.joinToString("\\"))
}
