package com.example.githubapicompose.utils

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun HtmlLoader(htmlName: String) {
    AndroidView(factory ={
        WebView(it).apply {
            webViewClient = WebViewClient()
            loadUrl(htmlName)
        }
    })
}