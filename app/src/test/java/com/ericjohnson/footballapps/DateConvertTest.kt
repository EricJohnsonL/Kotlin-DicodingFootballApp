package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.utils.TimeUtil
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by johnson on 12/10/18.
 */

class DateTimeConvertTest {

    @Test
    fun dateTimeConvertWithZoneTest() {
        val date: String = TimeUtil.getDateTime("2018-10-12 10:00:00+0000")
        assertEquals("Friday, 12 October 2018-17:00", date)
    }

    @Test
    fun dateTimeConvertDefaultTest() {
        val date: String = TimeUtil.getDateTime("2018-10-12 10:00:00")
        assertEquals("Friday, 12 October 2018-17:00", date)
    }

    @Test
    fun dateTimeConvertWithZoneToSplitTest() {
        val date: String = TimeUtil.getDateTimeToSplit("2018-10-12 10:00:00+0000")
        assertEquals("2018 10 12 17 00 00", date)
    }

    @Test
    fun dateTimeConvertDefaultToSplitTest() {
        val date: String = TimeUtil.getDateTimeToSplit("2018-10-12 10:00:00")
        assertEquals("2018 10 12 17 00 00", date)
    }
}