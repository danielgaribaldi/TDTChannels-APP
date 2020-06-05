package laquay.com.open.canalestdt.component

import laquay.com.open.canalestdt.model.Channel
import java.io.Serializable

data class ChannelListItem(val countryName: String,
                           val communityName: String,
                           val channel: Channel) : Serializable