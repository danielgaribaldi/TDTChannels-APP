package laquay.com.open.canalestdt.model

import java.io.Serializable
import java.util.*

data class Channel(var name: String,
              var web: String = "",
              var logo: String,
              var epgID: String= "",
              var options: ArrayList<ChannelOptions>,
              var extraInfo: String= "") : Serializable {

    override fun toString(): String {
        return "$name, $web, $logo, $epgID, $options, $extraInfo"
    }

}