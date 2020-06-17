package com.iptv.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iptv.app.model.ChannelListItem


class ChannelViewHolder(itemView: View,
                        private val listener: ChannelListAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

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


class ChannelListAdapter(private val listener: OnItemClickListener)
    : ListAdapter<ChannelListItem, ChannelViewHolder>(object : DiffUtil.ItemCallback<ChannelListItem>() {

    override fun areItemsTheSame(oldItem: ChannelListItem, newItem: ChannelListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChannelListItem, newItem: ChannelListItem): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ChannelViewHolder(LayoutInflater.from(parent.context)
                                                                            .inflate(R.layout.item_list_channels, parent, false), listener)

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) = holder.setChannel(getItem(position))


    interface OnItemClickListener {
        fun onItemClickListener(channelList: ChannelListItem)
    }
}