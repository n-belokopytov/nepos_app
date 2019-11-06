package com.nepos.webapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nepos.webapp.analytics.EventTracker

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenNeposActivity : AppCompatActivity() {

    private val Context.isConnected: Boolean
        get() = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).let {
            return@let it.getLinkProperties(it.activeNetwork) != null
        }

    private val webAppFragment = WebAppFragment()
    private val offlineSplashFragment = OfflineSplashFragment()
    lateinit var eventTracker: EventTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen_nepos)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()
    }

    private fun navigateToWebApp() {
        if (webAppFragment.isAdded && offlineSplashFragment.isAdded) {
            supportFragmentManager.beginTransaction().remove(offlineSplashFragment).commit()
        } else {
            if (!webAppFragment.isAdded) {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fullscreen_content,
                        webAppFragment,
                        WebAppFragment::class.simpleName
                    )
                    .commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (isConnected) {
            navigateToWebApp()
        } else {
            navigateToOfflineSplash()
        }
    }

    private fun navigateToOfflineSplash() {
        if (webAppFragment.isAdded && offlineSplashFragment.isAdded) {
            //nop
        } else {
            if (!offlineSplashFragment.isAdded) {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fullscreen_content,
                        offlineSplashFragment,
                        OfflineSplashFragment::class.simpleName
                    )
                    .commit()
            }
        }
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }
}
