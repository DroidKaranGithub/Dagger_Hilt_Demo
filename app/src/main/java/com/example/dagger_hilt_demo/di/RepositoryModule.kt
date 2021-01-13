package com.example.dagger_hilt_demo.di

import com.example.dagger_hilt_demo.repository.MainRepository
import com.example.dagger_hilt_demo.retrofit.BlogRetrofit
import com.example.dagger_hilt_demo.retrofit.NetworkMapper
import com.example.dagger_hilt_demo.room.BlogDao
import com.example.dagger_hilt_demo.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }

}