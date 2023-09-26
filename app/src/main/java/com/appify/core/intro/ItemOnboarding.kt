package com.appify.core.intro

import android.os.Parcelable
import com.appify.core.DefaultValues
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemOnboarding(val imgIntro: Int = DefaultValues.EMPTY_VALUE, val txtIntro: String = DefaultValues.EMPTY_STRING) : Parcelable

