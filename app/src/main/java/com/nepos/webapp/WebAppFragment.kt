package com.nepos.webapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_web_app.*

class WebAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_web_app, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        content_web_view.webChromeClient = WebChromeClient()
        content_web_view.webViewClient = WebViewClient()

        content_web_view.settings.javaScriptEnabled = true
        content_web_view.settings.domStorageEnabled = true

        content_web_view.loadUrl(generateInitialUrl(requireContext()))
    }

    private fun generateInitialUrl(context: Context) =
        "https://${context.getString(R.string.api_url)}/enter-usergroup/${context.getString(R.string.usergroup)}"
}
