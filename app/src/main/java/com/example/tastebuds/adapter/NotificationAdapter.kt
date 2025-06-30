package com.example.tastebuds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.NotificationViewModel
import com.example.tastebuds.databinding.NotificationItemBinding

class NotificationAdapter(var notificationList : ArrayList<NotificationViewModel>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(view)
    }

    override fun getItemCount(): Int = notificationList.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class NotificationViewHolder(var bind : NotificationItemBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(position: Int) {
            bind.notificationImage.setImageResource(notificationList[position].notificationImage)
            bind.notificationText.text = notificationList[position].notificationInfo
        }

    }
}