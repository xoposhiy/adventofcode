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
class CodeAdventDay2Test {

    val puzzle = "5\t1\t9\t5\n" +
        "7\t5\t3\n" +
        "2\t4\t6\t8"


    val puzzle2 = "86\t440\t233\t83\t393\t420\t228\t491\t159\t13\t110\t135\t97\t238\t92\t396\n" +
            "3646\t3952\t3430\t145\t1574\t2722\t3565\t125\t3303\t843\t152\t1095\t3805\t134\t3873\t3024\n" +
            "2150\t257\t237\t2155\t1115\t150\t502\t255\t1531\t894\t2309\t1982\t2418\t206\t307\t2370\n" +
            "1224\t343\t1039\t126\t1221\t937\t136\t1185\t1194\t1312\t1217\t929\t124\t1394\t1337\t168\n" +
            "1695\t2288\t224\t2667\t2483\t3528\t809\t263\t2364\t514\t3457\t3180\t2916\t239\t212\t3017\n" +
            "827\t3521\t127\t92\t2328\t3315\t1179\t3240\t695\t3144\t3139\t533\t132\t82\t108\t854\n" +
            "1522\t2136\t1252\t1049\t207\t2821\t2484\t413\t2166\t1779\t162\t2154\t158\t2811\t164\t2632\n" +
            "95\t579\t1586\t1700\t79\t1745\t1105\t89\t1896\t798\t1511\t1308\t1674\t701\t60\t2066\n" +
            "1210\t325\t98\t56\t1486\t1668\t64\t1601\t1934\t1384\t69\t1725\t992\t619\t84\t167\n" +
            "4620\t2358\t2195\t4312\t168\t1606\t4050\t102\t2502\t138\t135\t4175\t1477\t2277\t2226\t1286\n" +
            "5912\t6261\t3393\t431\t6285\t3636\t4836\t180\t6158\t6270\t209\t3662\t5545\t204\t6131\t230\n" +
            "170\t2056\t2123\t2220\t2275\t139\t461\t810\t1429\t124\t1470\t2085\t141\t1533\t1831\t518\n" +
            "193\t281\t2976\t3009\t626\t152\t1750\t1185\t3332\t715\t1861\t186\t1768\t3396\t201\t3225\n" +
            "492\t1179\t154\t1497\t819\t2809\t2200\t2324\t157\t2688\t1518\t168\t2767\t2369\t2583\t173\n" +
            "286\t2076\t243\t939\t399\t451\t231\t2187\t2295\t453\t1206\t2468\t2183\t230\t714\t681\n" +
            "3111\t2857\t2312\t3230\t149\t3082\t408\t1148\t2428\t134\t147\t620\t128\t157\t492\t2879"

    val  puzzle3 ="5\t9\t2\t8\n" +
        "9\t4\t7\t3\n" +
        "3\t8\t6\t5"

    val puzzle4 = "86\t440\t233\t83\t393\t420\t228\t491\t159\t13\t110\t135\t97\t238\t92\t396\n" +
            "3646\t3952\t3430\t145\t1574\t2722\t3565\t125\t3303\t843\t152\t1095\t3805\t134\t3873\t3024\n" +
            "2150\t257\t237\t2155\t1115\t150\t502\t255\t1531\t894\t2309\t1982\t2418\t206\t307\t2370\n" +
            "1224\t343\t1039\t126\t1221\t937\t136\t1185\t1194\t1312\t1217\t929\t124\t1394\t1337\t168\n" +
            "1695\t2288\t224\t2667\t2483\t3528\t809\t263\t2364\t514\t3457\t3180\t2916\t239\t212\t3017\n" +
            "827\t3521\t127\t92\t2328\t3315\t1179\t3240\t695\t3144\t3139\t533\t132\t82\t108\t854\n" +
            "1522\t2136\t1252\t1049\t207\t2821\t2484\t413\t2166\t1779\t162\t2154\t158\t2811\t164\t2632\n" +
            "95\t579\t1586\t1700\t79\t1745\t1105\t89\t1896\t798\t1511\t1308\t1674\t701\t60\t2066\n" +
            "1210\t325\t98\t56\t1486\t1668\t64\t1601\t1934\t1384\t69\t1725\t992\t619\t84\t167\n" +
            "4620\t2358\t2195\t4312\t168\t1606\t4050\t102\t2502\t138\t135\t4175\t1477\t2277\t2226\t1286\n" +
            "5912\t6261\t3393\t431\t6285\t3636\t4836\t180\t6158\t6270\t209\t3662\t5545\t204\t6131\t230\n" +
            "170\t2056\t2123\t2220\t2275\t139\t461\t810\t1429\t124\t1470\t2085\t141\t1533\t1831\t518\n" +
            "193\t281\t2976\t3009\t626\t152\t1750\t1185\t3332\t715\t1861\t186\t1768\t3396\t201\t3225\n" +
            "492\t1179\t154\t1497\t819\t2809\t2200\t2324\t157\t2688\t1518\t168\t2767\t2369\t2583\t173\n" +
            "286\t2076\t243\t939\t399\t451\t231\t2187\t2295\t453\t1206\t2468\t2183\t230\t714\t681\n" +
            "3111\t2857\t2312\t3230\t149\t3082\t408\t1148\t2428\t134\t147\t620\t128\t157\t492\t2879"

    @Test
    fun ttt() {
//        Day2.findDivision(Observable.just(5,9,2,8))
        Day2.processMatrix2(puzzle3)
        assertEquals(9, Day2.processMatrix2(puzzle3))
        assertEquals(294, Day2.processMatrix2(puzzle4))
    }
}



class Day2() {
    companion object {

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