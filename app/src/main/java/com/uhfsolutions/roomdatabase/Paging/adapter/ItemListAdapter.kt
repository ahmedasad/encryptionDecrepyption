package com.uhfsolutions.roomdatabase.Paging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.uhfsolutions.roomdatabase.Paging.model.Items
import com.uhfsolutions.roomdatabase.R

class ItemListAdapter(context: Context) : PagedListAdapter<Items, ItemListAdapter.ViewHolder>(DiffUtillCallBacks()) {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindData(item:Items){
            itemView.findViewById<TextView>(R.id.txtOwnerName).text = item.owner?.let { it.display_name.toString()}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,null,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

}

class DiffUtillCallBacks:DiffUtil.ItemCallback<Items>(){
    override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem.answer_id == newItem.answer_id
    }

    override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem.equals(newItem)
    }

}