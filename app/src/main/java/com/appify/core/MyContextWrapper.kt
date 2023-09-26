package com.appify.core

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.Locale


//Use this change language API if you use API > 25
//https://stackoverflow.com/a/40704077/20058248

class MyContextWrapper(base: Context) : ContextWrapper(base) {

    companion object {
        fun wrap(context: Context): ContextWrapper {
            SharedPref.init(context)
            val language = SharedPref.getLanguageCode()
            val config = context.resources.configuration
            val sysLocale = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                getSystemLocale(config)
            } else {
                getSystemLocaleLegacy(config)
            }
            if (language.isNotEmpty() && sysLocale.language != language) {
                val locale = Locale(language)
                Locale.setDefault(locale)
                setSystemLocale(config, locale)
            }
            context.createConfigurationContext(config)
            return MyContextWrapper(context)


        }

        @Suppress("DEPRECATION")
        private fun getSystemLocaleLegacy(config: Configuration): Locale {
            return config.locale
        }

        private fun getSystemLocale(config: Configuration): Locale {
            return config.locales[0]
        }

        private fun setSystemLocale(config: Configuration, locale: Locale) {
            config.setLocale(locale)
        }
    }
}