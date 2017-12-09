package ru

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toSingle
import org.testng.annotations.Test
import kotlin.test.assertEquals

/**
 * Created by skorokhodov on 01.12.2017.
 */
class CodeAdventDay3Test {


    @Test
    fun ttt() {
        Day3.generateSpiral(312051)
    }

    @Test
    fun tttt() {
        Day3.generateSumSpiral(312051)
    }
}



class Day3() {
    companion object {

        fun generateSumSpiral(maxItem: Int) {

            val items = mutableListOf(Pair(1, Pair(0,0)))
            var i = 1
            while( items.last().first < maxItem) {
                right(i, items)
                up(i, items)
                i++
                left(i, items)
                down(i, items)
                i++
            }
        }


        private fun right(i: Int, items: MutableList<Pair<Int, Pair<Int, Int>>>) {
            xz(i, items, {oldCoord: Pair<Int,Int> -> Pair(oldCoord.first + 1, oldCoord.second)} )
        }
        private fun left(i: Int, items: MutableList<Pair<Int, Pair<Int, Int>>>) {
            xz(i, items, {oldCoord: Pair<Int,Int> -> Pair(oldCoord.first - 1, oldCoord.second)} )
        }
        private fun up(i: Int, items: MutableList<Pair<Int, Pair<Int, Int>>>) {
            xz(i, items, {oldCoord: Pair<Int,Int> -> Pair(oldCoord.first, oldCoord.second + 1)} )
        }
        private fun down(i: Int, items: MutableList<Pair<Int, Pair<Int, Int>>>) {
            xz(i, items, {oldCoord: Pair<Int,Int> -> Pair(oldCoord.first, oldCoord.second - 1)} )
        }

        private fun xz(i: Int, items: MutableList<Pair<Int, Pair<Int, Int>>>, newCoordAction : (Pair<Int, Int>) -> Pair<Int,Int> ) {
            for(i in 1.. i) {
                val lastItem = items.last()
                val newCoord =  newCoordAction(lastItem.second)
                val newValue = generateNewValue(items, newCoord)
                println("new item: $newValue, $newCoord")
                items.add( Pair(newValue, newCoord))
            }
        }

        private fun generateNewValue(items: MutableList<Pair<Int, Pair<Int, Int>>>, newCoord: Pair<Int, Int>): Int {
            return items.filter { it.second.first >= newCoord.first - 1  }
                    .filter { it.second.first <= newCoord.first + 1  }
                    .filter { it.second.second >= newCoord.second - 1  }
                    .filter { it.second.second <= newCoord.second + 1  }
                    .map { it.first }.sum()
        }


        fun generateSpiral(maxItem: Int) {
            var level = 1

            var item = 1
            println("$item, 0")
            item++

            while (item < maxItem)
            {
                println("level $level")
                item = generateLevel(level, item)
                level++
            }
        }

        fun generateLevel(level: Int, ii: Int): Int {
            var item = ii
            for(i in 1..4) {
                for (steps in (level * 2 -1) downTo  level) {
                    println("$item, $steps")
                    item++
                }
                for (steps in (level+1) .. (level *2) ) {
                    println("$item, $steps")
                    item++
                }
            }
            return item
        }

        fun processMatrix(puzzle: String): Int {
            val xz = Observable.fromIterable(puzzle.split("\n"))
                    .doOnNext {  println("row: $it") }
                    .map { str -> Observable.fromIterable(str.split("\t").map { it.toInt() }) }
                    .map { obs -> sumMinMaxElements(obs) }
                    .doOnNext { println(it.blockingGet()) }
                    .reduce( 0, { acc, item ->  acc + item.blockingGet() } )

            return xz.blockingGet()
        }

        fun processMatrix2(puzzle: String): Int {
            val xz = Observable.fromIterable(puzzle.split("\n"))
                    .doOnNext {  println("row: $it") }
                    .map { str -> Observable.fromIterable(str.split("\t").map { it.toInt() }) }
                    .map { obs -> findDivision(obs) }
                    .doOnNext { println(it.blockingGet()) }
                    .reduce( 0, { acc, item ->  acc + item.blockingGet() } )

            return xz.blockingGet()
        }

        fun findDivision(items: Observable<Int>): Single<Int> {

            var xz: MutableList<Observable<Pair<Int, Int>>> = mutableListOf()
            for(offset in 1..items.count().blockingGet()-1) {
               xz.add(items.zipWith(items.skip(offset), BiFunction<Int, Int, Pair<Int,Int>> { a, b -> Pair(a, b)} ))
            }

            val xx = Observable.merge(xz)
                .flatMap { it -> Observable.just( it, Pair(it.second, it.first)) }
                    .filter { it -> it.first % it.second == 0  }
                    .map { it.first/it.second }
                    .map { it.toInt() }
                    .doOnNext { println(it) }
                    .blockingFirst()
            return Single.just(xx)
        }

        fun sumElements(items: Observable<Int>): Single<Int> {
            return items
                    .reduce{ acc, item -> acc + item}
                    .toSingle()
        }

        fun sumMinMaxElements(items: Observable<Int>): Single<Int> {
            val maxElem = items.reduce( Int.MIN_VALUE, {acc, item -> if (item > acc) item else acc })
            val minElem = items.reduce( Int.MAX_VALUE, {acc, item -> if(item < acc) item else acc } )

            return maxElem.zipWith(minElem, BiFunction<Int, Int, Int> { a, b -> a-b } )

//            return Single.just(maxElem.blockingGet()-minElem.blockingGet())
        }


        fun secondPuzzle(puzzle: String): Int {

            var s = 0
            puzzle.split("\n").forEach {
                str ->
                val list = str.split("\t").map { it.toInt() }
                var min: Int = list.first()
                var max: Int = list.first()
                list.forEach {
                    println(it)
                    if (it > max) max = it
                    if (it < min) min = it
                }
                s +=  max - min
                println("$max : $min")
                println("next line")
            }
            return s
        }

        fun secondPuzzle2(puzzle: String): Int {

            var s = 0
            puzzle.split("\n").forEach {
                str ->
                val list = str.split("\t").map { it.toInt() }
                var min: Int = list.first()
                var max: Int = list.first()
                list.forEach {
                    println(it)
                    if (it > max) max = it
                    if (it < min) min = it
                }
                s +=  max - min
                println("$max : $min")
                println("next line")
            }
            return s
        }

    }
}