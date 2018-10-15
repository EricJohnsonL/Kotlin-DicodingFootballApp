package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.utils.TimeUtil
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by johnson on 12/10/18.
 */

class DateConvertTest {

    @Test
    fun dateConvertTest() {
        val date: String = TimeUtil.getFormattedDate("2018-10-12")
        assertEquals("Friday, 12 October 2018", date)
    }
}