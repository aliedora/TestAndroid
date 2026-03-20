package com.example.testandroid.data.repository

import com.example.testandroid.data.model.Post

interface ApiRepository {
    suspend fun getPost(id: Int): Post
}
