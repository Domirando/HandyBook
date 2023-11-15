package com.example.handybook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.handybook.R
import com.example.handybook.model.Book

class ItemReadingAdapter(var item_reading:MutableList<Book>) : RecyclerView.Adapter<ItemReadingAdapter.CartHolder>(){

    class CartHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.kitob3_name)
        var author: TextView = itemView.findViewById(R.id.kitob3_author)
        var imageView: ImageView =itemView.findViewById(R.id.kitob3_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CartHolder {
        return CartHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_reading, parent, false))
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        var kitob = item_reading[position]
        holder.name.text = kitob.name
        holder.author.text=kitob.author
        holder.imageView.load(kitob.image)

    }

    override fun getItemCount(): Int {
        return item_reading.size
    }


}