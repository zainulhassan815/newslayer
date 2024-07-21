package org.dreamerslab.newslayer.core.data.api

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import org.dreamerslab.newslayer.core.model.Category

object ApiUtils {

    fun toQueryString(categories: Set<Category>): String =
        categories.joinToString(separator = ",") { it.name }

    @OptIn(FormatStringsInDatetimeFormats::class)
    val ApiDateTimeFormat = DateTimeComponents.Format {
        byUnicodePattern("uuuu-MM-dd HH:mm:ss")
    }

    fun parsePublishDate(dateTimeStr: String) = try {
        Instant.parse(dateTimeStr, ApiDateTimeFormat)
    } catch (e: Exception) {
        Clock.System.now()
    }

}
