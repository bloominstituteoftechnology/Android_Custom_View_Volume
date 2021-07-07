package com.example.volumeknob;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VolumeKnobView vkv;
        vkv = findViewById(R.id.volume_knob);
        vkv.setIndicatorColor(Color.CYAN);
        vkv.setKnobColor(Color.GRAY);

    }
}
