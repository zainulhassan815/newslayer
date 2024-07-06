package org.dreamerslab.newslayer.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.dreamerslab.newslayer.core.data.repository.NewsArticlesPagingSource
import org.dreamerslab.newslayer.core.data.repository.NewsRepository

@HiltViewModel(assistedFactory = CategoryListViewModel.CategoryListViewModelFactory::class)
class CategoryListViewModel @AssistedInject constructor(
    @Assisted category: String,
    newsRepository: NewsRepository,
) : ViewModel() {

    @AssistedFactory
    interface CategoryListViewModelFactory {
        fun create(category: String): CategoryListViewModel
    }

    val pagingData = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        ),
        pagingSourceFactory = {
            NewsArticlesPagingSource(
                categories = setOf(category),
                newsRepository = newsRepository
            )
        }
    )
        .flow
        .cachedIn(viewModelScope)
}
