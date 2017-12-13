import org.testng.annotations.Test
import kotlin.test.assertEquals


fun solve(size: Int, lengths: List<Int>, rounds: Int = 1): Pair<String, Int> {

    var items = (0..size-1).toList()
    var pos = 0
    var skip = 0
    (1..rounds).forEach {
        lengths.forEach { length ->
            items = reverseSublist(items, pos, length)
            pos = (pos + length + skip) % items.size
            skip += 1
        }
    }
    return Pair(denseHash(items), items[0] * items[1])
 }

fun denseHash(sparseHash: List<Int>) =
    (0..15).toList()
            .map {  sparseHash.subList(it * 16, it * 16 + 16).reduce { acc, v -> acc.xor(v) } }
            .map { java.lang.Integer.toHexString(it).padStart(2,'0') }
            .joinToString("")


fun reverseSublist(l: List<Int>, startPos: Int, length: Int): List<Int> {
    if(length > l.size)
        throw Exception("wrong length $length")

    val reversed = l.drop(startPos).plus(l).take(length).asReversed()
    val res = l.toMutableList()
    for(i in 0..reversed.size-1) {
        res[(startPos + i )% l.size] = reversed[i]
    }
    return res.toList()
}


fun createASCII(str: String) =
    str.asIterable().map { it.toInt() } + listOf(17, 31, 73, 47, 23)


class Day10Test {

    val puzzle = "18,1,0,161,255,137,254,252,14,95,165,33,181,168,2,188"
    @Test
    fun testFirstRound() {
        val data = puzzle.split(",").map(String::trim).map { it.toInt() }
        val res = solve(256, data)
        println(res.second)
    }

    @Test
    fun testAscii() {
        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", solve(256, createASCII("1,2,3"), 64).first)
        assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", solve(256, createASCII("1,2,4"), 64).first)
        assertEquals("23234babdc6afa036749cfa9b597de1b", solve(256, createASCII(puzzle), 64).first)
    }

    @Test
    fun testReverseList() {
        assertEquals(listOf(4,3,2,1,5), reverseSublist(listOf(1,2,3,4,5),0,4))
        assertEquals(listOf(3,2,1,4,5), reverseSublist(listOf(1,2,3,4,5),0,3))
        assertEquals(listOf(1,2,3,4,5), reverseSublist(listOf(1,2,3,4,5),0,1))

        assertEquals(listOf(5,4,3,2,1), reverseSublist(listOf(1,2,3,4,5),3,4))
        assertEquals(listOf(1,5,3,4,2), reverseSublist(listOf(1,2,3,4,5),4,3))
        assertEquals(listOf(3,2,1,5,4), reverseSublist(listOf(1,2,3,4,5),2,4))
    }
}