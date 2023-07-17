package com.appify.core.toolbar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.cardview.widget.CardView
import com.appify.core.DefaultValues
import com.appify.core.R
import com.appify.core.SharedPref
import com.appify.core.databinding.LayoutXToolbarBinding
import com.appify.core.gone
import com.appify.core.invisible
import com.appify.core.language.SelectLanguageView
import com.appify.core.setMargins
import com.appify.core.visible

/**
 * XToolbar is a custom toolbar implementation.
 *
 * This toolbar provides various customization options for appearance and behavior through XML attributes.
 * It supports setting the title, icons, and visibility of the left and right buttons. Additionally,
 * it allows customization of the background color, elevation, and visibility of the title text.
 *
 * Usage:
 * 1. Include the `XToolbar` in your XML layout file.
 * 2. Customize the appearance and behavior of the toolbar using the available XML attributes.
 * 3. Register click listeners for the left and right buttons using [setToolbarClickListener].
 *
 * - xtb_title: Title text for the toolbar.
 * - xtb_colorTitle: Color resource for the title text.
 * - xtb_visibilityTitle: Visibility of the title text (visible, invisible, or gone).
 * - xtb_visibilityLeft: Visibility of the left button (visible, invisible, or gone).
 * - xtb_iconLeft: Drawable resource for the icon of the left button.
 * - xtb_visibilityRight: Visibility of the right button (visible, invisible, or gone).
 * - xtb_iconRight: Drawable resource for the icon of the right button.
 * - xtb_textRight: Text for the right button.
 * - xtb_colorTextRight: Color resource for the text of the right button.
 * - xtb_backgroundRight: Background drawable for the right button.
 * - xtb_cardElevation: Elevation of the toolbar card.
 * - xtb_background: Background color or drawable for the toolbar.
 * - xtb_canShowLine: Boolean indicating whether a line should be shown below the toolbar.
 * - xtb_titleStyle: Style resource for the title text.
 *
 * Example usage in XML:
 * ```
 * <com.example.XToolbar
 *     android:id="@+id/toolbar"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:xtb_title="My Toolbar"
 *     app:xtb_colorTitle="@color/toolbar_title_color"
 *     app:xtb_visibilityTitle="visible"
 *     app:xtb_visibilityLeft="visible"
 *     app:xtb_iconLeft="@drawable/ic_back"
 *     app:xtb_visibilityRight="visible"
 *     app:xtb_iconRight="@drawable/ic_menu"
 *     app:xtb_textRight="Options"
 *     app:xtb_colorTextRight="@color/toolbar_text_color"
 *     app:xtb_backgroundRight="@drawable/button_background"
 *     app:xtb_cardElevation="4dp"
 *     app:xtb_background="@color/toolbar_background"
 *     app:xtb_canShowLine="true"
 *     app:xtb_titleStyle="@style/CustomToolbarTitle" />
 * ```
 *
 * @param context The context in which the toolbar is created.
 * @param attributeSet The attribute set containing custom attributes.
 */
class XToolbar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : CardView(context, attributeSet) {

    private var title: String? = null
    private var colorTitle = Color.BLACK
    private var visibilityTitle = View.VISIBLE

    private var visibilityRight = View.VISIBLE
    private var visibilityLeft = View.VISIBLE

    private var iconRight: Drawable? = null
    private var iconLeft: Drawable? = null

    private var cardElevation = 0f

    private var colorBackground = Color.WHITE

    private var textRight: String? = null
    private var colorTextRight = Color.WHITE
    private var backgroundRight: Drawable? = null
    private var styleTextRight = R.style.XToolbar_Right

    private var showLineBottom = false

    private var styleTitle = R.style.XToolbar

    private var gravityTitle = Gravity.START

    private var currentLanguageCode: String? = null

    private var _binding = LayoutXToolbarBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        //        _binding.root.configMarginStatusBar()
        attributeSet?.let { attrs ->
            context.theme.obtainStyledAttributes(attrs, R.styleable.XToolbar, 0, 0).apply {
                try {
                    title = getString(R.styleable.XToolbar_xtb_title)
                    colorTitle = getColor(R.styleable.XToolbar_xtb_colorTitle, colorTitle)
                    visibilityTitle = getInt(R.styleable.XToolbar_xtb_visibilityTitle, visibilityTitle)
                    gravityTitle = getInt(R.styleable.XToolbar_xtb_gravityTitle, gravityTitle)
                    styleTitle = getResourceId(R.styleable.XToolbar_xtb_titleStyle, styleTitle)

                    visibilityRight = getInt(R.styleable.XToolbar_xtb_visibilityRight, visibilityRight)
                    visibilityLeft = getInt(R.styleable.XToolbar_xtb_visibilityLeft, visibilityLeft)

                    iconRight = getDrawable(R.styleable.XToolbar_xtb_iconRight)
                    iconLeft = getDrawable(R.styleable.XToolbar_xtb_iconLeft)

                    colorBackground = getColor(R.styleable.XToolbar_xtb_background, colorBackground)

                    textRight = getString(R.styleable.XToolbar_xtb_textRight)
                    backgroundRight = getDrawable(R.styleable.XToolbar_xtb_backgroundRight)
                    colorTextRight = getColor(R.styleable.XToolbar_xtb_colorTextRight, colorTextRight)
                    styleTextRight = getResourceId(R.styleable.XToolbar_xtb_textRightStyle, styleTextRight)

                    cardElevation = getDimension(R.styleable.XToolbar_xtb_cardElevation, cardElevation)
                    showLineBottom = getBoolean(R.styleable.XToolbar_xtb_canShowLine, showLineBottom)


                } finally {
                    recycle()
                }
            }

        }

