package elsaghier.example.com.ntlbaking.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsaghier.example.com.ntlbaking.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class StepDetailsFragment extends Fragment {

    @BindView(R.id.step_description)
    TextView mDescription;
    @BindView(R.id.next)
    TextView mNext;
    @BindView(R.id.prev)
    TextView mPrev;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mExoPlayerView;
    private SimpleExoPlayer mExoPlayer;
    String mVideoURL;

    private BandwidthMeter bandwidthMeter;
    public static int pos;

    private DataSource.Factory mediaDataSourceFactory;

    final String SELECTED_STATE = "videoState";
    final String SELECTED_POSITION = "videoTime";
    final String SELECTED_VIDEO_POSITION = "videopos";
    long position;
    private boolean playWhenReady = true;

    public StepDetailsFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_POSITION, position);
        outState.putLong(SELECTED_VIDEO_POSITION, pos);
        outState.putBoolean(SELECTED_STATE, playWhenReady);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        position = C.TIME_UNSET;
        View v = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, v);
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean(SELECTED_STATE);
            pos = savedInstanceState.getInt(SELECTED_VIDEO_POSITION);
        }
        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
            changeURLandDescription();
            // Next icon click
            mNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos = ((++pos) % RecipeDetailFragment.modelList.size());
                    changeURLandDescription();
                    releasePlayer();
                    initializePlayer(mVideoURL);
                }
            });
            // previous icon click
            mPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos = ((--pos) % RecipeDetailFragment.modelList.size());
                    changeURLandDescription();
                    releasePlayer();
                    initializePlayer(mVideoURL);
                }
            });
            mExoPlayerViewInit();
            initializePlayer(mVideoURL);
        }
        return v;
    }

    private void changeURLandDescription() {
        mVideoURL = RecipeDetailFragment.modelList.get(pos).getVideoURL();
        mDescription.setText(RecipeDetailFragment.modelList.get(pos).getDescription());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(RecipeDetailFragment.modelList.get(pos).getShortDescription());

    }

    private void mExoPlayerViewInit() {
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
    }

    private void initializePlayer(String videoURL) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            LoadControl loadControl = new DefaultLoadControl();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);

            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mExoPlayerView.requestFocus();
            mExoPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(playWhenReady);

            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL), mediaDataSourceFactory, extractorsFactory, null, null);
            if (position != C.TIME_UNSET)
                mExoPlayer.seekTo(position);
            mExoPlayer.prepare(mediaSource);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        mVideoURL = RecipeDetailFragment.modelList.get(pos).getVideoURL();
        initializePlayer(mVideoURL);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(mVideoURL))
            initializePlayer(mVideoURL);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }
}
