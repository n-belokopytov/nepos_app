package com.nepos.webapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nepos.webapp.analytics.Event
import com.nepos.webapp.analytics.EventTracker

class OfflineSplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.offline_splash_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        EventTracker(requireContext()).trackEvent(OfflineSplashFragmentViewEvent())
    }
}

class OfflineSplashFragmentViewEvent : Event("OfflineSplashFragmentViewEvent", mapOf())