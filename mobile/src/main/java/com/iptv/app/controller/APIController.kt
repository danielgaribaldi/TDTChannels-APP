package com.iptv.app.controller

import android.content.Context
import android.util.Log
import com.iptv.app.model.Channel
import com.iptv.app.model.Channel.Source
import com.iptv.app.model.Country
import com.iptv.app.utils.APIUtils

class APIController private constructor() {
    private var televisionChannels: ArrayList<Country> = ArrayList()
    private var radioChannels: ArrayList<Country>? = null

    fun loadChannels(typeOfRequest: TypeOfRequest, forceUpdate: Boolean, context: Context, responseServerCallback: ResponseServerCallback) {

        if (typeOfRequest == TypeOfRequest.TV) {

            televisionChannels.clear()

            val raiChannels = arrayListOf(Channel(name = "Rai 1", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://raiuno1-live.akamaized.net/hls/live/598308/raiuno1/raiuno1/playlist_ma.m3u8", true))),
                                          Channel(name = "Rai 2", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=308718", true))),
                                          Channel(name = "Rai 3", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=308709", true))),
                                          Channel(name = "Rai 4", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746966", true))),
                                          Channel(name = "Rai 5", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=395276", true))),
                                          Channel(name = "Rai Premium", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746992", true))),
                                          Channel(name = "Rai Gulp", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746953", true))),
                                          Channel(name = "Rai Yoyo", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746899", true))),
                                          Channel(name = "Rai History", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=746990", true))),
                                          Channel(name = "Rai School", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=747011", true))),
                                          Channel(name = "Rai Movie", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=747002", true))),
                                          Channel(name = "Rai Sport", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=358025", true))),
                                          Channel(name = "Rai News 24H", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logo_of_RAI_%282016%29.svg/1200px-Logo_of_RAI_%282016%29.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://mediapolis.rai.it/relinker/relinkerServlet.htm?cont=1", true))))

            val mediasetChannels = arrayListOf(Channel(name = "Canale 5", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Canale_5_-_2018_logo.svg/1200px-Canale_5_-_2018_logo.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(C5)/index.m3u8"))),
                                               Channel(name = "Italia 1", logo = "https://vignette.wikia.nocookie.net/logopedia/images/c/cc/Italia_1_logo.svg/revision/latest/scale-to-width-down/340?cb=20100308164118", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(I1)/index.m3u8"))),
                                               Channel(name = "Italia 2", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Logo_Italia2.svg/1200px-Logo_Italia2.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(I2)/index.m3u8"))),
                                               Channel(name = "Rete 4", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/Rete_4_-_Logo_2018.svg/1200px-Rete_4_-_Logo_2018.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(R4)/index.m3u8"))),
                                               Channel(name = "20 Mediaset", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/20_Mediaset_2018.svg/1200px-20_Mediaset_2018.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(LB)/index.m3u8"))),
                                               Channel(name = "La 5", logo = "https://banner2.cleanpng.com/20181106/aer/kisspng-la5-logo-italy-la-cinq-television-5be27132c70ca1.9523043215415667708153.jpg", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KA)/index.m3u8"))),
                                               Channel(name = "Mediaset Extra", logo = "https://upload.wikimedia.org/wikipedia/commons/0/0f/Mediaset_extra_logo.PNG", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KQ)/index.m3u8"))),
                                               Channel(name = "Focus", logo = "https://vignette.wikia.nocookie.net/logopedia/images/a/a0/Focus_New.svg/revision/latest/scale-to-width-down/340?cb=20151222150429", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(FU)/index.m3u8"))),
                                               Channel(name = "Top Crime", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/Top_Crime_-_Logo_2013.svg/1200px-Top_Crime_-_Logo_2013.svg.png",
                                                       sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live3-mediaset-it.akamaized.net/Content/hls_h0_clr_vos/live/channel(lt)/index.m3u8"))),
                                               Channel(name = "Cine 34", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Cine34_logo.svg/1200px-Cine34_logo.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(B6)/index.m3u8"))),
                                               Channel(name = "Iris", logo = "https://upload.wikimedia.org/wikipedia/en/thumb/1/12/Logo_iris.svg/1200px-Logo_iris.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KI)/index.m3u8"))),
                                               Channel(name = "Boing", logo = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Logo_Boing.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KB)/index.m3u8"))),
                                               Channel(name = "Cartoonito", logo = "https://upload.wikimedia.org/wikipedia/en/thumb/4/42/Cartoonito_logo.svg/1280px-Cartoonito_logo.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(LA)/index.m3u8"))),
                                               Channel(name = "TGCom", logo = "https://pbs.twimg.com/profile_images/456372656359489536/W4tnPT1m_400x400.jpeg", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://live2-mediaset-it.akamaized.net/Content/hls_h0_cls_vos/live/channel(KF)/index.m3u8"))))

            val skyChannels = arrayListOf(Channel(name = "Dmax", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/DMAX_BLACK.svg/1280px-DMAX_BLACK.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_5@825063/master.m3u8"))),
                                          Channel(name = "Nove", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Nove_-_Logo_2017.svg/440px-Nove_-_Logo_2017.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_3@810997/master.m3u8"))),
                                          Channel(name = "Home & Garden", logo = "https://www.cabletv.com/app/uploads/HGTV-Logo-1-1.jpg", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_7@106896/master.m3u8"))),
                                          Channel(name = "Giallo", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/Giallo_-_Logo_2014.svg/1200px-Giallo_-_Logo_2014.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_2@810996/master.m3u8"))),
                                          Channel(name = "Motor Trend", logo = "https://images-na.ssl-images-amazon.com/images/I/412-MY9AQmL._SY355_.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_1@810993/master.m3u8"))),
                                          Channel(name = "Food Network", logo = "https://upload.wikimedia.org/wikipedia/commons/f/f9/Food_Network_New_Logo.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_6@1000854/master.m3u8"))),
                                          Channel(name = "TEST", logo = "", sources = arrayListOf(Source(Source.Type.YOUTUBE_LIVE, "https://www.youtube.com/watch?v=bWdjJ-dWQvo"))),
                                          Channel(name = "TEST2", logo = "", sources = arrayListOf(Source(Source.Type.YOUTUBE, "https://www.youtube.com/watch?v=GrrMORBZv9w"))),

                                          Channel(name = "RealTime", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Discovery_Real_Time_E.svg/1200px-Discovery_Real_Time_E.svg.png", sources = arrayListOf(Source(Source.Type.EXO_HLS, "https://sbshdlu5-lh.akamaihd.net/i/sbshdl_4@810998/master.m3u8"))))

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