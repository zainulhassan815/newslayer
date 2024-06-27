package org.dreamerslab.newslayer.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dreamerslab.newslayer.core.model.NewsArticlesPage

@Serializable
data class NetworkResponseDto(
    @SerialName("status") val status: String,
    @SerialName("totalResults") val totalResults: Int,
    @SerialName("results") val results: List<NewsArticleDto>,
    @SerialName("nextPage") val nextPage: String? = null,
) {
    fun toResultPage(previousPage: String? = null): NewsArticlesPage = NewsArticlesPage(
        totalNewsArticles = totalResults,
        newsArticles = results.map(NewsArticleDto::toDomainNewsArticle),
        nextPage = nextPage,
        previousPage = previousPage
    )
}
