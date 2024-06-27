package org.dreamerslab.newslayer.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.dreamerslab.newslayer.core.data.repository.NewsRepository
import org.dreamerslab.newslayer.core.data.repository.NewsRepositoryImpl
import org.dreamerslab.newslayer.core.data.repository.OfflineUserDataRepository
import org.dreamerslab.newslayer.core.data.repository.UserDataRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindUserDataRepository(
        impl: OfflineUserDataRepository
    ): UserDataRepository

    @Binds
    fun bindNewsRepository(
        impl: NewsRepositoryImpl
    ): NewsRepository

}
