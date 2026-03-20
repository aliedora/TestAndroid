package com.example.testandroid.util

import android.content.Context

class PreferencesHelper(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveValue(value: String) {
        prefs.edit().putString(KEY_VALUE, value).apply()
    }

    fun getValue(): String? = prefs.getString(KEY_VALUE, null)

    fun clear() {
        prefs.edit().remove(KEY_VALUE).apply()
    }

    companion object {
        const val PREFS_NAME = "test_android_prefs"
        const val KEY_VALUE = "saved_value"
    }
}
