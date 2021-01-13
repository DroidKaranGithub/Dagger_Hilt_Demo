package com.example.dagger_hilt_demo.repository

import com.example.dagger_hilt_demo.mvvm.model.Blog
import com.example.dagger_hilt_demo.retrofit.BlogRetrofit
import com.example.dagger_hilt_demo.retrofit.NetworkMapper
import com.example.dagger_hilt_demo.room.BlogDao
import com.example.dagger_hilt_demo.room.CacheMapper
import com.example.dagger_hilt_demo.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun geBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntities(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntites(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}
