package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaplayer;
    AudioManager audioManager;

    public void playAudio(View view) {
        mediaplayer.start();
    }

    public void pauseAudio(View view) {
        mediaplayer.pause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mediaPlayer = (TextView) findViewById(R.id.MediaPlayer);
        mediaPlayer.setPaintFlags(mediaPlayer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        ImageView imageview = (ImageView)findViewById(R.id.headphones);
        imageview.setImageResource(R.drawable.headphones);

        mediaplayer = MediaPlayer.create(this, R.raw.sjlt);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int max_volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int cur_volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeTrack = (SeekBar)findViewById(R.id.volumeTrack);
        volumeTrack.setMax(max_volume);
        volumeTrack.setProgress(cur_volume);
        volumeTrack.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        volumeTrack.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        volumeTrack.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(audioManager.STREAM_MUSIC, progress, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar audioTrack = (SeekBar) findViewById(R.id.audioTrack);
        audioTrack.setMax(mediaplayer.getDuration());
        audioTrack.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        audioTrack.getThumb().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioTrack.setProgress(mediaplayer.getCurrentPosition());
            }
        } ,0, 1000 );

        audioTrack.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaplayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
