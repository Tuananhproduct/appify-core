package com.appify.core.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.appify.core.databinding.FragmentChildBinding

class FragmentChild : Fragment() {

    private lateinit var binding: FragmentChildBinding
    private val layoutIdRes by lazy { arguments?.getInt(LAYOUT_ID) ?: View.NO_ID }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChildBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        addLayoutId(layoutIdRes)
    }

    private fun addLayoutId(@LayoutRes layoutRes: Int) {
        val view = inflaterView(layoutRes)
        binding.flContainer.addView(view)
    }

    private fun inflaterView(@LayoutRes layoutRes: Int): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(layoutRes, binding.flContainer, false)

    }

    companion object {
        private const val LAYOUT_ID = "LAYOUT_ID"

        fun newInstance(@LayoutRes layoutId: Int) = FragmentChild().apply {
            arguments = Bundle().apply {
                putInt(LAYOUT_ID, layoutId)
            }
        }
    }


}