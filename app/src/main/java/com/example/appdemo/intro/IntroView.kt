package com.example.appdemo.intro

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class IntroView : FrameLayout {

    constructor(context: Context) : super(context){
        setupView()
    }

    private fun setupView() {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){
        initAttrs(attributeSet)
        setupView()
    }

    private fun initAttrs(attributeSet: AttributeSet) {
//        context.theme.obtainStyledAttributes()

    }
}