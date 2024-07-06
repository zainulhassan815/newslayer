package org.dreamerslab.newslayer.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.dreamerslab.newslayer.core.data.repository.NewsArticlesQuery
import org.dreamerslab.newslayer.core.data.repository.NewsRepository
import org.dreamerslab.newslayer.core.model.NewsArticle

sealed interface CategoryListState {
    data object Loading : CategoryListState
    data object Error : CategoryListState
    data class Success(
        val articles: List<NewsArticle>
    ) : CategoryListState
}

@HiltViewModel(assistedFactory = CategoryListViewModel.CategoryListViewModelFactory::class)
class CategoryListViewModel @AssistedInject constructor(
    @Assisted category: String,
    newsRepository: NewsRepository,
) : ViewModel() {

    @AssistedFactory
    interface CategoryListViewModelFactory {
        fun create(category: String): CategoryListViewModel
    }

    val state = newsRepository.getNewsArticles(
        query = NewsArticlesQuery(
            categories = setOf(category),
        )
    ).map { result ->
        result.fold(
            ifLeft = { CategoryListState.Error },
            ifRight = { articlesPage ->
                CategoryListState.Success(articlesPage.newsArticles)
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = CategoryListState.Loading
    )
}
