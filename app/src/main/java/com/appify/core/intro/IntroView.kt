package com.appify.core.intro

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.appify.core.DefaultValues
import com.appify.core.R
import com.appify.core.databinding.LayoutIntroBinding
import com.appify.core.gone
import com.appify.core.setMargins
import com.appify.core.visible
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

/**
 * IntroView is a custom view for displaying an introduction screen with multiple slides.
 *
 * This view provides a customizable introduction screen with multiple slides. It supports
 * customization options for appearance and behavior through XML attributes. The slides are
 * represented by layout resources that can be specified using the layoutId1, layoutId2, and
 * layoutId3 attributes. The "Next" button can be customized with background, icon, and text
 * color. The dots indicator can also be customized with colors and shape.
 *
 * Usage:
 * 1. Include the `IntroView` in your XML layout file.
 * 2. Customize the appearance and behavior of the view using the available XML attributes.
 * 3. Set the layout resources for the slides using layoutId1, layoutId2, and layoutId3 attributes.
 * 4. Register a listener for the "Next" button click event using [onFinishListener] if needed.
 *
 * - iv_layout_id_1: ID of the layout resource for slide 1.
 * - iv_layout_id_2: ID of the layout resource for slide 2.
 * - iv_layout_id_3: ID of the layout resource for slide 3.
 * - iv_gravityAction_corner: Position for displaying the "Next" button when it is in the bottom corner.
 * - iv_gravityAction_normal: Position for displaying the "Next" button in the normal position.
 * - iv_gravityAction_icon: Position for displaying the "Next" button with an icon.
 * - iv_gravityAction_center: Position for displaying the "Next" button in the center.
 * - iv_gravityAds: Gravity for displaying ads (BOTTOM or MIDDLE).
 * - iv_backgroundNext: Background drawable for the "Next" button.
 * - iv_nextTextColor: Color resource for the text of the "Next" button.
 * - iv_nextStyle: Style resource for the "Next" button.
 * - iv_nextIcon: Drawable resource for the icon of the "Next" button.
 * - iv_background: Background drawable for the IntroView.
 * - iv_dotsColor: Color resource for the dots indicator.
 * - iv_selectedDotsColor: Color resource for the selected dot in the dots indicator.
 * - iv_dotsShape: Shape option for the dots indicator (PILL or CIRCLE).
 * -
 * Example usage in XML:
 * ```
 * <com.example.IntroView
 *     android:id="@+id/introView"
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent"
 *     app:iv_layout_id_1="@layout/intro_slide_1"
 *     app:iv_layout_id_2="@layout/intro_slide_2"
 *     app:iv_layout_id_3="@layout/intro_slide_3"
 *     app:iv_gravityAction_corner="BOTTOM"
 *     app:iv_gravityAction_normal="TOP"
 *     app:iv_gravityAction_icon="BOTTOM"
 *     app:iv_gravityAction_center="TOP"
 *     app:iv_gravityAds="BOTTOM"
 *     app:iv_backgroundNext="@drawable/button_background"
 *     app:iv_nextTextColor="@color/colorPrimary"
 *     app:iv_nextStyle="@style/Intro_Button_Next"
 *     app:iv_nextIcon="@drawable/ic_next"
 *     app:iv_dotsColor="@color/dots_color"
 *     app:iv_selectedDotsColor="@color/blue"
 *     app:iv_dotsShape="CIRCLE" />
 * ```
 *
 * @param context The context in which the view is created.
 * @param attributeSet The attribute set containing custom attributes.
 */
