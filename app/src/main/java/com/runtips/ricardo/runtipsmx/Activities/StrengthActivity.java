package com.runtips.ricardo.runtipsmx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.view.ActionMode;
import android.widget.Toast;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;
import com.runtips.ricardo.runtipsmx.Classes.PlayerConfig;
import com.runtips.ricardo.runtipsmx.R;

public class StrengthActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,
    YouTubePlayer.PlaybackEventListener, YouTubePlayer.PlayerStateChangeListener, AppCompatCallback {

    YouTubePlayerView youTubePlayerStrength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength);

        youTubePlayerStrength = findViewById(R.id.strengthPlayer);
        youTubePlayerStrength.initialize(PlayerConfig.API_KEY, this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);
        if(!wasRestored){
            youTubePlayer.cueVideo("jUWgbTLSlPQ");
            youTubePlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError())
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        else{
            String error =  "Error al iniciar YouTube" + youTubeInitializationResult.toString();
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == 1){
            getYouTubePlayerProvider().initialize(PlayerConfig.API_KEY, this);

        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider(){
        return youTubePlayerStrength;
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
