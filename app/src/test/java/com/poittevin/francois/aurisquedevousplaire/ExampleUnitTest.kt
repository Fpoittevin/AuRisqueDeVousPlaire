package com.poittevin.francois.aurisquedevousplaire

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun division_isCorrect() {

        val maxSmsChar = 149

        val nbTotalChar = 321

        val nbSms = nbTotalChar.div(maxSmsChar)


        assertEquals(2, nbSms)
    }
}