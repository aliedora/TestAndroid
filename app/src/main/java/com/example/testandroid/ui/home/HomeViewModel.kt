package com.example.testandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.data.repository.ApiRepository
import kotlinx.coroutines.launch

sealed class ApiResult {
    object Loading : ApiResult()
    data class Success(val title: String, val body: String) : ApiResult()
    data class Error(val message: String) : ApiResult()
}

class HomeViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _apiResult = MutableLiveData<ApiResult>()
    val apiResult: LiveData<ApiResult> = _apiResult

    fun fetchPost() {
        _apiResult.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                val post = repository.getPost(1)
                _apiResult.value = ApiResult.Success(post.title, post.body)
            } catch (e: Exception) {
                _apiResult.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}
