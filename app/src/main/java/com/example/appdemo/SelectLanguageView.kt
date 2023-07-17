package com.example.appdemo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdemo.databinding.SelectLanguageBinding


/**
 * SelectLanguageView is a custom view for selecting a language.
 *
 * This view displays a list of languages that can be selected by the user. It provides
 * customization options for appearance and behavior through XML attributes. The selected
 * language can be obtained by registering a listener using [onChangeLanguageListener].
 *
 * To use this view, add the SelectLanguageView to your layout XML file. You can customize
 * its appearance and behavior using the available XML attributes. Register a listener to
 * be notified when the language is changed using [onChangeLanguageListener].
 *
 * XML Attributes:
 * - x_backgroundItem: Background drawable for each language item.
 * - x_backgroundItem_selected: Background drawable for the selected language item.
 * - x_background: Background drawable for the SelectLanguageView.
 * - x_titleItemStyle: Style resource for the language item title when not selected.
 * - x_titleItemStyle_selected: Style resource for the language item title when selected.
 * - x_listLanguage: Type of language list to display (12 languages, 6 languages, or custom list).
 * - x_positionSelected: Position of the initially selected language item.
 *
 * Example usage in XML:
 * ```
 * <com.example.appdemo.SelectLanguageView
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:x_backgroundItem="@drawable/language_item_background"
 *     app:x_backgroundItem_selected="@drawable/language_item_background_selected"
 *     app:x_background="@color/white"
 *     app:x_titleItemStyle="@style/TextTitle_Normal"
 *     app:x_titleItemStyle_selected="@style/TextTitle_Selected"
 *     app:x_listLanguage="LIST_12_LANGUAGE"
 *     app:x_positionSelected="0" />
 * ```
 *
 * @constructor Creates an instance of SelectLanguageView.
 * @param context The context of the view.
 * @param attributeSet The attribute set containing XML attributes.
 */
class SelectLanguageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var backgroundItem: Drawable? = null
    private var backgroundItemSelected: Drawable? = null

    private var background: Drawable? = null

    private var styleTitle = R.style.TextTitle_Normal
    private var styleTitleSelected = R.style.TextTitle_Selected

    private var styleBottomAction = R.style.XToolbar_Bottom
    private var colorBottomAction = Color.WHITE
    private var backgroundBottomAction: Drawable? = null
    private var showBottomAction = false

    private var style = 0
    private var styleSelected = 0

    private var heightItem = 0
    private var sizeFlag = 0

    private var column = 1

    private var positionSelected = 0

    private var typeListLanguage = TypeLanguage.LIST_12_LANGUAGE

    private var gravityAds = GravityAds.BOTTOM

    private var listLanguageCustom = mutableListOf<ItemLanguage>()

    private val _binding = SelectLanguageBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private var itemLanguage: ((String) -> Unit)? = null

    private val languageAdapter = SelectorLanguageAdapter {
        itemLanguage?.invoke(it)
    }

    init {
        attributeSet?.let { attrs ->
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SelectLanguageView,
                0,
                0
            ).apply {
                backgroundItem = getDrawable(R.styleable.SelectLanguageView_x_backgroundItem)
                backgroundItemSelected =
                    getDrawable(R.styleable.SelectLanguageView_x_backgroundItem_selected)

                background = getDrawable(R.styleable.SelectLanguageView_x_background)

                styleTitle = getResourceId(R.styleable.SelectLanguageView_x_titleItemStyle, styleTitle)
                styleTitleSelected = getResourceId(R.styleable.SelectLanguageView_x_titleItemStyle_selected, styleTitleSelected)

                style = getResourceId(R.styleable.SelectLanguageView_x_itemStyle, style)
                styleSelected = getResourceId(R.styleable.SelectLanguageView_x_itemStyle_selected, styleSelected)

                typeListLanguage = TypeLanguage.fromId(getInt(R.styleable.SelectLanguageView_x_listLanguage, typeListLanguage.id))

                gravityAds = GravityAds.fromId(getInt(R.styleable.SelectLanguageView_x_gravityAds, gravityAds.id))

                heightItem = getDimensionPixelSize(R.styleable.SelectLanguageView_x_heightItem, heightItem)
                sizeFlag = getDimensionPixelSize(R.styleable.SelectLanguageView_x_sizeFlag, sizeFlag)

                column = getInt(R.styleable.SelectLanguageView_x_column, column)

                positionSelected = getInt(R.styleable.SelectLanguageView_x_positionSelected, positionSelected)

                backgroundBottomAction = getDrawable(R.styleable.SelectLanguageView_x_backgroundBottomAction)
                colorBottomAction = getColor(R.styleable.SelectLanguageView_x_colorTitleBottomAction, colorBottomAction)
                styleBottomAction = getResourceId(R.styleable.SelectLanguageView_x_bottomActionStyle, styleBottomAction)
                showBottomAction = getBoolean(R.styleable.SelectLanguageView_x_canShowBottomAction, showBottomAction)

            }
        }

        setupViews()
    }

    /**
     * Sets up the child views and initializes the SelectLanguageView.
     */
    private fun setupViews() {
        setupConfigDefault()

        _binding.btnBottomAction.apply {
            if (showBottomAction) visible() else gone()
            if (backgroundBottomAction != null) {
                background = backgroundBottomAction
            }

            setTextAppearance(styleBottomAction)
            setTextColor(colorBottomAction)
        }

        languageAdapter.apply {
            _binding.rvLanguage.layoutManager = GridLayoutManager(context, column)
            _binding.rvLanguage.adapter = this

            submitList(getLanguageList())
            setBackgroundItem(backgroundItem, backgroundItemSelected)
            setStyleTextAppearance(styleTitle, styleTitleSelected)
            setStyleItem(style, styleSelected)
            setPositionSelected(positionSelected)
            setHeightItem(heightItem)
            setSizeFlag(sizeFlag)
        }
    }

    /**
     * Sets the listener to be notified when the language is changed.
     *
     * @param languageCode The callback function to be invoked when the language is changed.
     */
    fun onChangeLanguageListener(languageCode: (String) -> Unit) {
        itemLanguage = languageCode
    }

    /**
     * Sets the custom list of languages to be displayed.
     *
     * @param list The list of custom languages. It should be a list of [ItemLanguage] objects.
     */
    fun setListLanguageCustom(list: List<ItemLanguage>) {
        listLanguageCustom.clear()
        listLanguageCustom.addAll(list)
        languageAdapter.submitList(getLanguageList())
    }

    /**
     * Sets the background drawable for the SelectLanguageView.
     *
     * @param background The background drawable to be set.
     */
    override fun setBackground(background: Drawable?) {
        _binding.root.background = background
    }

    /**
     * Sets up the default configuration if custom values are not provided.
     */
    private fun setupConfigDefault() {
        setListLanguageCustom(listOf())
        if (background == null) {
            background = ContextCompat.getDrawable(context, R.color.white)
        }

        if (backgroundItem == null) {
            backgroundItem = ContextCompat.getDrawable(context, R.drawable.background_language)
        }

        if (backgroundItemSelected == null) {
            backgroundItemSelected =
                ContextCompat.getDrawable(context, R.drawable.background_language_selector)
        }

    }

    /**
     * Retrieves the list of languages based on the selected type.
     *
     * @return The list of languages to be displayed.
     */
    private fun getLanguageList(): List<ItemLanguage> {
        return when (typeListLanguage) {
            TypeLanguage.LIST_12_LANGUAGE -> listLanguages
            TypeLanguage.LIST_6_LANGUAGE -> listLanguages.subList(0, 6)
            TypeLanguage.LIST_CUSTOM -> listLanguageCustom
        }
    }

    //TODO Doc
    private fun getViewAds(): FrameLayout {
        return when (gravityAds) {
            GravityAds.BOTTOM -> _binding.flAdsBottom
            GravityAds.TOP -> _binding.flAdsTop
        }
    }

    fun setBottomActionClickListener(onBottomAction: () -> Unit) {
        _binding.btnBottomAction.setOnClickListener {
            onBottomAction.invoke()
        }
    }

    /**
     * Enum class defining the types of language lists.
     *
     * @param id The unique identifier for the language list type.
     */
    enum class TypeLanguage(val id: Int) {
        LIST_12_LANGUAGE(12),
        LIST_6_LANGUAGE(6),
        LIST_CUSTOM(3);

        companion object {
            /**
             * Returns the TypeLanguage enum based on the provided id.
             *
             * @param id The unique identifier for the language list type.
             * @return The corresponding TypeLanguage enum.
             */
            fun fromId(id: Int): TypeLanguage {
                return values().find { it.id == id } ?: LIST_12_LANGUAGE
            }
        }
    }

    enum class GravityAds(val id: Int) {
        TOP(2),
        BOTTOM(4);

        companion object {
            /**
             * Returns the TypeLanguage enum based on the provided id.
             *
             * @param id The unique identifier for the language list type.
             * @return The corresponding TypeLanguage enum.
             */
            fun fromId(id: Int): GravityAds {
                return values().find { it.id == id } ?: BOTTOM
            }
        }
    }
}