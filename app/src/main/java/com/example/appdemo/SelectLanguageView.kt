package com.example.appdemo

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import com.example.appdemo.databinding.SelectLanguageBinding
import java.lang.annotation.RetentionPolicy


class SelectLanguageView : FrameLayout {

    private var backgroundItem: Drawable? = null
    private var backgroundItemSelected: Drawable? = null

    private var background: Drawable? = null

    private var styleTitle = R.style.ItemLanguage_Normal
    private var styleTitleSelected = R.style.ItemLanguage_Selected

    private var positionSelected = 0

    private var typeListLanguage = TypeLanguage.LIST_12_LANGUAGE.id

    private var listLanguageCustom = mutableListOf<ItemLanguage>()

    private val _binding = SelectLanguageBinding.inflate(LayoutInflater.from(context), this, true)

    private var itemLanguage: ((String) -> Unit?)? = null

    private val languageAdapter = SelectorLanguageAdapter {
        itemLanguage?.invoke(it)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initAttrs(attributeSet)
        init()
        setupViews()
    }

    private fun setupViews() {
        setBackground(background)

    }

    private fun initAttrs(attributeSet: AttributeSet) {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.SelectLanguageView, 0, 0).apply {
            backgroundItem = getDrawable(R.styleable.SelectLanguageView_x_backgroundItem)
            backgroundItemSelected = getDrawable(R.styleable.SelectLanguageView_x_backgroundItem_selected)

            //TODO Change Background
            background = getDrawable(R.styleable.SelectLanguageView_x_background)

            styleTitle = getInt(R.styleable.SelectLanguageView_x_titleItemStyle, styleTitle)
            styleTitleSelected = getInt(R.styleable.SelectLanguageView_x_titleItemStyle_selected, styleTitleSelected)

            typeListLanguage = getInt(R.styleable.SelectLanguageView_x_listLanguage, typeListLanguage)

            positionSelected = getInt(R.styleable.SelectLanguageView_x_positionSelected, positionSelected)
        }
    }

    fun onChangeLanguageListener(languageCode: (String) -> Unit) {
        this.itemLanguage = languageCode
    }

    fun setToolbarListener(clickLeft: () -> Unit, clickRight: () -> Unit) {
        _binding.xToolbar.setToolbarClickListener(
            clickLeft = {
                clickLeft.invoke()
            },

            clickRight = {
                clickRight.invoke()
            })
    }

    override fun setBackground(background: Drawable?) {
        _binding.xToolbar.setBackground(ContextCompat.getColor(context, android.R.color.transparent))
        _binding.root.background = background
    }

    fun setListLanguageCustom(list: List<ItemLanguage>) {
        listLanguageCustom.clear()
        listLanguageCustom.addAll(list)
    }

    private fun init() {
        setupConfigDefault()

        _binding.rvLanguage.adapter = languageAdapter
        languageAdapter.apply {
            when (typeListLanguage) {
                TypeLanguage.LIST_12_LANGUAGE.id -> {
                    submitList(list12Languages)
                }

                TypeLanguage.LIST_6_LANGUAGE.id -> {
                    submitList(list6Languages)
                }

                TypeLanguage.LIST_CUSTOM.id -> {
                    submitList(listLanguageCustom)
                }
            }


            setBackgroundItem(backgroundItem, backgroundItemSelected)
            setStyleTextAppearance(styleTitle, styleTitleSelected)
            setPositionSelected(positionSelected)
        }


    }

    private fun setupConfigDefault() {
        if (background == null) {
            background = ContextCompat.getDrawable(context, R.color.white)
        }

        if (backgroundItem == null) {
            backgroundItem = ContextCompat.getDrawable(context, R.drawable.background_language)
        }

        if (backgroundItemSelected == null) {
            backgroundItemSelected = ContextCompat.getDrawable(context, R.drawable.background_language_selector)
        }
    }


    enum class TypeLanguage(val id: Int) {
        LIST_12_LANGUAGE(12), LIST_6_LANGUAGE(6), LIST_CUSTOM(3)
    }


}