package com.example.appdemo

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appdemo.databinding.ItemLanguageBinding


class TaskDiffCallback : DiffUtil.ItemCallback<ItemLanguage>() {
    override fun areItemsTheSame(oldItem: ItemLanguage, newItem: ItemLanguage): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: ItemLanguage, newItem: ItemLanguage): Boolean {
        return oldItem == newItem
    }

}


class SelectorLanguageAdapter(private val itemClickListener: (String) -> Unit) :
    ListAdapter<ItemLanguage, SelectorLanguageAdapter.SelectorViewHolder>(TaskDiffCallback()) {

    private var currentPositionSelected = 0

    private var background: Drawable? = null
    private var backgroundSelected: Drawable? = null

    @DimenRes
    private var heightItem: Int = 0

    @DimenRes
    private var sizeFlag: Int = 0

    @StyleRes
    private var style = R.style.ItemLanguage_Normal

    @StyleRes
    private var styleSelected = R.style.ItemLanguage_Selected


    class SelectorViewHolder(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ItemLanguage, @DimenRes sizeFlag: Int) {
            binding.txtLanguage.setText(item.country)

            if (sizeFlag != 0) {
                binding.txtLanguage.leftDrawable(item.flag, sizeFlag)
            } else {
                binding.txtLanguage.leftDrawable(item.flag)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectorViewHolder {
        val binding = ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.updateLayoutParams {
            height = if (heightItem == 0) {
                parent.context.resources.getDimensionPixelSize(R.dimen.height_item)
            } else {
                heightItem
            }
        }
        return SelectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectorViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition), sizeFlag)

        val isSelected = currentPositionSelected == holder.adapterPosition
        val backgroundRes = if (isSelected) backgroundSelected else background
        val styleRes = if (isSelected) styleSelected else style

        with(holder.binding) {
            imgBackground.background = backgroundRes
            txtLanguage.setTextAppearance(styleRes)
        }

        holder.binding.root.setOnClickListener {
            notifyItemChanged(currentPositionSelected)
            currentPositionSelected = holder.adapterPosition
            notifyItemChanged(currentPositionSelected)
            itemClickListener(getItem(holder.adapterPosition).code)
        }
    }


    fun setBackgroundItem(background: Drawable?, backgroundSelected: Drawable?) {
        this.background = background
        this.backgroundSelected = backgroundSelected
    }

    fun setHeightItem(@DimenRes heightItem: Int) {
        this.heightItem = heightItem
    }

    fun setSizeFlag(@DimenRes sizeFlag: Int) {
        this.sizeFlag = sizeFlag
    }


    fun setStyleTextAppearance(@StyleRes styleRes: Int, @StyleRes styleSelectedRes: Int) {
        this.style = styleRes
        this.styleSelected = styleSelectedRes
    }

    fun setPositionSelected(positionSelected: Int) {
        this.currentPositionSelected = positionSelected
    }


}

fun TextView.leftDrawable(@DrawableRes id: Int = 0, sizeRes: Int = 0) {
    val drawable = ContextCompat.getDrawable(context, id)
    drawable?.let {
        if (sizeRes != 0) {
            it.setBounds(0, 0, sizeRes, sizeRes)
        } else {
            it.setBounds(0, 0, it.intrinsicWidth, drawable.intrinsicHeight)
        }
    }

    this.setCompoundDrawables(drawable, null, null, null)
}


