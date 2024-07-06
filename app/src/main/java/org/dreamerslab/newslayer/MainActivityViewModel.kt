package org.dreamerslab.newslayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.dreamerslab.newslayer.core.data.repository.UserDataRepository
import org.dreamerslab.newslayer.core.model.UserData

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    val state = userDataRepository.userData.map {
        MainActivityState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = MainActivityState.Loading
    )
}

sealed interface MainActivityState {
    data object Loading : MainActivityState
    data class Success(
        val userData: UserData
    ) : MainActivityState
}
