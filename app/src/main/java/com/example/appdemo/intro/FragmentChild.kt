package com.example.appdemo.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.appdemo.ItemIntro
import com.example.appdemo.databinding.FragmentChildBinding

class FragmentChild : Fragment() {

    private val dataItem by lazy { arguments?.getParcelable(KEY_DATA) ?: ItemIntro() }
    private lateinit var binding: FragmentChildBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChildBinding.inflate(LayoutInflater.from(container?.context), container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        Glide.with(requireContext()).load(dataItem.imgIntro).into(binding.imgIntro)
        binding.txtIntro.text = dataItem.txtIntro
    }

    companion object {
        private const val KEY_DATA = "KEY_DATA"

        fun newInstance(item: ItemIntro) = FragmentChild().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_DATA, item)
            }
        }
    }


}