package org.dreamerslab.newslayer.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.dreamerslab.newslayer.core.datastore.UserPreferences
import org.dreamerslab.newslayer.core.datastore.UserPreferencesSerializer

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideNewsLayerPreferencesDataSource(
        @ApplicationContext context: Context,
        userPreferencesSerializer: UserPreferencesSerializer
    ) : DataStore<UserPreferences> = DataStoreFactory.create(
        serializer = userPreferencesSerializer,
    ) {
        context.dataStoreFile("user_preferences.pb")
    }

}
