package org.dreamerslab.newslayer.core.model

/**
 * Represents a single news response page fetched from backend.
 */
data class NewsArticlesPage(
    val totalNewsArticles: Int,
    val newsArticles: List<NewsArticle>,
    val nextPage: String?,
    val previousPage: String?,
)
