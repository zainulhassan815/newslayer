package org.dreamerslab.newslayer.core.model

/**
 * Represents a [Category] that a user can follow
 */
data class FollowableCategory(
    val name: String,
    val isFollowed: Boolean,
)
