package org.dreamerslab.newslayer

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination

@Serializable
data class ArticleDetails(
    val articleId: String
) : Destination

@Serializable
data object OnBoarding : Destination

@Serializable
sealed interface TopLevelDestination : Destination

@Serializable
data object Home : TopLevelDestination

@Serializable
data object Search : TopLevelDestination

@Serializable
data object Downloads : TopLevelDestination
