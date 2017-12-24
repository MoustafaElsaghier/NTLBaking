package elsaghier.example.com.ntlbaking.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
    public static final String ARG_ITEM_ID = "item_id";


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
    private DataSource.Factory mediaDataSourceFactory;

    final String SELECTED_POSITION = "videoTime";

    public static int pos;
    long position;

    public StepDetailsFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_POSITION, position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        position = C.TIME_UNSET;
        View v = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, v);
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
        }
        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
            mVideoURL = RecipeDetailFragment.list.get(pos).getVideoURL();
            mDescription.setText(RecipeDetailFragment.list.get(pos).getDescription());

            // Next icon click
            mNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pos = ((++pos) % RecipeDetailFragment.list.size());
                    mVideoURL = RecipeDetailFragment.list.get(pos).getVideoURL();
                    mDescription.setText(RecipeDetailFragment.list.get(pos).getDescription());
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(RecipeDetailFragment.list.get(pos).getShortDescription());
                    releasePlayer();
                    initializePlayer(mVideoURL);
                }
            });
            // previous icon click
            mPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    pos = ((--pos) % RecipeDetailFragment.list.size()) + 1;
                    mVideoURL = RecipeDetailFragment.list.get(pos).getVideoURL();
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(RecipeDetailFragment.list.get(pos).getShortDescription());
                    mDescription.setText(RecipeDetailFragment.list.get(pos).getDescription());
                    releasePlayer();
                    initializePlayer(mVideoURL);
                }
            });
            mExoPlayerViewInit();
        }
        return v;
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
            mExoPlayer.setPlayWhenReady(true);

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
        mVideoURL = RecipeDetailFragment.list.get(pos).getVideoURL();
        initializePlayer(mVideoURL);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mVideoURL != null)
            initializePlayer(mVideoURL);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            releasePlayer();
        }

    }

}
