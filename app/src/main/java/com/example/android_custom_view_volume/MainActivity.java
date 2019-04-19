package com.example.android_custom_view_volume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DialView gv=findViewById(R.id.dial_volume);

        gv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView tv=findViewById(R.id.text_values);
                tv.setText(gv.getValues());
                tv.invalidate();

            }
        });



    }
}
