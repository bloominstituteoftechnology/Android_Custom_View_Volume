package com.jakeesveld.android_custom_view_volume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    VolumeKnob volumeKnob;
    static TextView volumeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volumeKnob = findViewById(R.id.volume_knob);
        volumeText = findViewById(R.id.text_volume);

    }

    public static void setVolumeText(int volume){
        volumeText.setText("Volume: " + volume + "%");
    }
}
