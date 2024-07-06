package org.dreamerslab.newslayer.core.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dreamerslab.newslayer.core.model.NewsArticle
import org.dreamerslab.newslayer.core.model.NewsSource

@Serializable
data class NewsArticleDto(
    @SerialName("article_id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("link") val link: String,
    @SerialName("keywords") val keywords: List<String> = emptyList(),
    @SerialName("description") val description: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("pubDate") val publishDate: String,
    @SerialName("source_id") val sourceId: String,
    @SerialName("source_url") val sourceUrl: String,
    @SerialName("source_icon") val sourceIcon: String? = null,
) {
    companion object {
        @OptIn(FormatStringsInDatetimeFormats::class)
        private val ApiDateTimeFormat = DateTimeComponents.Format {
            byUnicodePattern("uuuu-MM-dd HH:mm:ss")
        }
    }

    fun toDomainNewsArticle(): NewsArticle = NewsArticle(
        id = id,
        title = title,
        description = description ?: "",
        headerImageUrl = imageUrl,
        source = NewsSource(
            id = sourceId,
            url = sourceUrl,
            icon = sourceIcon,
        ),
        link = link,
        publishDate = parsePublishDate(publishDate)
    )

    private fun parsePublishDate(dateTimeStr: String) = try {
        Instant.parse(dateTimeStr, ApiDateTimeFormat)
    } catch (e: Exception) {
        Clock.System.now()
    }
}
