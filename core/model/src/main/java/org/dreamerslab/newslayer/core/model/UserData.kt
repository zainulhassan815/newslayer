package org.dreamerslab.newslayer.core.model

data class UserData(
    val followedCategories: Set<String>,
    val darkThemeConfig: DarkThemeConfig,
    val notificationsEnabled: Boolean,
    val shouldHideOnboarding: Boolean,
)
