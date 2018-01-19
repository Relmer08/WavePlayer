package com.todhacn.waveplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;

public class WavePlayer extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_player);
        Button play = (Button) findViewById(R.id.button_play);
        Button pause = (Button) findViewById(R.id.button_pause);
        Button stop = (Button) findViewById(R.id.button_stop);
        Button back = (Button) findViewById(R.id.button_back_to_the_future);
        final MediaPlayer mp=new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Uri Location= Uri.parse(getIntent().getStringExtra("songURI"));
        Toast.makeText(this, (CharSequence) Location, Toast.LENGTH_SHORT).show();
        try {
            mp.setDataSource(getApplicationContext(),Location);
        } catch (IOException e) {
            e.printStackTrace();
        }
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.reset();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.release();
                Intent i2 = new Intent(WavePlayer.this, WavePlayerMeniu.class);
                startActivity(i2);
                overridePendingTransition(R.anim.transition_muzica_lista1, R.anim.transition_muzica_lista2);

            }
        });
    }
}
