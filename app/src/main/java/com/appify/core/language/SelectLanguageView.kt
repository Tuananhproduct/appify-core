package com.appify.core.language

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.appify.core.R
import com.appify.core.SharedPref
import com.appify.core.databinding.SelectLanguageBinding
import com.appify.core.gone
import com.appify.core.visible

/**
 * SelectLanguageView is a custom view for selecting a language.
 *
 * This view displays a list of languages and allows the user to select a language. It provides
 * customization options for appearance and behavior through XML attributes. The selected language
 * display the list of available languages in the form of 12 languages, 6 languages and a custom language list
 * can be obtained through the [onChangeLanguageListener] callback. The view also supports displaying
 * ads at the top or bottom, depending on the gravityAds setting.
 *
 * Usage:
 * 1. Include the `SelectLanguageView` in your XML layout file.
 * 2. Customize the appearance and behavior of the view using the available XML attributes.
 * 3. Register a listener for the language change event using [onChangeLanguageListener].
 * 4. Set a custom list of languages using [setListLanguageCustom] if needed.
 * 5. Set a click listener for the bottom action button using [setBottomActionClickListener] if needed.
 *
 * - x_backgroundItem: Background drawable for each language item.
 * - x_backgroundItem_selected: Background drawable for the selected language item.
 * - x_background: Background drawable for the SelectLanguageView.
 * - x_titleItemStyle: Style resource for the language item title when not selected.
 * - x_titleItemStyle_selected: Style resource for the language item title when selected.
 * - x_listLanguage: Type of language list to display (12 languages, 6 languages, or custom list).
 * - x_positionSelected: Position of the initially selected language item.
 * - x_gravityAds: Gravity for displaying ads (TOP or BOTTOM).
 * - x_heightItem: Height of each language item.
 * - x_sizeFlag: Size of the language flag icon.
 * - x_column: Number of columns for the language list display.
 * - x_backgroundBottomAction: Background drawable for the bottom action button.
 * - x_colorTitleBottomAction: Color resource for the text of the bottom action button.
 * - x_bottomActionStyle: Style resource for the bottom action button.
 * - x_canShowBottomAction: Boolean indicating whether the bottom action button should be shown.
 *
 * Example usage in XML:
 * ```
 * <com.example.SelectLanguageView
 *     android:id="@+id/selectLanguageView"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:x_backgroundItem="@drawable/background_language"
 *     app:x_backgroundItem_selected="@drawable/background_language_selector"
 *     app:x_background="@color/white"
 *     app:x_titleItemStyle="@style/TextTitle_Normal"
 *     app:x_titleItemStyle_selected="@style/TextTitle_Selected"
 *     app:x_itemStyle="@style/ItemStyle"
 *     app:x_itemStyle_selected="@style/ItemStyle_Selected"
 *     app:x_listLanguage="LIST_12_LANGUAGE"
 *     app:x_gravityAds="BOTTOM"
 *     app:x_heightItem="48dp"
 *     app:x_sizeFlag="24dp"
 *     app:x_column="2"
 *     app:x_positionSelected="0"
 *     app:x_backgroundBottomAction="@drawable/button_background"
 *     app:x_colorTitleBottomAction="@color/colorPrimary"
 *     app:x_bottomActionStyle="@style/XToolbar_Bottom"
 *     app:x_canShowBottomAction="true" />
 * ```
 *
 * @param context The context in which the view is created.
 * @param attributeSet The attribute set containing custom attributes.
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

    private var currentLanguageCode: String? = null

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
                backgroundItem = getDrawable(R.styleable.SelectLanguageView_sl_backgroundItem)
                backgroundItemSelected =
                    getDrawable(R.styleable.SelectLanguageView_sl_backgroundItem_selected)

                background = getDrawable(R.styleable.SelectLanguageView_sl_background)

                styleTitle = getResourceId(R.styleable.SelectLanguageView_sl_titleItemStyle, styleTitle)
                styleTitleSelected = getResourceId(R.styleable.SelectLanguageView_sl_titleItemStyle_selected, styleTitleSelected)

                style = getResourceId(R.styleable.SelectLanguageView_sl_itemStyle, style)
                styleSelected = getResourceId(R.styleable.SelectLanguageView_sl_itemStyle_selected, styleSelected)

                typeListLanguage = TypeLanguage.fromId(getInt(R.styleable.SelectLanguageView_sl_listLanguage, typeListLanguage.id))

                gravityAds = GravityAds.fromId(getInt(R.styleable.SelectLanguageView_sl_gravityAds, gravityAds.id))

                heightItem = getDimensionPixelSize(R.styleable.SelectLanguageView_sl_heightItem, heightItem)
                sizeFlag = getDimensionPixelSize(R.styleable.SelectLanguageView_sl_sizeFlag, sizeFlag)

                column = getInt(R.styleable.SelectLanguageView_sl_column, column)

                positionSelected = getInt(R.styleable.SelectLanguageView_sl_positionSelected, positionSelected)

                backgroundBottomAction = getDrawable(R.styleable.SelectLanguageView_sl_backgroundBottomAction)
                colorBottomAction = getColor(R.styleable.SelectLanguageView_sl_colorTitleBottomAction, colorBottomAction)
                styleBottomAction = getResourceId(R.styleable.SelectLanguageView_sl_bottomActionStyle, styleBottomAction)
                showBottomAction = getBoolean(R.styleable.SelectLanguageView_sl_canShowBottomAction, showBottomAction)

            }
        }

        setupViews()
    }

    /**
     * Sets up the child views and initializes the SelectLanguageView.
     */
    private fun setupViews() {
        setupConfigDefault()
        setupLanguageAdapter()
        setupBottomAction()
    }

    /**
     * Sets up the language adapter for the RecyclerView.
     */
    private fun setupLanguageAdapter() {
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
     * Sets up the bottom action button.
     */
    private fun setupBottomAction() {
        _binding.btnBottomAction.apply {
            if (showBottomAction) visible() else gone()
            if (backgroundBottomAction != null) {
                background = backgroundBottomAction
            }

            setTextAppearance(styleBottomAction)
            setTextColor(colorBottomAction)
        }

    }

    /**
     * Sets the selected position based on the provided language code.
     *
     * @param code The language code of the item to be selected.
     */
    fun setPositionSelected(code: String) {
        val positionSelected = listLanguages.getPosition(code)
        languageAdapter.setPositionSelected(positionSelected)
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

        onChangeLanguageListener {
            currentLanguageCode = it
        }

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

    /**
     * Returns the FrameLayout container for displaying ads based on the gravity setting.
     *
     * @return The FrameLayout container for ads.
     */
    fun getViewAds(): FrameLayout {
        return when (gravityAds) {
            GravityAds.BOTTOM -> _binding.flAdsBottom
            GravityAds.TOP -> _binding.flAdsTop
        }
    }


    /**
     * Sets a click listener for the bottom action button.
     *
     * @param onBottomAction The callback function to be invoked when the bottom action button is clicked.
     */
    fun setBottomActionClickListener(onBottomAction: () -> Unit) {
        setPositionSelected(SharedPref.getLanguageCode())
        _binding.btnBottomAction.setOnClickListener {
            SharedPref.saveLanguageCode(currentLanguageCode ?: SharedPref.getLanguageCode())
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