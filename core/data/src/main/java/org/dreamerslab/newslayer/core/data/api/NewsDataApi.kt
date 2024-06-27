package org.dreamerslab.newslayer.core.data.api

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import org.dreamerslab.newslayer.core.data.model.NetworkResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDataApi {
    @GET("api/1/latest?language=en")
    suspend fun getNewsArticles(
        @Query("q") searchQuery: String? = null,
        @Query("category") categories: Set<String>? = null,
        @Query("page") page: String? = null,
    ): Either<CallError,NetworkResponseDto>
}
