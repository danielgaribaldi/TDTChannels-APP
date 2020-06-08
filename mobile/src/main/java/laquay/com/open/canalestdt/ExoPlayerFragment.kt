package laquay.com.open.canalestdt

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import laquay.com.open.canalestdt.model.Channel
import java.net.HttpURLConnection
import java.net.URL

class ExoPlayerFragment : DialogFragment() {

    companion object {
        private const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36"
        private const val CHANNEL_KEY = "CHANNEL"
        private val TAG = ExoPlayerFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(channel: Channel.Source): ExoPlayerFragment {
            val videoDialogFragment = ExoPlayerFragment()
            val args = Bundle()
            args.putParcelable(CHANNEL_KEY, channel)
            videoDialogFragment.arguments = args
            return videoDialogFragment
        }
    }

    private var exoPlayerView: PlayerView? = null
    private lateinit var exoPlayer: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_video_dialog, container, false)
        exoPlayerView = rootView.findViewById(R.id.channel_video_detail_exoplayer)

        if (arguments != null) {

            val channelUrl = arguments!!.getParcelable<Channel.Source>(CHANNEL_KEY)!!
            activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            loadVideo(channelUrl)
        }
        return rootView
    }

    override fun onStart() {
        super.onStart()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onStop() {

        super.onStop()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        exoPlayer.release()
    }

    private fun resolveRedirectedURL(url: URL): URL {
        try {
            val con = url.openConnection() as HttpURLConnection
            con.instanceFollowRedirects = false
            con.setRequestProperty("User-Agent", USER_AGENT)
            con.addRequestProperty("Accept-Language", "en-US,en;q=0.8")
            con.addRequestProperty("Referer", "https://www.google.com/")
            con.connect()
            val resCode = con.responseCode
            if (resCode == HttpURLConnection.HTTP_SEE_OTHER ||
                    resCode == HttpURLConnection.HTTP_MOVED_PERM ||
                    resCode == HttpURLConnection.HTTP_MOVED_TEMP) {

                var location = con.getHeaderField("Location")
                if (location.startsWith("/")) {
                    location = url.protocol + "://" + url.host + location
                }
                return resolveRedirectedURL(URL(location))
            }
        }
        catch (e: Exception) {
            println(e.message)
        }
        return url
    }

    fun setExoPlayerListener(listener: Player.EventListener){
        exoPlayer = SimpleExoPlayer.Builder(context!!).build()
        exoPlayer.addListener(listener)
    }

    private fun loadVideo(source: Channel.Source) {

        exoPlayerView!!.player = exoPlayer

        val dataSourceFactory = DefaultDataSourceFactory(context, DefaultHttpDataSourceFactory(
                USER_AGENT,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS * 10,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS * 10,
                true
        ))

        val sourceUri = Uri.parse(if (source.resolveRedirect) {
            resolveRedirectedURL(URL(source.url))
        } else {
            URL(source.url)
        }.toString())

        val videoSource = when (source.type) {
            Channel.Source.Type.EXO_HLS -> HlsMediaSource.Factory(dataSourceFactory).createMediaSource(sourceUri)
            else -> null
        }

        // Prepare the player with the source.
        exoPlayer.prepare(videoSource!!)
        exoPlayer.playWhenReady = true

    }
}