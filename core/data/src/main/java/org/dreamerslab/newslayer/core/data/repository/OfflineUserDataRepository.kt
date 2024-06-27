package org.dreamerslab.newslayer.core.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import org.dreamerslab.newslayer.core.datastore.NewsLayerPreferencesDataSource
import org.dreamerslab.newslayer.core.model.DarkThemeConfig
import org.dreamerslab.newslayer.core.model.UserData

@Singleton
class OfflineUserDataRepository @Inject constructor(
    private val preferencesDataSource: NewsLayerPreferencesDataSource
) : UserDataRepository {
    override val userData: Flow<UserData> = preferencesDataSource.userData

    override suspend fun setFollowedCategoryIds(categoryIds: Set<String>) {
        preferencesDataSource.setFollowedCategoryIds(categoryIds)
    }

    override suspend fun setCategoryIdFollowed(followedCategoryId: String, followed: Boolean) {
        preferencesDataSource.setCategoryIdFollowed(followedCategoryId, followed)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        preferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        preferencesDataSource.setNotificationsEnabled(enabled)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        preferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
    }


}