class IntroView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var _binding = LayoutIntroBinding.inflate(LayoutInflater.from(context), this, true)
    private var introViewPagerAdapter: IntroViewPagerAdapter? = null

    @LayoutRes
    private var layoutId1 = View.NO_ID
    private var layoutId2 = View.NO_ID
    private var layoutId3 = View.NO_ID


    private var gravityActionNormal = 0
    private var gravityActionIcon = 0
    private var gravityActionCorner = 0
    private var gravityActionCenter = 0

    private var gravityAds = 0

    private var backgroundNext: Drawable? = null
    private var background: Drawable? = null

    private var colorTextNext = 0
    private var styleNext = 0

    @DrawableRes
    private var iconNext = 0

    @ColorInt
    private var dotsColor = 0
    private var selectedDotsColor = 0

    private var dotsShape = DotsShape.CIRCLE.id

    private var viewAds: FrameLayout? = null

    init {
        requireActivity()
        attributeSet?.let { attrs ->
            context.theme.obtainStyledAttributes(attrs, R.styleable.IntroView, 0, 0).apply {
                try {
                    layoutId1 = getResourceId(R.styleable.IntroView_iv_layout_id_1, View.NO_ID)
                    layoutId2 = getResourceId(R.styleable.IntroView_iv_layout_id_2, View.NO_ID)
                    layoutId3 = getResourceId(R.styleable.IntroView_iv_layout_id_3, View.NO_ID)

                    gravityActionCorner = getInt(R.styleable.IntroView_iv_gravityAction_corner, 0)
                    gravityActionNormal = getInt(R.styleable.IntroView_iv_gravityAction_normal, 0)
                    gravityActionIcon = getInt(R.styleable.IntroView_iv_gravityAction_icon, 0)
                    gravityActionCenter = getInt(R.styleable.IntroView_iv_gravityAction_center, 0)

                    gravityAds = getInt(R.styleable.IntroView_iv_gravityAds, 0)

                    backgroundNext = getDrawable(R.styleable.IntroView_iv_backgroundNext)
                    colorTextNext = getInt(R.styleable.IntroView_iv_nextTextColor, colorTextNext)
                    styleNext = getResourceId(R.styleable.IntroView_iv_nextStyle, styleNext)
                    iconNext = getResourceId(R.styleable.IntroView_iv_nextIcon, iconNext)

                    background = getDrawable(R.styleable.IntroView_iv_background)

                    dotsColor = getColor(R.styleable.IntroView_iv_dotsColor, dotsColor)
                    selectedDotsColor = getColor(R.styleable.IntroView_iv_selectedDotsColor, selectedDotsColor)

                    dotsShape = getInt(R.styleable.IntroView_iv_dotsShape, dotsShape)


                } finally {
                    recycle()
                }
            }
        }

        setupView()
    }

    /**
     * Returns the [FragmentActivity] associated with the current context.
     *
     * @return The [FragmentActivity] instance.
     */
    private fun requireActivity(): FragmentActivity {
        return context as FragmentActivity
    }

    /**
     * Sets up the view by initializing default configurations, gravity actions, icon next, style next,
     * background next, dots color, dots shape, and color of the "Next" text.
     */
    private fun setupView() {
        setupConfigDefault()
        setupViewPager()
        setupGravityAction()
        setupIconNext()
        setupStyleNext()
        setupBackgroundNext()
        setupDotsColor()
        setupDotsShape()
        setupColorTextNext()
        setupBackground()


    }

    /**
     * Sets up the background of the view.
     */
    private fun setupBackground() {
        if (background != null) {
            this.setBackground(background)
        }
    }

    /**
     * Sets up the ViewPager and its associated components.
     */
    private fun setupViewPager() {
        if (introViewPagerAdapter == null) {
            introViewPagerAdapter = IntroViewPagerAdapter(requireActivity())
        }

        _binding.vgIntro.apply {
            adapter = introViewPagerAdapter
            _binding.indicatorBottom.attachTo(this)
            _binding.indicatorTop.attachTo(this)
            _binding.indicatorCenter.attachTo(this)
        }

        introViewPagerAdapter?.submitList(getListFragment())
    }

    /**
     * Sets up the style for the "Next" text based on the provided style resource.
     */
    private fun setupStyleNext() {
        if (styleNext != 0) {
            _binding.txtNextBottom.setTextAppearance(styleNext)
            _binding.txtNextTop.setTextAppearance(styleNext)
            _binding.btnNextCenter.setTextAppearance(styleNext)
        }
    }

    /**
     * Sets up the background for the "Next" text based on the provided drawable resource.
     */
    private fun setupBackgroundNext() {
        if (backgroundNext != null) {
            _binding.txtNextTop.background = backgroundNext
            _binding.txtNextBottom.background = backgroundNext
            _binding.btnNextCenter.background = backgroundNext
        }
    }

    /**
     * Sets up the color of the "Next" text based on the provided color value.
     */
    private fun setupColorTextNext() {
        _binding.txtNextBottom.setTextColor(colorTextNext)
        _binding.txtNextTop.setTextColor(colorTextNext)
    }

    /**
     * Sets up the default configuration values if they are not provided.
     */
    private fun setupConfigDefault() {
        if (dotsColor == 0) {
            dotsColor = ContextCompat.getColor(context, R.color.dots_color)
        }

        if (selectedDotsColor == 0) {
            selectedDotsColor = ContextCompat.getColor(context, R.color.blue)
        }

        if (iconNext == 0) {
            iconNext = R.drawable.ic_next
        }

        if (colorTextNext == 0) {
            colorTextNext = ContextCompat.getColor(context, R.color.black)
        }

        if (styleNext == 0) {
            styleNext = R.style.Intro_Button_Next
        }
    }

    /**
     * Sets up the shape of the dots indicator based on the provided dotsShape value.
     */
    private fun setupDotsShape() {
        when (dotsShape) {
            DotsShape.PILL.id -> {
                _binding.indicatorBottom.setWithFactor(DefaultValues.PILL)
                _binding.indicatorTop.setWithFactor(DefaultValues.PILL)
                _binding.indicatorCenter.setWithFactor(DefaultValues.PILL)
            }

            DotsShape.CIRCLE.id -> {
                _binding.indicatorBottom.setWithFactor(DefaultValues.CIRCLE)
                _binding.indicatorTop.setWithFactor(DefaultValues.CIRCLE)
                _binding.indicatorCenter.setWithFactor(DefaultValues.CIRCLE)
            }
        }
    }

    /**
     * Sets up the color of the dots indicator based on the provided dotsColor and selectedDotsColor values.
     */
    private fun setupDotsColor() {
        _binding.indicatorCenter.dotsColor = dotsColor
        _binding.indicatorBottom.dotsColor = dotsColor
        _binding.indicatorTop.dotsColor = dotsColor

        _binding.indicatorCenter.selectedDotColor = selectedDotsColor
        _binding.indicatorBottom.selectedDotColor = selectedDotsColor
        _binding.indicatorTop.selectedDotColor = selectedDotsColor
    }

    /**
     * Sets up the icon for the "Next" button based on the provided iconNext drawable resource.
     */
    private fun setupIconNext() {
        _binding.imgNextBottom.setImageResource(iconNext)
        _binding.imgNextTop.setImageResource(iconNext)
    }

    /**
     * Creates a list of fragments based on the provided layoutId1, layoutId2, and layoutId3 values.
     *
     * @return The list of fragments.
     */
    private fun getListFragment() = mutableListOf<Fragment>().apply {
        add(FragmentChild.newInstance(layoutId1))
        add(FragmentChild.newInstance(layoutId2))
        add(FragmentChild.newInstance(layoutId3))
    }

    /**
     * Sets up the gravity actions for the view based on the provided gravityAds, gravityActionNormal,
     * gravityActionCenter, gravityActionCorner, and gravityActionIcon values.
     */
    private fun setupGravityAction() {
        setupGravityAds()
        setupGravityActionNormal()
        setupGravityActionCenter()
        setupGravityActionCorner()
        setupGravityActionIcon()
    }

    /**
     * Returns the view for displaying ads based on the gravityAds value.
     *
     * @return The FrameLayout containing the ads view.
     */
    private fun getViewAds(): FrameLayout {
        if (gravityAds != 0) {
            when (gravityAds) {
                GravityAds.BOTTOM.id -> {
                    _binding.viewAdsBottom.visible()
                    _binding.viewAdsMiddle.gone()
                    _binding.viewAdsMiddleCenter.gone()
                    return _binding.viewAdsBottom
                }

                GravityAds.MIDDLE.id -> {
                    _binding.viewAdsBottom.gone()
                    return if (gravityActionCenter != 0) {
                        _binding.viewAdsMiddleCenter.visible()
                        _binding.viewAdsMiddle.gone()
                        _binding.viewAdsMiddleCenter
                    } else {
                        _binding.viewAdsMiddle.visible()
                        _binding.viewAdsMiddleCenter.gone()
                        _binding.viewAdsMiddle
                    }
                }
            }
        }
        return _binding.viewAdsBottom
    }

    /**
     * Sets up the gravity action for the "Next" button when gravityActionNormal is selected.
     */
    private fun setupGravityActionNormal() {
        if (gravityActionNormal != 0) {
            when (gravityActionNormal) {
                GravityAction.BOTTOM.id -> {
                    _binding.viewActionBottom.visible()
                    _binding.viewActionTop.gone()

                    _binding.txtNextBottom.apply {
                        visible()
                        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
                    }
                }

                GravityAction.TOP.id -> {
                    _binding.viewActionTop.visible()
                    _binding.viewActionBottom.gone()

                    _binding.txtNextTop.apply {
                        visible()
                        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
                    }
                }
            }

            _binding.btnNextBottom.setMargins(rightMarginDp = 8)
            _binding.btnNextTop.setMargins(rightMarginDp = 8)

            _binding.viewActionCenter.gone()
            _binding.imgNextBottom.gone()
            _binding.imgNextTop.gone()
        }
    }

    /**
     * Sets up the gravity action for displaying ads when gravityAds is selected.
     */
    private fun setupGravityAds() {
        if (gravityAds != 0) {
            when (gravityAds) {
                GravityAds.BOTTOM.id -> {
                    viewAds = _binding.viewAdsBottom
                    _binding.viewAdsBottom.visible()
                    _binding.viewAdsMiddle.gone()
                    _binding.viewAdsMiddleCenter.gone()
                }

                GravityAds.MIDDLE.id -> {
                    _binding.viewAdsMiddle.apply {
                        viewAds = this
                        visible()
                    }
                }
            }
        }
    }

    /**
     * Sets up the gravity action for the "Next" button when gravityActionCenter is selected.
     */
    private fun setupGravityActionCenter() {
        if (gravityActionCenter != 0) {
            _binding.btnNextCenter.setTextSizeSmall()

            _binding.viewActionBottom.gone()
            _binding.viewActionCenter.visible()
            _binding.viewActionTop.gone()
        }
    }

    /**
     * Sets up the gravity action for the "Next" button when gravityActionCorner is selected.
     */
    private fun setupGravityActionCorner() {
        if (gravityActionCorner != 0) {
            _binding.txtNextBottom.setTextSizeSmall()
            _binding.txtNextTop.setTextSizeSmall()

            when (gravityActionCorner) {
                GravityAction.BOTTOM.id -> {
                    _binding.txtNextBottom.visible()
                    _binding.imgNextBottom.gone()
                    _binding.viewActionBottom.visible()
                    _binding.viewActionCenter.gone()
                    _binding.viewActionTop.gone()
                }

                GravityAction.TOP.id -> {
                    _binding.txtNextTop.visible()
                    _binding.imgNextTop.gone()
                    _binding.viewActionBottom.gone()
                    _binding.viewActionCenter.gone()
                    _binding.viewActionTop.visible()
                }
            }
        }
    }

    /**
     * Sets up the gravity action for the "Next" button when gravityActionIcon is selected.
     */
    private fun setupGravityActionIcon() {
        if (gravityActionIcon != 0) {
            when (gravityActionIcon) {
                GravityAction.BOTTOM.id -> {
                    _binding.imgNextBottom.visible()
                    _binding.viewActionBottom.visible()
                    _binding.txtNextBottom.gone()

                    _binding.viewActionCenter.gone()
                    _binding.viewActionTop.gone()
                }

                GravityAction.TOP.id -> {
                    _binding.imgNextTop.visible()
                    _binding.viewActionTop.visible()
                    _binding.txtNextTop.gone()

                    _binding.viewActionCenter.gone()
                    _binding.viewActionBottom.gone()
                }
            }
        }
    }

    /**
     * Sets the click listener for the "Next" button to perform the onNext action.
     *
     * @param onNext The action to be performed when the "Next" button is clicked.
     */
    fun onFinishListener(onNext: () -> Unit) {
        _binding.btnNextBottom.setOnClickListener {
            if (handlerNext()) onNext.invoke()
        }

        _binding.btnNextCenter.setOnClickListener {
            if (handlerNext()) onNext.invoke()
        }

        _binding.btnNextTop.setOnClickListener {
            if (handlerNext()) onNext.invoke()
        }
    }

    /**
     * Handles the "Next" button click event by incrementing the current item of the view pager.
     *
     * @return True if the current item is the last item, false otherwise.
     */
    private fun handlerNext(): Boolean {
        val currentItem = _binding.vgIntro.currentItem + 1
        _binding.vgIntro.currentItem = currentItem
        return currentItem == DefaultValues.TOTAL_INTRO
    }
}

/**
 * Sets the width factor of the dots indicator using reflection.
 *
 * @param withFactory The width factor to be set.
 */
private fun DotsIndicator.setWithFactor(withFactory: Float) {
    try {
        val field = DotsIndicator::class.java.getDeclaredField("dotsWidthFactor")
        field.isAccessible = true
        field.set(this, withFactory)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Sets the text size of the text view to a smaller value.
 */
private fun TextView.setTextSizeSmall() {
    setTextSize(
        TypedValue.COMPLEX_UNIT_PX,
        resources.getDimension(R.dimen.text_size_corner_button_next)
    )
}

/**
 * An enum class representing gravity actions for the "Next" button.
 *
 * @property id The identifier for the gravity action.
 */
enum class GravityAction(val id: Int) {
    BOTTOM(3), TOP(1)
}

/**
 * An enum class representing gravity options for displaying ads.
 *
 * @property id The identifier for the gravity option.
 */
enum class GravityAds(val id: Int) {
    BOTTOM(6), MIDDLE(2)
}

/**
 * An enum class representing shape options for the dots indicator.
 *
 * @property id The identifier for the shape option.
 */
enum class DotsShape(val id: Int) {
    PILL(4), CIRCLE(8)
}
