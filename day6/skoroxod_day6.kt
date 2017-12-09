package ru.kontur

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Created by skorokhodov on 06.12.2017.
 */
class Day6Test {

    @Test
    fun testSingleReallocation() {
        assertEquals("2,4,1,2", Day6.singleReallocation("0,2,7,0"))
        assertEquals("3,1,2,3", Day6.singleReallocation("2,4,1,2"))
        assertEquals("0,2,3,4", Day6.singleReallocation("3,1,2,3"))
        assertEquals("1,3,4,1", Day6.singleReallocation("0,2,3,4"))
        assertEquals("2,4,1,2", Day6.singleReallocation("1,3,4,1"))
        assertEquals(                "6,2,11,1,2,8,13,0,4,13,9,11,8,13,1,7",
                Day6.singleReallocation("5,1,10,0,1,7,13,14,3,12,8,10,7,12,0,6"))
    }

    @Test
    fun testFull() {
        assertEquals(Pair(5,4), Day6.fullAction("0\t2\t7\t0"))
        assertEquals(Pair(5042,1086), Day6.fullAction("5\t1\t10\t0\t1\t7\t13\t14\t3\t12\t8\t10\t7\t12\t0\t6"))
    }

}

class Day6 {
    companion object {

        fun fullAction(items: String): Pair<Int,Int> {
            val reallocations = mutableListOf(items.replace("\t",","))
            var counter = 0
            do {
                reallocations.add(singleReallocation(reallocations.last()))
                counter++
            } while (reallocations.size == reallocations.distinct().size)

            val ind = reallocations.indexOfFirst { it == reallocations.last() }
            val loopSize = reallocations.size - ind - 1
            return Pair(counter, loopSize)
        }

        fun singleReallocation(items: String): String {
            val result = items.split(",").map { it.toInt() }.toIntArray()
            var maxValue = result.max()
            var index = result.indexOfFirst { it == result.max() }
            result[index] = 0
            while (maxValue!! > 0) {
                index = (index+1) % result.size
                result[index]++
                maxValue--
            }
            return result.joinToString(",")
        }
    }
}