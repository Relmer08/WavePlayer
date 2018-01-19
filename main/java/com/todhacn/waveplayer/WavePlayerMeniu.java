package com.todhacn.waveplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WavePlayerMeniu extends AppCompatActivity {

    private static final int My_permission_request = 1;
    ArrayList<String> arrayList;
    ArrayList<Long> arrayListLoc;
    ListView listView;
    ArrayAdapter<String> adapter;
    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_player_meniu);

        if (ContextCompat.checkSelfPermission(WavePlayerMeniu.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(WavePlayerMeniu.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, My_permission_request);
        else
            baga_codu();
    }


    public void baga_codu() {
        listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        arrayListLoc=new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int i, long j) {
                Intent intent = new Intent(WavePlayerMeniu.this, WavePlayer.class);
                Uri songURI= ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,arrayListLoc.get(i));
                intent.putExtra("songURI", songURI.toString());
                //startActivity(intent);
                overridePendingTransition(R.anim.transition_lista_muzica1, R.anim.transition_lista_muzica2);
            }
        });
    }

    public void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            long songID=songCursor.getColumnIndex(MediaStore.Audio.Media._ID);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentLocation=songCursor.getString(songLocation);
                //songURI= ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,songID);
                arrayList.add(currentTitle+"\n"+currentLocation);
                arrayListLoc.add(index,songID);
            } while (songCursor.moveToNext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case My_permission_request:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(WavePlayerMeniu.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                        baga_codu();
                    } else {
                        Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }


        }
    }


}
