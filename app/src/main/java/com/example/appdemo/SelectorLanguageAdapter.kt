package com.example.appdemo

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StyleRes
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

    private var style = R.style.ItemLanguage_Normal
    private var styleSelected = R.style.ItemLanguage_Selected


    class SelectorViewHolder(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ItemLanguage) {
            binding.txtLanguage.setText(item.country)
            binding.imgFlag.setImageResource(item.flag)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectorViewHolder {
        val binding = ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectorViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
        if (currentPositionSelected != holder.adapterPosition) {
            holder.binding.imgBackground.background = background
            holder.binding.txtLanguage.setTextAppearance(style)
        } else {
            holder.binding.imgBackground.background = backgroundSelected
            holder.binding.txtLanguage.setTextAppearance(styleSelected)
        }

        holder.binding.root.setOnClickListener {
            notifyItemChanged(currentPositionSelected)
            notifyItemChanged(holder.adapterPosition)
            currentPositionSelected = holder.adapterPosition
            itemClickListener(getItem(holder.adapterPosition).code)


        }
    }


    fun setBackgroundItem(background: Drawable?, backgroundSelected: Drawable?) {
        this.background = background
        this.backgroundSelected = backgroundSelected
    }

    fun setStyleTextAppearance(@StyleRes styleRes: Int, @StyleRes styleSelectedRes: Int) {
        this.style = styleRes
        this.styleSelected = styleSelectedRes
    }

    fun setPositionSelected(positionSelected : Int){
        this.currentPositionSelected = positionSelected
    }


}



