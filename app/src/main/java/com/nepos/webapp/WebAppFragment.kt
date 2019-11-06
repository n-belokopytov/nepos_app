package com.nepos.webapp

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != requireContext().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
                WebView.setWebContentsDebuggingEnabled(true)
            }
        }

        content_web_view.webChromeClient = WebChromeClient()
        content_web_view.webViewClient = buildWebViewClientWithBrowserRedirect(BuildConfig.API_URL)

        content_web_view.settings.javaScriptEnabled = true
        content_web_view.settings.domStorageEnabled = true
        content_web_view.settings.mediaPlaybackRequiresUserGesture = false
    }

    override fun onStart() {
        super.onStart()
        if (content_web_view.url == null) {
            content_web_view.loadUrl(
                generateInitialUrl(
                    BuildConfig.API_URL,
                    BuildConfig.USER_GROUP
                )
            )
        }
    }

    private fun buildWebViewClientWithBrowserRedirect(apiUrl: String) = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            webResourceRequest: WebResourceRequest
        ) =
            if (webResourceRequest.isForMainFrame && webResourceRequest.url.host?.contains(apiUrl) != true) {
                navigateToUrlInBrowser(webResourceRequest.url.toString(), requireContext())
                true
            } else {
                false
            }
    }

    fun navigateToUrlInBrowser(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    private fun generateInitialUrl(apiString: String, userGroupString: String) =
        "https://$apiString/enter-usergroup/$userGroupString"
}
