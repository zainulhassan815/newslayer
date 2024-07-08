package org.dreamerslab.newslayer.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.getOrElse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.dreamerslab.newslayer.core.data.repository.NewsRepository
import org.dreamerslab.newslayer.core.data.repository.UserDataRepository
import org.dreamerslab.newslayer.core.model.DarkThemeConfig
import org.dreamerslab.newslayer.core.model.FollowableCategory
import org.dreamerslab.newslayer.core.model.UserData

sealed interface ConfigScreenState {
    data object Loading : ConfigScreenState
    data class Success(
        val userData: UserData,
        val categories: List<FollowableCategory>
    ) : ConfigScreenState

    /**
     * Checks if the user can continue to next page
     */
    val canProceedFurther: Boolean
        get() = this is Success && this.userData.followedCategories.isNotEmpty()
}

@HiltViewModel
class ConfigScreenViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    newsRepository: NewsRepository
) : ViewModel() {

    private val categories = newsRepository.getNewsCategories()
        .map { it.getOrElse { emptyList() } }

    val state = userDataRepository.userData
        .combine(categories) { userData, categories ->
            ConfigScreenState.Success(
                userData = userData,
                categories = categories.map { category ->
                    FollowableCategory(
                        name = category.name,
                        isFollowed = category.name in userData.followedCategories
                    )
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ConfigScreenState.Loading
        )

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userDataRepository.setNotificationsEnabled(enabled)
        }
    }

    fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun setCategoryIdFollowed(id: String, followed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setCategoryIdFollowed(id, followed)
        }
    }

    fun setShouldHideOnboarding(shouldHide: Boolean) {
        viewModelScope.launch {
            userDataRepository.setShouldHideOnboarding(shouldHide)
        }
    }
}
