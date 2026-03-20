package com.example.testandroid.runner

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import io.qameta.allure.android.runners.AllureAndroidJUnitRunner
import java.io.File

class AllureInstrumentationRunner : AllureAndroidJUnitRunner() {

    override fun finish(resultCode: Int, results: Bundle) {
        copyResultsToDownloads()
        super.finish(resultCode, results)
    }

    private fun copyResultsToDownloads() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return

        val src = File(targetContext.filesDir, "allure-results")
        if (!src.exists()) {
            Log.w("AllureRunner", "Results dir not found: ${src.absolutePath}")
            return
        }

        val resolver = targetContext.contentResolver

        resolver.delete(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            "${MediaStore.Downloads.RELATIVE_PATH} LIKE ?",
            arrayOf("Download/allure-results/%")
        )

        val files = src.listFiles() ?: return
        var count = 0

        files.forEach { file ->
            val values = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, file.name)
                put(MediaStore.Downloads.MIME_TYPE, "application/json")
                put(MediaStore.Downloads.RELATIVE_PATH, "Download/allure-results")
            }
            resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)?.let { uri ->
                resolver.openOutputStream(uri)?.use { out ->
                    file.inputStream().use { it.copyTo(out) }
                }
                count++
            }
        }

        Log.d("AllureRunner", "Copied $count files to Download/allure-results")
    }
}
