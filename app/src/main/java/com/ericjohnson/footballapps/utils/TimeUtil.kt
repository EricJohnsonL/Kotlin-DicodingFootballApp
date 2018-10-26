package com.ericjohnson.footballapps.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by johnson on 06/10/18.
 */

class TimeUtil {

    companion object {
        private val INDONESIA = Locale("en", "ID")

        fun getDateTime(dateTime: String): String {
            lateinit var formattedDate: Date
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.getDefault())

            when {
                checkDateTimeFormatWithZone(dateTime) -> formattedDate = formatter.parse(dateTime)
                checkDateTimeFormatDefault(dateTime) -> formattedDate = formatter.parse("$dateTime+0000")
            }

            val dayFormatter = SimpleDateFormat("EEEE, d MMMM yyyy-HH:mm", INDONESIA)
            return dayFormatter.format(formattedDate)
        }

        fun getDateTimeToSplit(dateTime: String): String {

            lateinit var formattedDate: Date
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.getDefault())

            when {
                checkDateTimeFormatWithZone(dateTime) -> formattedDate = formatter.parse(dateTime)
                checkDateTimeFormatDefault(dateTime) -> formattedDate = formatter.parse("$dateTime+0000")
            }

            val dayFormatter = SimpleDateFormat("yyyy MM d HH mm ss", INDONESIA)
            return dayFormatter.format(formattedDate)
        }

        private fun checkDateTimeFormatWithZone(dateTime: String): Boolean {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.getDefault())
            formatter.isLenient = false
            try {
                formatter.parse(dateTime)
            } catch (e: ParseException) {
                return false
            }
            return true
        }

        private fun checkDateTimeFormatDefault(dateTime: String): Boolean {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            formatter.isLenient = false
            try {
                formatter.parse(dateTime)
            } catch (e: ParseException) {
                return false
            }
            return true
        }

    }

}