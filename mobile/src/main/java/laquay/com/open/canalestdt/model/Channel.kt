package laquay.com.open.canalestdt.model

import java.io.Serializable
import java.util.*

data class Channel(val name: String,
                   val logo: String,
                   val sources: ArrayList<Source>,
                   val epgID: String = "",
                   val web: String = "",
                   val extraInfo: String = "") : Serializable {


    data class Source(val type: Type,
                      val url: String,
                      val resolveRedirect: Boolean = false) : Serializable {

        enum class Type(value: String) {
            EXO_HLS("exo_hls"),
            YOUTUBE("youtube")
        }
    }
}


data class Country(val name: String, val channelGroups: ArrayList<ChannelGroup>) : Serializable {

    data class ChannelGroup(val name: String, val channels: ArrayList<Channel>) : Serializable
}


data class ChannelListItem(val countryName: String,
                           val communityName: String,
                           val channel: Channel) : Serializable