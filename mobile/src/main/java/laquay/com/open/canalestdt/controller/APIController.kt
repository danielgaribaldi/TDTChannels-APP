package laquay.com.open.canalestdt.controller

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import laquay.com.open.canalestdt.model.Channel
import laquay.com.open.canalestdt.model.ChannelOptions
import laquay.com.open.canalestdt.model.Community
import laquay.com.open.canalestdt.model.Country
import laquay.com.open.canalestdt.utils.APIUtils
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class APIController private constructor() {
    private var televisionChannels: ArrayList<Country> = ArrayList()
    private var radioChannels: ArrayList<Country>? = null

    fun loadChannels(typeOfRequest: TypeOfRequest, forceUpdate: Boolean, context: Context, responseServerCallback: ResponseServerCallback) {


        if (typeOfRequest == TypeOfRequest.TV) {

            televisionChannels.clear()

            val raiChannels = arrayListOf(Channel(name = "Rai 1", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://raiuno1-live.akamaized.net/hls/live/598308/raiuno1/raiuno1/playlist_ma.m3u8"))),
                                          Channel(name = "Rai 2", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=308718"))),
                                          Channel(name = "Rai 3", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=308709"))),
                                          Channel(name = "Rai 4", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746966"))),
                                          Channel(name = "Rai 5", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=395276"))),
                                          Channel(name = "Rai Premium", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746992"))),
                                          Channel(name = "Rai Gulp", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746953"))),
                                          Channel(name = "Rai Yoyo", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746899"))),
                                          Channel(name = "Rai History", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746990"))),
                                          Channel(name = "Rai School", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=747011"))),
                                          Channel(name = "Rai Movie", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=747002"))),
                                          Channel(name = "Rai Sport", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=358025"))),
                                          Channel(name = "Rai News 24H", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=1"))))

            val mediasetChannels = arrayListOf(Channel(name = "Canale 5", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(C5)/index.m3u8"))),
                                               Channel(name = "Italia 1", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(I1)/index.m3u8"))),
                                               Channel(name = "Italia 2", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(I2)/index.m3u8"))),
                                               Channel(name = "Rete 4", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(R4)/index.m3u8"))),
                                               Channel(name = "20 Mediaset", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(LB)/index.m3u8"))),
                                               Channel(name = "La 5", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KA)/index.m3u8"))),
                                               Channel(name = "Mediaset Extra", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KQ)/index.m3u8"))),
                                               Channel(name = "Focus", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(FU)/index.m3u8"))),
                                               Channel(name = "Top Crime", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(LT)/index.m3u8"))),
                                               Channel(name = "Cine 34", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(B6)/index.m3u8"))),
                                               Channel(name = "Iris", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KI)/index.m3u8"))),
                                               Channel(name = "Boing", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KB)/index.m3u8"))),
                                               Channel(name = "Cartoonito", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(LA)/index.m3u8"))),
                                               Channel(name = "TGCom", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KF)/index.m3u8"))))

            val skyChannels = arrayListOf(Channel(name = "Dmax", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_5@825063/master.m3u8"))),
                                          Channel(name = "Nove", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_3@810997/master.m3u8"))),
                                          Channel(name = "Home & Garden", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_7@106896/master.m3u8"))),
                                          Channel(name = "Giallo", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_2@810996/master.m3u8"))),
                                          Channel(name = "Motor Trend", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_1@810993/master.m3u8"))),
                                          Channel(name = "Food Network", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_6@1000854/master.m3u8"))),
                                          Channel(name = "RealTime", logo = "", options = arrayListOf(ChannelOptions("m3u8", "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_4@810998/master.m3u8"))))

            val communities = arrayListOf(Community("Rai", raiChannels),
                                          Community("Mediaset", mediasetChannels),
                                          Community("Discovery", skyChannels))

            televisionChannels.add(Country("Italia", communities))

            responseServerCallback.onChannelsLoadServer(televisionChannels)

        }
        else {
            if (typeOfRequest == TypeOfRequest.RADIO) {
                if (!forceUpdate && radioChannels != null && !radioChannels!!.isEmpty()) {
                    Log.i(TAG, "Load radio channels from cache")
                    responseServerCallback.onChannelsLoadServer(radioChannels)
                }
                else {
                    Log.i(TAG, "Load radio channels from server: " + APIUtils.RADIO_URL)
                    radioChannels = ArrayList()
                    downloadChannels(APIUtils.RADIO_URL, radioChannels!!, context, responseServerCallback)
                }
            }
        }
    }

    private fun downloadChannels(URL: String, channelsToMatch: ArrayList<Country>, context: Context, responseServerCallback: ResponseServerCallback) {
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                Response.Listener { response ->
                    Log.i(TAG, "Response OK")
                    try {
                        val countriesJsonArray = response.getJSONArray("countries")
                        for (i in 0 until countriesJsonArray.length()) {
                            val country = countriesJsonArray.getJSONObject(i)
                            val countryName = country.getString("name")
                            val communitiesArray = country.getJSONArray("ambits")
                            val communities = ArrayList<Community>()
                            for (j in 0 until communitiesArray.length()) {
                                val communityJson = communitiesArray.getJSONObject(j)
                                val communityName = communityJson.getString("name")
                                val channelsArray = communityJson.getJSONArray("channels")
                                val channels = ArrayList<Channel>()
                                for (k in 0 until channelsArray.length()) {
                                    val channelJson = channelsArray.getJSONObject(k)
                                    val channelName = channelJson.getString("name")
                                    val channelWeb = channelJson.getString("web")
                                    val channelLogo = channelJson.getString("logo")
                                    val channelEPG = channelJson.getString("epg_id")
                                    val channelOptionsJson = channelJson.getJSONArray("options")
                                    val channelExtraInfo = channelJson.getString("extra_info")
                                    val channelOptions = ArrayList<ChannelOptions>()
                                    for (z in 0 until channelOptionsJson.length()) {
                                        val optionJson = channelOptionsJson.getJSONObject(z)
                                        val optionFormat = optionJson.getString("format")
                                        val optionURL = optionJson.getString("url")
                                        channelOptions.add(ChannelOptions(optionFormat, optionURL))
                                    }
                                    val channel = Channel(channelName, channelWeb, channelLogo,
                                                          channelEPG, channelOptions, channelExtraInfo)
                                    //Log.i(TAG, "Adding channel: " + channel.toString());
                                    channels.add(channel)
                                }
                                communities.add(Community(communityName, channels))
                            }
                            channelsToMatch.add(Country(countryName, communities))
                        }
                        responseServerCallback.onChannelsLoadServer(channelsToMatch)
                    }
                    catch (e: JSONException) {
                        Log.e(TAG, "ERROR Parsing JSON")
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { // Do something when error occurred
                    Log.e(TAG, "Error while accessing to URL $URL")
                }
        )

        // Add JsonArrayRequest to the RequestQueue
        VolleyController.getInstance(context).addToQueue(jsonObjectRequest)
    }

    enum class TypeOfRequest {
        TV, RADIO
    }

    interface ResponseServerCallback {
        fun onChannelsLoadServer(countries: ArrayList<Country>?)
    }

    companion object {
        val TAG = APIController::class.java.simpleName
        private var instance: APIController? = null

        @JvmStatic
        fun getInstance(): APIController? {
            if (instance == null) {
                createInstance()
            }
            return instance
        }

        @Synchronized
        private fun createInstance() {
            if (instance == null) {
                instance = APIController()
            }
        }
    }
}