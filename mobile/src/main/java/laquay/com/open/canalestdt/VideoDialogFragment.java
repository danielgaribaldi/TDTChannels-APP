package laquay.com.open.canalestdt;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.HttpURLConnection;
import java.net.URL;

public class VideoDialogFragment extends DialogFragment implements Player.EventListener {

    public static final String TAG = VideoDialogFragment.class.getSimpleName();
    private static final String CHANNEL_KEY = "CHANNEL_URL";
    private SimpleExoPlayer player;
    private PlayerView channelVideoView;
    private DefaultDataSourceFactory dataSourceFactory;

    public static VideoDialogFragment newInstance(String streamURL) {

        VideoDialogFragment videoDialogFragment = new VideoDialogFragment();

        Bundle args = new Bundle();
        args.putString(CHANNEL_KEY, streamURL);
        videoDialogFragment.setArguments(args);

        return videoDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    private URL resolveRedirectedURL(URL url) {

        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setInstanceFollowRedirects(false);
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            con.addRequestProperty("Referer", "https://www.google.com/");
            con.connect();
            int resCode = con.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_SEE_OTHER
                    || resCode == HttpURLConnection.HTTP_MOVED_PERM
                    || resCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                String Location = con.getHeaderField("Location");
                if (Location.startsWith("/")) {
                    Location = url.getProtocol() + "://" + url.getHost() + Location;
                }
                return resolveRedirectedURL(new URL(Location));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video_dialog, container, false);

        channelVideoView = rootView.findViewById(R.id.channel_video_detail_exoplayer);

        if (getArguments() != null) {
            String channelUrl = getArguments().getString(CHANNEL_KEY);
            if (getActivity() != null && getContext() != null && channelUrl != null) {
                player = new SimpleExoPlayer.Builder(getContext()).build();

                DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                        Util.getUserAgent(getContext(), "lMozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36"),
                        DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS * 10,
                        DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS * 10,
                        true
                );

                dataSourceFactory = new DefaultDataSourceFactory(getContext(), httpDataSourceFactory);

                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                loadVideo(channelUrl);
            }
        }
        return rootView;
    }

    public void loadVideo(String streamURL) {

        channelVideoView.setPlayer(player);

        // Add listener for onPlayerError
        player.addListener(this);

        if (streamURL.contains("m3u8")) {
            MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(streamURL));

            // Prepare the player with the source.
            player.prepare(videoSource);
            player.setPlayWhenReady(true);
        }
        else {

            try {
                MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(resolveRedirectedURL(new URL(streamURL)).toString()));

                // Prepare the player with the source.
                player.prepare(videoSource);
                player.setPlayWhenReady(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onStart() {

        super.onStart();

        if (getActivity() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    // Activity onStop, player must be release because of memory saving
    @Override
    public void onStop() {

        super.onStop();

        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            if (player != null) {
                player.release();
            }

            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

        if (getContext() != null) {
            switch (error.type) {
                case ExoPlaybackException.TYPE_SOURCE:
                    Toast.makeText(getContext(), getContext().getString(R.string.channel_detail_source_error_message), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "TYPE_SOURCE: " + error.getSourceException().getMessage());
                    break;
                case ExoPlaybackException.TYPE_RENDERER:
                    Toast.makeText(getContext(), getContext().getString(R.string.channel_detail_renderer_error_message), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "TYPE_RENDERER: " + error.getRendererException().getMessage());
                    break;
                case ExoPlaybackException.TYPE_UNEXPECTED:
                    Toast.makeText(getContext(), getContext().getString(R.string.channel_detail_unexpected_error_message), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "TYPE_UNEXPECTED: " + error.getUnexpectedException().getMessage());
                    break;
                default:
                    Toast.makeText(getContext(), getContext().getString(R.string.channel_detail_unexpected_error_message), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "TYPE_UNKNOWN: " + error.getCause().getMessage());
            }
        }

        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
}
