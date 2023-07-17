package com.example.appdemo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.cardview.widget.CardView
import com.example.appdemo.databinding.LayoutXToolbarBinding


class XToolbar : CardView {

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
    private var colorTextRight  = Color.WHITE
    private var backgroundRight :Drawable? = null
    private var styleTextRight = R.style.XToolbar_Right

    private var showLineBottom = false

    private var styleTitle = R.style.XToolbar

    private var gravityTitle = Gravity.START

    private var _binding = LayoutXToolbarBinding.inflate(LayoutInflater.from(context), this, true)


    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initAttrs(context, attributeSet)
        setupView()
    }

    private fun initAttrs(context: Context, attributeSet: AttributeSet?) {
//        _binding.root.configMarginStatusBar()
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.XToolbar, 0, 0).apply {
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

    private fun setupView() {
        setCardElevation(cardElevation)
        setShowLineBottom(showLineBottom)

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
            setTitleText(title ?: EMPTY_STRING)
            setTitleColor(colorTitle)
            setTitleTextVisible(visibilityTitle)
            setTitleTextAppearance(styleTitle)
            setGravityTitle(gravityTitle)
        }

        _binding.imgLeft.apply {
            setImageDrawable(iconLeft)
            setLeftButtonVisible(visibilityLeft)
        }

        _binding.iconRight.apply {
            setImageDrawable(iconRight)
            setRightButtonVisible(visibilityRight)
        }

        _binding.root.setCardBackgroundColor(colorBackground)

    }

    override fun setCardElevation(elevation: Float) {
        super.setCardElevation(elevation)


    }

    private fun setGravityTitle(gravity: Int) {
        _binding.title.gravity = gravity

    }

    fun setShowLineBottom(canShow: Boolean) {
        if (canShow) _binding.vLineBottom.visible() else _binding.vLineBottom.invisible()
    }


    fun setToolbarClickListener(clickLeft: (View) -> Unit = {}, clickRight: (View) -> Unit = {}) {
        _binding.apply {
            buttonLeft.setOnClickListener {
                clickLeft(it)
            }

            buttonRight.setOnClickListener {
                clickRight(it)
            }
        }
    }

    fun setLeftButtonResource(@DrawableRes resId: Int) {
        _binding.imgLeft.setImageResource(resId)
    }


    fun setLeftButtonVisible(visible: Int) {
        _binding.buttonLeft.visibility = visible
    }


    fun setRightButtonResource(@DrawableRes resId: Int) {
        _binding.iconRight.setImageResource(resId)
    }

    fun setRightButtonVisible(visible: Int) {
        _binding.buttonRight.visibility = visible
    }

    fun setTitleTextVisible(visible: Int) {
        _binding.title.visibility = visible
    }

    fun setTitleTextResource(@StringRes titleResId: Int) {
        _binding.title.text = context.getString(titleResId)
    }

    fun setTitleText(title: String) {
        _binding.title.text = title
    }


    fun setTitleColor(@ColorInt titleColorId: Int) {
        _binding.title.setTextColor(titleColorId)
    }

    fun setTitleGravity(gravity: Int) {
        _binding.title.gravity = gravity
    }

    fun setTitleTextAppearance(@StyleRes styleRes: Int) {
        _binding.title.setTextAppearance(styleRes)
    }


    companion object {
        const val EMPTY_STRING = "Unknown"
    }


}

fun View.setMargins(
    leftMarginDp: Int? = null,
    topMarginDp: Int? = null,
    rightMarginDp: Int? = null,
    bottomMarginDp: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        leftMarginDp?.run { params.leftMargin = this.dpToPx(context) }
        topMarginDp?.run { params.topMargin = this.dpToPx(context) }
        rightMarginDp?.run { params.rightMargin = this.dpToPx(context) }
        bottomMarginDp?.run { params.bottomMargin = this.dpToPx(context) }
        requestLayout()
    }
}

fun Int.dpToPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}

