package com.example.handybook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handybook.R
import com.example.handybook.model.Comment

class CommentsAdapter(var comments:MutableList<Comment>) : RecyclerView.Adapter<CommentsAdapter.CommentsHolder>(){


    class CommentsHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name:TextView = itemView.findViewById(R.id.c_username)
        var comment:TextView = itemView.findViewById(R.id.c_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsHolder {
        return CommentsHolder(LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentsHolder, position: Int) {
        var item = comments[position]
//        holder.name.text = item.username
        holder.comment.text = item.text
    }

}