package org.dreamerslab.newslayer.core.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.first
import org.dreamerslab.newslayer.core.model.NewsArticle

class NewsArticlesPagingSource(
    private val searchQuery: String? = null,
    private val categories: Set<String>? = null,
    private val newsRepository: NewsRepository
) : PagingSource<String, NewsArticle>() {

    override fun getRefreshKey(state: PagingState<String, NewsArticle>): String? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.nextKey
        }
    }

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, NewsArticle> {
        return newsRepository
            .getNewsArticles(
                query = NewsArticlesQuery(
                    searchQuery = searchQuery,
                    categories = categories,
                    page = params.key,
                )
            )
            .first()
            .fold(
                ifLeft = { failure ->
                    val cause = when (failure) {
                        is NewsRepositoryFailure.HttpError -> Throwable(failure.message)
                        is NewsRepositoryFailure.NetworkError -> failure.cause
                        is NewsRepositoryFailure.Unknown -> failure.cause
                    }
                    LoadResult.Error(cause)
                },
                ifRight = { resultPage ->
                    LoadResult.Page(
                        data = resultPage.newsArticles,
                        nextKey = resultPage.nextPage,
                        prevKey = resultPage.previousPage
                    )
                }
            )
    }

}
