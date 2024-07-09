package org.dreamerslab.newslayer.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.dreamerslab.newslayer.core.data.repository.NewsArticlesPagingSource
import org.dreamerslab.newslayer.core.data.repository.NewsRepository

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    newsRepository: NewsRepository,
) : ViewModel() {
    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query

    @OptIn(FlowPreview::class)
    val pagingDataFlow = _query
        .filter { it.isNotBlank() }
        .debounce(300)
        .flatMapLatest {
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
        }

    fun updateQuery(value: String) {
        viewModelScope.launch {
            _query.update { value }
        }
    }
}
