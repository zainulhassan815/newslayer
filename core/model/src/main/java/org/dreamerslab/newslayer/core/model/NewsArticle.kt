package org.dreamerslab.newslayer.core.model

import kotlinx.datetime.Instant

/**
 * Represents a single news article.
 */
data class NewsArticle(
    val id: String,
    val title: String,
    val description: String,
    val headerImageUrl: String?,
    val source: NewsSource,
    val publishDate: Instant,
    val link: String,
)
