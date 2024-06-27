package org.dreamerslab.newslayer.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.dreamerslab.newslayer.core.model.DarkThemeConfig
import org.dreamerslab.newslayer.core.model.UserData

interface UserDataRepository {

    /**
     * Stream of [UserData]
     */
    val userData: Flow<UserData>

    /**
     * Sets the user's currently followed categories
     */
    suspend fun setFollowedCategoryIds(categoryIds: Set<String>)

    /**
     * Sets the user's newly followed/unfollowed category
     */
    suspend fun setCategoryIdFollowed(followedCategoryId: String, followed: Boolean)

    /**
     * Sets desired dark theme preference
     */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Sets whether the user has enabled notifications
     */
    suspend fun setNotificationsEnabled(enabled: Boolean)

    /**
     * Sets whether the user has completed onboarding process
     */
    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)

}
