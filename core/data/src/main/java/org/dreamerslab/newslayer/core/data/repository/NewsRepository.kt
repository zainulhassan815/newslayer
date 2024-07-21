package org.dreamerslab.newslayer.core.data.repository

import arrow.core.Either
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import org.dreamerslab.newslayer.core.model.Category
import org.dreamerslab.newslayer.core.model.NewsArticle
import org.dreamerslab.newslayer.core.model.NewsArticlesPage

data class NewsArticlesQuery(
    val searchQuery: String? = null,
    val categories: Set<Category>? = null,
    val page: String? = null,
)

interface NewsRepository {

    fun getNewsCategories(): Flow<Either<NewsRepositoryFailure, List<Category>>>

    fun getNewsArticles(
        query: NewsArticlesQuery = NewsArticlesQuery()
    ): Flow<Either<NewsRepositoryFailure, NewsArticlesPage>>

    fun getNewsArticleById(
        articleId: String
    ): Flow<Either<NewsRepositoryFailure, NewsArticle?>>

}

sealed interface NewsRepositoryFailure {
    data class HttpError(
        val code: Int,
        val message: String
    ) : NewsRepositoryFailure

    data class NetworkError(
        val cause: IOException
    ) : NewsRepositoryFailure

    data class Unknown(
        val cause: Throwable
    ) : NewsRepositoryFailure
}
