package com.example.appdemo

import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
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
    private var styleTitle = 0

    @StyleRes
    private var styleTitleSelected = 0

    @StyleRes
    private var style = 0

    @StyleRes
    private var styleSelected = 0


    class SelectorViewHolder(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ItemLanguage) {
            binding.txtLanguageTitle.setText(item.country)
            binding.txtLanguageStyle.setText(item.country)
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
        holder.bind(getItem(holder.adapterPosition))

        val isSelected = currentPositionSelected == holder.adapterPosition
        val backgroundRes = if (isSelected) backgroundSelected else background
        val styleTitle = if (isSelected) styleTitleSelected else styleTitle
        val styleItem = if (isSelected) styleSelected else style

        if (styleItem != 0) {
            with(holder.binding) {
                txtLanguageStyle.visible()
                llNormal.gone()

                imgBackground.background = backgroundRes
                txtLanguageStyle.setTextAppearance(styleItem)

                if (sizeFlag != 0) {
                    txtLanguageStyle.bindIconDrawable(getItem(holder.adapterPosition).flag, sizeFlag, styleItem)
                } else {
                    txtLanguageStyle.bindIconDrawable(getItem(holder.adapterPosition).flag, styleRes = styleItem)
                }
            }
        } else {
            with(holder.binding) {
                txtLanguageStyle.gone()
                llNormal.visible()

                imgBackground.background = backgroundRes
                imgLanguage.setImageResource(getItem(holder.adapterPosition).flag)
                txtLanguageTitle.setTextAppearance(styleTitle)

            }
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
        this.styleTitle = styleRes
        this.styleTitleSelected = styleSelectedRes
    }

    fun setStyleItem(@StyleRes styleRes: Int, @StyleRes styleSelected: Int) {
        this.style = styleRes
        this.styleSelected = styleSelected
    }

    fun setPositionSelected(positionSelected: Int) {
        this.currentPositionSelected = positionSelected
    }


}

fun TextView.bindIconDrawable(@DrawableRes id: Int = 0, sizeRes: Int = 0, @StyleRes styleRes: Int) {

    val a: TypedArray = context.theme.obtainStyledAttributes(styleRes, intArrayOf(android.R.attr.drawableEnd))
    val drawableResourceId = a.getResourceId(0, 0)
    val drawableEnd: Drawable? = AppCompatResources.getDrawable(context, drawableResourceId)

    val drawable = ContextCompat.getDrawable(context, id)
    drawable?.let {
        if (sizeRes != 0) {
            it.setBounds(0, 0, sizeRes, sizeRes)
        } else {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
        }
    }

    this.setCompoundDrawablesWithIntrinsicBounds(drawable, null, drawableEnd, null)
}

