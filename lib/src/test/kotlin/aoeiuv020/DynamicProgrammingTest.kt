package aoeiuv020

import org.junit.Test
import kotlin.test.*

/**
 * Created by AoEiuV020 on 2017/05/14.
 */
class DynamicProgrammingTest {
    @Test
    fun lcs() {
        assertEquals(listOf("B"), DynamicProgramming.longestCommonSubsequence("AB", "BD"))
        assertEquals(listOf("BCAB", "BDAB", "BCBA").sorted(), DynamicProgramming.longestCommonSubsequence("ABCBDAB", "BDCABA"))
    }
}

