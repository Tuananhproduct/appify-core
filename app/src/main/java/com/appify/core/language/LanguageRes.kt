package com.appify.core.language

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.appify.core.R

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

val listLanguages = mutableListOf<ItemLanguage>().apply {
    add(ItemLanguage(R.drawable.flag_es, R.string.language_english, LanguageCode.ENGLISH.code))
    add(ItemLanguage(R.drawable.flag_india, R.string.language_hindi, LanguageCode.HINDI.code))
    add(ItemLanguage(R.drawable.flag_spain, R.string.language_spanish, LanguageCode.SPANISH.code))
    add(ItemLanguage(R.drawable.flag_france, R.string.language_french, LanguageCode.FRENCH.code))
    add(ItemLanguage(R.drawable.flag_arabic, R.string.language_arabic, LanguageCode.ARABIC.code))
    add(ItemLanguage(R.drawable.flag_bangladesh, R.string.language_bengali, LanguageCode.BENGALI.code))
    add(ItemLanguage(R.drawable.flag_russia, R.string.language_russian, LanguageCode.RUSSIAN.code))
    add(ItemLanguage(R.drawable.flag_portugal, R.string.language_portuguese, LanguageCode.PORTUGUESE.code))
    add(ItemLanguage(R.drawable.flag_indonesia, R.string.language_indonesian, LanguageCode.INDONESIAN.code))
    add(ItemLanguage(R.drawable.flag_germany, R.string.language_german, LanguageCode.GERMAN.code))
    add(ItemLanguage(R.drawable.flag_italy, R.string.language_italian, LanguageCode.ITALIAN.code))
    add(ItemLanguage(R.drawable.flag_korean, R.string.language_korean, LanguageCode.KOREAN.code))

}

fun List<ItemLanguage>.getPosition(eCode: String): Int {
    return this.indexOfFirst { it.code == eCode }
}