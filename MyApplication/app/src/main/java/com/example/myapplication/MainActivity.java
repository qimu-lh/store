package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.isShown()) {
                    imageView.setVisibility(View.INVISIBLE);
                    button.setText(R.string.DisplayPicture);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    button.setText(R.string.HidePicture);
                }
            }
        });
    }
}
