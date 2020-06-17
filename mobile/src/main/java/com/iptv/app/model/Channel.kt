package com.iptv.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Channel(val name: String,
                   val logo: String,
                   val sources: ArrayList<Source>,
                   val epgID: String = "",
                   val web: String = "",
                   val extraInfo: String = "") : Parcelable {

    @Parcelize
    data class Source(val type: Type,
                      val url: String,
                      val resolveRedirect: Boolean = false) : Parcelable {

        enum class Type(value: String) {
            EXO_HLS("exo_hls"),
            EXO_DASH("exo_dash"),
            YOUTUBE("youtube"),
            YOUTUBE_LIVE("youtube_live")
        }
    }
}

@Parcelize
data class Country(val name: String, val channelGroups: ArrayList<ChannelGroup>) : Parcelable {

    @Parcelize
    data class ChannelGroup(val name: String, val channels: ArrayList<Channel>) : Parcelable
}

@Parcelize
data class ChannelListItem(val countryName: String,
                           val communityName: String,
                           val channel: Channel) : Parcelable