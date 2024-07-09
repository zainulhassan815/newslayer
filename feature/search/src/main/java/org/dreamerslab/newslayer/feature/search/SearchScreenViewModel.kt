package org.dreamerslab.newslayer.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.dreamerslab.newslayer.core.data.repository.NewsArticlesPagingSource
import org.dreamerslab.newslayer.core.data.repository.NewsRepository
import org.dreamerslab.newslayer.core.model.NewsArticle

sealed interface SearchScreenState {
    data object Initial : SearchScreenState

    data class Data(
        val pagingDataFlow: Flow<PagingData<NewsArticle>>
    ) : SearchScreenState
}

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    newsRepository: NewsRepository,
) : ViewModel() {
    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query

    @OptIn(FlowPreview::class)
    val state = _query
        .filter { it.isNotBlank() }
        .debounce(300)
        .mapLatest {
            SearchScreenState.Data(
                Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        initialLoadSize = 10,
                    ),
                    pagingSourceFactory = {
                        NewsArticlesPagingSource(
                            newsRepository = newsRepository,
                            searchQuery = _query.value.trim()
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SearchScreenState.Initial
        )


    fun updateQuery(value: String) {
        viewModelScope.launch {
            _query.update { value }
        }
    }
}
