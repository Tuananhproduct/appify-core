package com.example.appdemo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ItemLanguage(@DrawableRes val flag: Int, @StringRes val country: Int, val code: String)

enum class LanguageCode(val code: String) {
    ENGLISH("en"),
    HINDI("hi"),
    SPANISH("es"),
    FRENCH("fr"),
    ARABIC("ar"),
    BENGALI("bn"),
    RUSSIAN("ru"),
    PORTUGUESE("pt"),
    INDONESIAN("in"),
    GERMAN("de"),
    ITALIAN("it"),
    KOREAN("ko"),

}

val list12Languages = mutableListOf<ItemLanguage>().apply {
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_english, LanguageCode.ENGLISH.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_hindi, LanguageCode.HINDI.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_spanish, LanguageCode.SPANISH.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_french, LanguageCode.FRENCH.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_arabic, LanguageCode.ARABIC.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_bengali, LanguageCode.BENGALI.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_russian, LanguageCode.RUSSIAN.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_portuguese, LanguageCode.PORTUGUESE.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_indonesian, LanguageCode.INDONESIAN.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_german, LanguageCode.GERMAN.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_italian, LanguageCode.ITALIAN.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_korean, LanguageCode.KOREAN.code))

}

val list6Languages = mutableListOf<ItemLanguage>().apply {
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_english, LanguageCode.ENGLISH.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_hindi, LanguageCode.HINDI.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_spanish, LanguageCode.SPANISH.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_french, LanguageCode.FRENCH.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_arabic, LanguageCode.ARABIC.code))
    add(ItemLanguage(R.drawable.ic_england_flag, R.string.language_bengali, LanguageCode.BENGALI.code))

}


fun List<ItemLanguage>.getCode(eCode: String): String? {
    val item = this.find { it.code == eCode }
    return item?.code
}

fun List<ItemLanguage>.getFlagRes(eCode: String): String? {
    val item = this.find { it.code == eCode }
    return item?.code
}

fun List<ItemLanguage>.getCountry(eCode: String): String? {
    val item = this.find { it.code == eCode }
    return item?.code
}