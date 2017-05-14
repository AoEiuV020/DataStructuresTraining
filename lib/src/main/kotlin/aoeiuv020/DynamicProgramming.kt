package aoeiuv020

import java.util.*

typealias StringBuilder = java.lang.StringBuilder

/**
 * 最长公共子序列，
 * 输出所有子序列，
 * Created by AoEiuV020 on 2017/05/14.
 */
object DynamicProgramming {
    enum class Last {
        XY, X, Y, E
    }
    fun longestCommonSubsequence(x: String, y: String): List<String> = longestCommonSubsequence(x.toCharArray(), y.toCharArray())
    private fun longestCommonSubsequence(x: CharArray, y: CharArray): List<String> {
        val c = Array(x.size, {
            Array(y.size, {
                Pair(0, Last.XY)
            })
        })
        var i = x.size - 1
        var j = y.size - 1
        _lcs(c, x, y, i, j)
        return collect(c, x, y, i, j).map(StringBuilder::toString).distinct().sorted()
    }
    private fun collect(c: Array<Array<Pair<Int, Last>>>, x: CharArray, y: CharArray, i: Int, j: Int): MutableList<StringBuilder> = if (i < 0 || j < 0) mutableListOf<StringBuilder>(StringBuilder())
    else when (c[i][j].second) {
        Last.X -> collect(c, x, y, i - 1, j)
        Last.Y -> collect(c, x, y, i, j - 1)
        Last.E -> (collect(c, x, y, i - 1, j) + collect(c, x, y, i, j - 1)).toMutableList()
        Last.XY -> {
            collect(c, x, y, i - 1, j - 1).map {
                it.append(x[i])
            }.toMutableList()
        }
    }
    private fun _lcs(c: Array<Array<Pair<Int, Last>>>, x: CharArray, y: CharArray, i: Int, j: Int): Int = when {
        i == -1 || j == -1 -> 0
        x[i] == y[j] -> {
            val xy = _lcs(c, x, y, i - 1, j - 1) + 1
            c[i][j] = Pair(xy, Last.XY)
            c[i][j].first
        }
        else -> {
            val x1 = _lcs(c, x, y, i - 1, j)
            val y1 = _lcs(c, x, y, i, j - 1)
            c[i][j] = if (x1 > y1) {
                Pair(x1, Last.X)
            } else if (x1 < y1) {
                Pair(y1, Last.Y)
            } else {
                Pair(x1, Last.E)
            }
            c[i][j].first
        }
    }
}

