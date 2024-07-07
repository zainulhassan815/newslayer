package org.dreamerslab.newslayer.core.data.repository

import arrow.core.Either
import arrow.core.right
import arrow.retrofit.adapter.either.networkhandling.HttpError
import arrow.retrofit.adapter.either.networkhandling.IOError
import arrow.retrofit.adapter.either.networkhandling.UnexpectedCallError
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.dreamerslab.newslayer.core.data.api.NewsDataApi
import org.dreamerslab.newslayer.core.model.Category
import org.dreamerslab.newslayer.core.model.NewsArticlesPage

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsDataApi: NewsDataApi,
) : NewsRepository {

    override fun getNewsCategories(): Flow<Either<NewsRepositoryFailure, List<Category>>> =
        flow {
            listOf(
                "business",
                "crime",
                "domestic",
                "education",
                "entertainment",
                "environment",
                "food",
                "health",
                "lifestyle",
                "other",
                "politics",
                "science",
                "sports",
                "technology",
                "top",
                "tourism",
                "world"
            )
                .map(::Category)
                .right()
                .let { emit(it) }
        }

    override fun getNewsArticles(
        query: NewsArticlesQuery
    ): Flow<Either<NewsRepositoryFailure, NewsArticlesPage>> = flow {
        newsDataApi.getNewsArticles(
            searchQuery = query.searchQuery,
            categories = query.categories?.joinToString(separator = ",") { it.name },
            page = query.page
        ).map {
            it.toResultPage(previousPage = query.page)
        }.mapLeft {
            when (it) {
                is HttpError -> NewsRepositoryFailure.HttpError(it.code, it.message)
                is IOError -> NewsRepositoryFailure.NetworkError(it.cause)
                is UnexpectedCallError -> NewsRepositoryFailure.Unknown(it.cause)
            }
        }.let { emit(it) }
    }

}
