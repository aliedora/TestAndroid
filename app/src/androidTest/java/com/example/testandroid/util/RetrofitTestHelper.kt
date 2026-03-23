package com.example.testandroid.util

import com.example.testandroid.data.network.ApiService
import com.example.testandroid.data.network.RetrofitClient

object RetrofitTestHelper {
    fun inject(service: ApiService) {
        val delegateField = RetrofitClient::class.java.getDeclaredField("apiService\$delegate")
        delegateField.isAccessible = true
        val lazyDelegate = delegateField.get(RetrofitClient)
        val valueField = lazyDelegate.javaClass.getDeclaredField("_value")
        valueField.isAccessible = true
        valueField.set(lazyDelegate, service)
    }
}
