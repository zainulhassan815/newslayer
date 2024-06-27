package org.dreamerslab.newslayer.core.model

import kotlinx.datetime.Instant

/**
 * Represents [UserNewsArticle] with additional user data
 */
data class UserNewsArticle(
    val id: String,
    val title: String,
    val description: String,
    val headerImageUrl: String?,
    val source: NewsSource,
    val publishDate: Instant,
    val link: String,
    val isSaved: Boolean
)
