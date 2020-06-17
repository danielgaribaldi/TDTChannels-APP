package com.iptv.app.ui.channels.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.iptv.app.R
import com.iptv.app.model.ChannelListItem
import com.iptv.app.ui.channels.holder.ChannelViewHolder

class ChannelsAdapter(private val listener: OnItemClickListener)
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