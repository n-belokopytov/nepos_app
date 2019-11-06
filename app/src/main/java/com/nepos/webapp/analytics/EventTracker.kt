package com.nepos.webapp.analytics

import android.content.Context
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics

class EventTracker(context: Context) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun trackEvent(event: Event) {
        firebaseAnalytics.logEvent(event.name, bundleOf(*event.parameters.toVarargArray()))
    }
}