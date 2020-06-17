package laquay.com.open.canalestdt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import laquay.com.open.canalestdt.model.ChannelListItem


class ChannelViewHolder(itemView: View,
                        private val listener: ChannelListAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

    private var imageView: ImageView = itemView.findViewById(R.id.channel_icon)
    private var titleView: TextView = itemView.findViewById(R.id.channel_title)
    private var subtitleView: TextView = itemView.findViewById(R.id.channel_description)

    fun setChannel(channel: ChannelListItem) {

        titleView.text = channel.channel.name
        subtitleView.text = channel.communityName
        val imageUrl = channel.channel.logo
        Glide.with(itemView.context).load(imageUrl).placeholder(R.mipmap.ic_launcher).fallback(R.mipmap.ic_launcher).into(imageView)
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