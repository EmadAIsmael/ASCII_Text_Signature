package signature

fun main() {
    val top = '_'
    val side = '|'
    val bottom = '¯'
    val first = "Hyper"
    val last = "Skill"
    val len = first.length + last.length + 3
    print(" ")
    repeat(len) {
        print(top)
    }
    println()
    println("$side $first $last $side")
    print(" ")
    repeat(len) {
        print(bottom)
    }
}
