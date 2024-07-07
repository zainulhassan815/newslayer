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
import org.dreamerslab.newslayer.core.model.Category

@HiltViewModel(assistedFactory = CategoryListViewModel.CategoryListViewModelFactory::class)
class CategoryListViewModel @AssistedInject constructor(
    @Assisted categories: List<Category>,
    newsRepository: NewsRepository,
) : ViewModel() {

    @AssistedFactory
    interface CategoryListViewModelFactory {
        fun create(categories: List<Category>): CategoryListViewModel
    }

    val pagingData = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        ),
        pagingSourceFactory = {
            NewsArticlesPagingSource(
                categories = categories.toSet(),
                newsRepository = newsRepository
            )
        }
    )
        .flow
        .cachedIn(viewModelScope)
}
