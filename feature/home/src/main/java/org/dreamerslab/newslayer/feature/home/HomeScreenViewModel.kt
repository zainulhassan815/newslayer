package org.dreamerslab.newslayer.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.dreamerslab.newslayer.core.data.repository.NewsRepository
import org.dreamerslab.newslayer.core.data.repository.UserDataRepository
import org.dreamerslab.newslayer.core.model.Category

sealed interface HomeScreenState {
    data object Loading : HomeScreenState
    data object Error : HomeScreenState
    data class Success(
        val followedCategories: List<Category>,
        val otherCategories: List<Category>
    ) : HomeScreenState
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
    newsRepository: NewsRepository
) : ViewModel() {

    val state = combine(
        userDataRepository.userData,
        newsRepository.getNewsCategories()
    ) { userData, categoriesEither ->
        categoriesEither.fold(
            ifLeft = { HomeScreenState.Error },
            ifRight = { allCategories ->
                HomeScreenState.Success(
                    followedCategories = userData.followedCategories.map { Category(it) },
                    otherCategories = allCategories.filter { it.name !in userData.followedCategories }
                )
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = HomeScreenState.Loading,
    )
}
