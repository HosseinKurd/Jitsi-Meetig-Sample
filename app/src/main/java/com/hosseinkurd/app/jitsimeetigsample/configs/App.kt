package com.hosseinkurd.app.jitsimeetigsample.configs

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import modularization.libraries.utils.MagicalNavigator
import modularization.libraries.utils.helpers.LocaleHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initApplication()

        // MagicalNavigator.getInstance().navigateToSplashActivity(this)
    }

    private fun initApplication() {
        LocaleHelper.setApplicationLanguage(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.setLocale(applicationContext, LocaleHelper.getPersistedLocale(this))

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

}