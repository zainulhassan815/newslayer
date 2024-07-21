package org.dreamerslab.newslayer.feature.articledetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.dreamerslab.newslayer.core.data.repository.NewsRepository
import org.dreamerslab.newslayer.core.model.NewsArticle

sealed interface ArticleDetailsState {
    data object InvalidArticleId : ArticleDetailsState
    data object Error : ArticleDetailsState
    data object Loading : ArticleDetailsState
    data class Success(
        val article: NewsArticle
    ) : ArticleDetailsState
}

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository,
) : ViewModel() {

    val state = savedStateHandle
        .getStateFlow<String?>("articleId", null)
        .filterNotNull()
        .flatMapLatest { articleId ->
            newsRepository.getNewsArticleById(articleId)
        }.map { result ->
            result.fold(
                ifLeft = { ArticleDetailsState.Error },
                ifRight = { article ->
                    when {
                        article == null -> ArticleDetailsState.InvalidArticleId
                        else -> ArticleDetailsState.Success(article)
                    }
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ArticleDetailsState.Loading
        )

    fun launchUrl(context: Context, url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).let {
            context.startActivity(it)
        }
    }

}
