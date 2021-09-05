package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.firebaseis1313.R;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
    private Button btnExit;
    CarouselView carouselView;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        carouselView = findViewById(R.id.carouselView);
        btnExit = findViewById(R.id.btnExit);

        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        int index = intent.getIntExtra("imageIndex", 0);

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(images.get(position)).into(imageView);
            }
        };
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(images.size());
        carouselView.setCurrentItem(index);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                setResult(100, myIntent);
                finish();
            }
        });
    }

}