        setupView()

    }

    /**
     * Sets up the view with the provided configurations.
     */
    private fun setupView() {
        setupStatesLanguage()
        setCardElevation(cardElevation)
        setShowLineBottom(showLineBottom)
        setupTitle()
        setupIconLeft()
        setupIconRight()
        setupBackground()
    }

    /**
     * Sets up the background color of the view.
     */
    private fun setupBackground() {
        _binding.root.setCardBackgroundColor(colorBackground)
    }

    /**
     * Sets up the right button icon and visibility.
     */
    private fun setupIconRight() {
        _binding.iconRight.apply {
            setImageDrawable(iconRight)
            setRightButtonVisible(visibilityRight)
        }
    }

    /**
     * Sets up the left button icon and visibility.
     */
    private fun setupIconLeft() {
        _binding.imgLeft.apply {
            setImageDrawable(iconLeft)
            setLeftButtonVisible(visibilityLeft)
        }
    }

    /**
     * Sets up the title text and its properties.
     */
    private fun setupTitle() {
        if (textRight != null) {
            _binding.txtRight.visible()
            _binding.iconRight.gone()
            _binding.buttonRight.setMargins(rightMarginDp = 16)
            _binding.buttonRight.background = backgroundRight
            _binding.txtRight.text = textRight
            _binding.txtRight.setTextAppearance(styleTextRight)
            _binding.txtRight.setTextColor(colorTextRight)
        }

        _binding.title.apply {
            setTitleText(title ?: DefaultValues.EMPTY_STRING)
            setTitleColor(colorTitle)
            setTitleTextVisible(visibilityTitle)
            setTitleTextAppearance(styleTitle)
            setGravityTitle(gravityTitle)
        }
    }

    /**
     * Sets up the language states by saving the current language code to shared preferences.
     */
    private fun setupStatesLanguage() {
        SharedPref.saveLanguageCode(SharedPref.getLanguageCode())
    }

    /**
     * Sets up the view with the provided language configurations.
     * @param viewLanguage The SelectLanguageView to be set up with language configurations.
     */
    fun setupWithLanguageView(viewLanguage: SelectLanguageView) {
        viewLanguage.onChangeLanguageListener { languageCode ->
            currentLanguageCode = languageCode
        }

        viewLanguage.setPositionSelected(SharedPref.getLanguageCode())
    }

    /**
     * Sets the gravity for the title in the view.
     * @param gravity The gravity to be set for the title.
     */
    private fun setGravityTitle(gravity: Int) {
        _binding.title.gravity = gravity
    }

    /**
     * Sets the visibility of the bottom line in the view.
     * @param canShow Whether to show the bottom line or not.
     */
    fun setShowLineBottom(canShow: Boolean) {
        if (canShow) {
            _binding.vLineBottom.visible()
        } else {
            _binding.vLineBottom.invisible()
        }
    }

    /**
     * Sets click listeners for the toolbar buttons.
     * @param clickLeft The function to be invoked when the left button is clicked.
     * @param clickRight The function to be invoked when the right button is clicked.
     */
    fun setToolbarClickListener(clickLeft: (View) -> Unit = {}, clickRight: (View) -> Unit = {}) {
        _binding.apply {
            buttonLeft.setOnClickListener {
                clickLeft(it)
            }

            buttonRight.setOnClickListener {
                clickRight(it)
                SharedPref.saveLanguageCode(currentLanguageCode ?: SharedPref.getLanguageCode())
            }
        }
    }

    /**
     * Sets the resource for the left button icon in the view.
     * @param resId The drawable resource ID to be set for the left button.
     */
    fun setLeftButtonResource(@DrawableRes resId: Int) {
        _binding.imgLeft.setImageResource(resId)
    }

    /**
     * Sets the visibility of the left button in the view.
     * @param visible The visibility value to be set for the left button.
     */
    fun setLeftButtonVisible(visible: Int) {
        _binding.buttonLeft.visibility = visible
    }

    /**
     * Sets the resource for the right button icon in the view.
     * @param resId The drawable resource ID to be set for the right button.
     */
    fun setRightButtonResource(@DrawableRes resId: Int) {
        _binding.iconRight.setImageResource(resId)
    }

    /**
     * Sets the visibility of the right button in the view.
     * @param visible The visibility value to be set for the right button.
     */
    fun setRightButtonVisible(visible: Int) {
        _binding.buttonRight.visibility = visible
    }

    /**
     * Sets the visibility of the title text in the view.
     * @param visible The visibility value to be set for the title text.
     */
    fun setTitleTextVisible(visible: Int) {
        _binding.title.visibility = visible
    }

    /**
     * Sets the resource for the title text in the view.
     * @param titleResId The string resource ID to be set as the title text.
     */
    fun setTitleTextResource(@StringRes titleResId: Int) {
        _binding.title.text = context.getString(titleResId)
    }

    /**
     * Sets the text for the title in the view.
     * @param title The title text to be set.
     */
    fun setTitleText(title: String) {
        _binding.title.text = title
    }

    /**
     * Sets the color for the title text in the view.
     * @param titleColorId The color value to be set for the title text.
     */
    fun setTitleColor(@ColorInt titleColorId: Int) {
        _binding.title.setTextColor(titleColorId)
    }

    /**
     * Sets the text appearance for the title text in the view.
     * @param styleRes The style resource ID to be set for the title text.
     */
    fun setTitleTextAppearance(@StyleRes styleRes: Int) {
        _binding.title.setTextAppearance(styleRes)
    }
}
