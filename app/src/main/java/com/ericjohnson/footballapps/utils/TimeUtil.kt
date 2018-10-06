package com.ericjohnson.footballapps.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by johnson on 06/10/18.
 */

class TimeUtil {

    companion object {
        fun getFormattedDate(date: String): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate: Date = formatter.parse(date)

            val dayFormatter = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
            return dayFormatter.format(formattedDate)
        }

    }

}