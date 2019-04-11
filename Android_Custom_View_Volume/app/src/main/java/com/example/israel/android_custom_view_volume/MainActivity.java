package com.example.israel.android_custom_view_volume;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements VolumeControlFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // the fragment is loaded when the activity inflates the layout on setContentView
        // I did it like this because I don't need to pass any parameters to VolumeControlFragment

        TextView volumeLevelTextView = findViewById(R.id.activity_main_text_view_volume_level);
        volumeLevelTextView.setAlpha(0.f);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void showVolumeLevel(float volumeLevel) {
        TextView volumeLevelTextView = findViewById(R.id.activity_main_text_view_volume_level);
        if (volumeLevelTextView == null) {
            return;
        }
        volumeLevelTextView.setText(Integer.toString((int)volumeLevel) + "%");
        volumeLevelTextView.setAlpha(1.f);
        // just for fun
        // mimics a toaster
        volumeLevelTextView.animate()
                .setDuration(500)
                .alpha(0.f)
                .start();
    }
}
