package org.dreamerslab.newslayer.core.data.di

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.dreamerslab.newslayer.core.data.BuildConfig
import org.dreamerslab.newslayer.core.data.api.ApiKeyInterceptor
import org.dreamerslab.newslayer.core.data.api.NewsDataApi
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.apply {
            addInterceptor(ApiKeyInterceptor(BuildConfig.NEWS_DATA_API))

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
                addInterceptor(loggingInterceptor)
            }
        }

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        return Retrofit.Builder()
            .baseUrl("https://newsdata.io")
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    contentType = "application/json; charset=UTF8".toMediaType(),
                )
            )
            .addCallAdapterFactory(EitherCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDataApi(
        retrofit: Retrofit
    ): NewsDataApi = retrofit.create(NewsDataApi::class.java)

}
