package io.taipei.androidtaipei20180524.provider.preferences

import android.app.Application
import android.content.Context

/**
 * Provides sharedPreferences without context.
 * should be initial when [Application.onCreate]
 */
class SharedPreferencesProvider(
        private var application: Application
) : AppSharedPreferences {

    override fun sharedPreferences() = application.getSharedPreferences(NAME, Context.MODE_PRIVATE)!!

    companion object {
        private const val NAME = "HouseRealPrice"
    }
}