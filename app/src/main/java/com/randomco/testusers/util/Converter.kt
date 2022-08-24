package com.randomco.testusers.util

import java.text.SimpleDateFormat
import java.util.*

fun String.dateFormat() : String{
    val dfSource = SimpleDateFormat(SIMPLE_DATE_FORMAT_SOURCE_PATTERN, Locale.getDefault())
    val dfDest = SimpleDateFormat(SIMPLE_DATE_FORMAT_DEST_PATTERN, Locale.getDefault())
    return dfSource.parse(this)?.let { dfDest.format(it) } ?: ""
}