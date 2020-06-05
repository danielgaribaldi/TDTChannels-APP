package laquay.com.open.canalestdt.controller

import android.content.Context
import android.util.Log
import laquay.com.open.canalestdt.model.Channel
import laquay.com.open.canalestdt.model.Channel.Source
import laquay.com.open.canalestdt.model.Country
import laquay.com.open.canalestdt.utils.APIUtils

class APIController private constructor() {
    private var televisionChannels: ArrayList<Country> = ArrayList()
    private var radioChannels: ArrayList<Country>? = null

    fun loadChannels(typeOfRequest: TypeOfRequest, forceUpdate: Boolean, context: Context, responseServerCallback: ResponseServerCallback) {


        if (typeOfRequest == TypeOfRequest.TV) {

            televisionChannels.clear()

            val raiChannels = arrayListOf(Channel(name = "Rai 1", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://raiuno1-live.akamaized.net/hls/live/598308/raiuno1/raiuno1/playlist_ma.m3u8", true))),
                                          Channel(name = "Rai 2", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=308718", true))),
                                          Channel(name = "Rai 3", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=308709", true))),
                                          Channel(name = "Rai 4", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746966", true))),
                                          Channel(name = "Rai 5", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=395276", true))),
                                          Channel(name = "Rai Premium", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746992", true))),
                                          Channel(name = "Rai Gulp", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746953", true))),
                                          Channel(name = "Rai Yoyo", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746899", true))),
                                          Channel(name = "Rai History", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746990", true))),
                                          Channel(name = "Rai School", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=747011", true))),
                                          Channel(name = "Rai Movie", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=747002", true))),
                                          Channel(name = "Rai Sport", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=358025", true))),
                                          Channel(name = "Rai News 24H", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=1", true))))

            val mediasetChannels = arrayListOf(Channel(name = "Canale 5", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(C5)/index.m3u8"))),
                                               Channel(name = "Italia 1", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(I1)/index.m3u8"))),
                                               Channel(name = "Italia 2", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(I2)/index.m3u8"))),
                                               Channel(name = "Rete 4", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(R4)/index.m3u8"))),
                                               Channel(name = "20 Mediaset", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(LB)/index.m3u8"))),
                                               Channel(name = "La 5", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KA)/index.m3u8"))),
                                               Channel(name = "Mediaset Extra", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KQ)/index.m3u8"))),
                                               Channel(name = "Focus", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(FU)/index.m3u8"))),
                                               Channel(name = "Top Crime", logo = "", sources = arrayListOf(Source(Source.Type.YOUTUBE, "https://www.youtube.com/watch?v=I7g08nwEmyY"),
                                                                                                            Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(LT)/index.m3u8"))),
                                               Channel(name = "Cine 34", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(B6)/index.m3u8"))),
                                               Channel(name = "Iris", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KI)/index.m3u8"))),
                                               Channel(name = "Boing", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KB)/index.m3u8"))),
                                               Channel(name = "Cartoonito", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(LA)/index.m3u8"))),
                                               Channel(name = "TGCom", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KF)/index.m3u8"))))

            val skyChannels = arrayListOf(Channel(name = "Dmax", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_5@825063/master.m3u8"))),
                                          Channel(name = "Nove", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_3@810997/master.m3u8"))),
                                          Channel(name = "Home & Garden", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_7@106896/master.m3u8"))),
                                          Channel(name = "Giallo", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_2@810996/master.m3u8"))),
                                          Channel(name = "Motor Trend", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_1@810993/master.m3u8"))),
                                          Channel(name = "Food Network", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_6@1000854/master.m3u8"))),
                                          Channel(name = "RealTime", logo = "", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_4@810998/master.m3u8"))))

            val communities = arrayListOf(Country.ChannelGroup("Rai", raiChannels),
                                          Country.ChannelGroup("Mediaset", mediasetChannels),
                                          Country.ChannelGroup("Discovery", skyChannels))

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
                   // downloadChannels(APIUtils.RADIO_URL, radioChannels!!, context, responseServerCallback)
                }
            }
        }
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