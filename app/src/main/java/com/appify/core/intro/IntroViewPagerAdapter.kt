package com.appify.core.intro

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class IntroViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    private val listFragment: MutableList<Fragment> = mutableListOf()

    fun submitList(listFragment: List<Fragment>) {
        this.listFragment.clear()
        this.listFragment.addAll(listFragment)
    }

    override fun getItemCount() = listFragment.size

    override fun createFragment(position: Int) = listFragment[position]


}