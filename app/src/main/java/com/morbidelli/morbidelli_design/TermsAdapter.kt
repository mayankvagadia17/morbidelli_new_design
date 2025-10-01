package com.morbidelli.morbidelli_design

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TermsAdapter(
    private val terms: List<TermsItem>
) : RecyclerView.Adapter<TermsAdapter.TermsViewHolder>() {

    class TermsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val content: TextView = itemView.findViewById(R.id.tv_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_terms, parent, false)
        return TermsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TermsViewHolder, position: Int) {
        val term = terms[position]
        
        holder.title.text = term.title
        holder.content.text = term.content
    }

    override fun getItemCount(): Int = terms.size
}
