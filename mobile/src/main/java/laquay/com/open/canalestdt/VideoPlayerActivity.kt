package laquay.com.open.canalestdt

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import laquay.com.open.canalestdt.model.Channel
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

@ExperimentalStdlibApi
class VideoPlayerActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL = "CHANNEL"
        private const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36"
    }

    private lateinit var exoPlayerView: PlayerView
    private lateinit var youtubePlayerView: YouTubePlayerView

    private var exoPlayer: SimpleExoPlayer? = null
    lateinit var sourcesQueue: ArrayDeque<Channel.Source>

    private val exoListener = object : Player.EventListener {

        override fun onPlayerError(error: ExoPlaybackException) {

            supportFragmentManager.popBackStackImmediate()
            loadSource()
        }
    }

    override fun onStart() {
        super.onStart()
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onStop() {

        super.onStop()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        exoPlayer?.release()
    }

    private fun getYoutubeID(url: String): String {

        /*  WORKING URLS
            http://www.youtube.com/watch?v=dQw4w9WgXcQ&a=GxdCwVVULXctT2lYDEPllDR0LRTutYfW
            http://www.youtube.com/watch?v=dQw4w9WgXcQ
            http://youtu.be/dQw4w9WgXcQ
            http://www.youtube.com/embed/dQw4w9WgXcQ
            http://www.youtube.com/v/dQw4w9WgXcQ
            http://www.youtube.com/e/dQw4w9WgXcQ
            http://www.youtube.com/watch?v=dQw4w9WgXcQ
            http://www.youtube.com/watch?feature=player_embedded&v=dQw4w9WgXcQ
            http://www.youtube-nocookie.com/v/6L3ZvIMwZFM?version=3&hl=en_US&rel=0
        */

        var videoID = ""

        val pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"

        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(url)

        if (matcher.find()) {
            videoID = matcher.group()
        }

        return videoID
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_player_activity)

        exoPlayerView = findViewById(R.id.exoPlayerView)
        youtubePlayerView = findViewById(R.id.youtubePlayerView)

        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val channel = intent.getParcelableExtra<Channel>(CHANNEL)!!

        sourcesQueue = ArrayDeque(channel.sources)

        loadSource()
    }

    private fun loadYoutubeSource(source: Channel.Source) {

        youtubePlayerView.visibility = View.VISIBLE

        this.lifecycle.addObserver(youtubePlayerView)

        // youtubePlayerView.enterFullScreen()
        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(getYoutubeID(source.url), 0F)
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                loadSource()
            }
        })
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

    private fun loadExoPlayerSource(source: Channel.Source) {

        exoPlayerView.visibility = View.VISIBLE
        exoPlayer = SimpleExoPlayer.Builder(this).build()
        exoPlayer?.addListener(exoListener)
        exoPlayerView.player = exoPlayer

        val dataSourceFactory = DefaultDataSourceFactory(this, DefaultHttpDataSourceFactory(
                USER_AGENT,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS * 10,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS * 10,
                true
        ))

        val sourceUri = Uri.parse(if (source.resolveRedirect) {
            resolveRedirectedURL(URL(source.url))
        }
                                  else {
            URL(source.url)
        }.toString())

        val videoSource = when (source.type) {
            Channel.Source.Type.EXO_HLS -> HlsMediaSource.Factory(dataSourceFactory).createMediaSource(sourceUri)
            Channel.Source.Type.YOUTUBE -> null
            Channel.Source.Type.EXO_DASH -> ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(sourceUri)
        }

        exoPlayer?.prepare(videoSource!!)
        exoPlayer?.playWhenReady = true
    }

    private fun loadSource() {

        exoPlayerView.visibility = View.GONE
        youtubePlayerView.visibility = View.GONE

        try {
            val source = sourcesQueue.removeFirst()

            when (source.type) {

                Channel.Source.Type.EXO_HLS -> loadExoPlayerSource(source)
                Channel.Source.Type.YOUTUBE -> loadYoutubeSource(source)
            }

        }
        catch (e: NoSuchElementException) {
            finish()
        }
    }
}