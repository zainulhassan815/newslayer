package org.dreamerslab.newslayer.core.model

/**
 * Represents [NewsArticlesPage] with additional user data
 */
data class UserResultPage(
    val totalNewsArticles: Int,
    val newsArticles: List<UserNewsArticle>,
    val nextPage: String?,
    val previousPage: String?,
)
