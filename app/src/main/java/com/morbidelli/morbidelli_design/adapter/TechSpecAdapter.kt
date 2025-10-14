package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.TechSpecCategory

class TechSpecAdapter : RecyclerView.Adapter<TechSpecAdapter.TechSpecViewHolder>() {

    private var categories = listOf<TechSpecCategory>()

    fun updateData(newCategories: List<TechSpecCategory>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    fun collapseAll() {
        categories = categories.map { it.copy(isExpanded = false) }
        notifyDataSetChanged()
    }

    fun expandAll() {
        categories = categories.map { it.copy(isExpanded = true) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechSpecViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tech_spec, parent, false)
        return TechSpecViewHolder(view)
    }

    override fun onBindViewHolder(holder: TechSpecViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    inner class TechSpecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val ivExpand: ImageView = itemView.findViewById(R.id.iv_expand)
        private val llSpecifications: LinearLayout = itemView.findViewById(R.id.ll_specifications)

        fun bind(category: TechSpecCategory) {
            tvTitle.text = category.title
            ivExpand.setImageResource(if (category.isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more)
            
            llSpecifications.removeAllViews()
            if (category.isExpanded) {
                category.specifications.forEach { (label, value) ->
                    val specView = LayoutInflater.from(itemView.context)
                        .inflate(R.layout.item_specification_detail, llSpecifications, false)
                    
                    val tvLabel: TextView = specView.findViewById(R.id.tv_label)
                    val tvValue: TextView = specView.findViewById(R.id.tv_value)
                    
                    tvLabel.text = label
                    tvValue.text = value
                    
                    llSpecifications.addView(specView)
                }
            }
            
            itemView.setOnClickListener {
                val updatedCategory = category.copy(isExpanded = !category.isExpanded)
                categories = categories.toMutableList().apply {
                    set(adapterPosition, updatedCategory)
                }
                notifyItemChanged(adapterPosition)
            }
        }
    }
}
