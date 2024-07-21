package org.dreamerslab.newslayer.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dreamerslab.newslayer.core.data.api.ApiUtils
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
    fun toDomainNewsArticle(): NewsArticle = NewsArticle(
        id = id,
        title = title,
        description = description.orEmpty(),
        headerImageUrl = imageUrl,
        source = NewsSource(
            id = sourceId,
            url = sourceUrl,
            icon = sourceIcon,
        ),
        link = link,
        publishDate = ApiUtils.parsePublishDate(publishDate)
    )
}
