package com.iptv.app.ui.channels.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iptv.app.R
import com.iptv.app.model.ChannelListItem
import com.iptv.app.ui.channels.adapter.ChannelsAdapter

class ChannelViewHolder(itemView: View,
                        private val listener: ChannelsAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.channel_icon)
    private val titleView: TextView = itemView.findViewById(R.id.channel_title)
    private val subtitleView: TextView = itemView.findViewById(R.id.channel_description)

    fun setChannel(channel: ChannelListItem) {

        titleView.text = channel.channel.name
        subtitleView.text = channel.communityName

        Glide.with(itemView.context)
                .load(channel.channel.logo)
                .placeholder(R.mipmap.ic_launcher)
                .fallback(R.mipmap.ic_launcher)
                .into(imageView)

        itemView.setOnClickListener { listener.onItemClickListener(channel) }
    }
}
