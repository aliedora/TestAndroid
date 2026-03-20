package com.example.testandroid.data.repository

import com.example.testandroid.data.model.Post
import com.example.testandroid.data.network.ApiService

class ApiRepositoryImpl(private val apiService: ApiService) : ApiRepository {
    override suspend fun getPost(id: Int): Post = apiService.getPost(id)
}
