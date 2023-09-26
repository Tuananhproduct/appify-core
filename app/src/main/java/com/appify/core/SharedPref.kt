package com.appify.core

import android.content.Context
import android.content.SharedPreferences


object SharedPref {
    private const val PREFS_NAME = "MyPrefs"
    private const val KEY_LANGUAGE_CODE = "languageCode"

    private var sharedPref: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveLanguageCode(languageCode: String) {
        val editor = sharedPref?.edit()
        editor?.putString(KEY_LANGUAGE_CODE, languageCode)
        editor?.apply()
    }

    fun getLanguageCode(): String {
        return sharedPref?.getString(KEY_LANGUAGE_CODE, null) ?: DefaultValues.LANGUAGE_CODE_DEFAULT
    }
}