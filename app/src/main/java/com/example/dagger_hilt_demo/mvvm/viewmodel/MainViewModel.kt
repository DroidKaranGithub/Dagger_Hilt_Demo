package com.example.dagger_hilt_demo.mvvm.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.dagger_hilt_demo.mvvm.model.Blog
import com.example.dagger_hilt_demo.repository.MainRepository
import com.example.dagger_hilt_demo.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class MainViewModel
@ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataSate: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataSate

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {

                is MainStateEvent.GetBlogEvents -> {
                    mainRepository.geBlog()
                        .onEach { dataState ->
                            _dataSate.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                else -> {
                }
            }
        }
    }
}

sealed class MainStateEvent {

    object GetBlogEvents : MainStateEvent()

    object None : MainStateEvent()
}