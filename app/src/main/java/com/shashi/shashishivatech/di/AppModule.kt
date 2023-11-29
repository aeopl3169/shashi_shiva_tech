package com.shashi.shashishivatech.di

import android.content.Context
import com.shashi.shashishivatech.api.ApplicationsAPI
import com.shashi.shashishivatech.data.networks.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): ApplicationsAPI {
        return remoteDataSource.buildApi(ApplicationsAPI::class.java, context)
    }

}