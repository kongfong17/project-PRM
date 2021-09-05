package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.firebaseis1313.main.MainActivity;
import com.example.firebaseis1313.R;
import com.example.firebaseis1313.helper.SlideAdapter;

public class OnBoradingActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    SlideAdapter sliderAdapter;
    TextView[] dots;
    Button getStarts;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_borading);
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        getStarts = findViewById(R.id.get_starts_btn);
        sliderAdapter = new SlideAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
        if (isOpenFirst()) {
            Intent intent = new Intent(OnBoradingActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("firstTime", MODE_PRIVATE).edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }

    private boolean isOpenFirst() {
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime", MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("firstTime", false);
        return result;
    }


    public void skip(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void next(View view) {
        viewPager.setCurrentItem(currentPos + 1);
    }

    public void starts(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void addDots(int position) {
        dots = new TextView[4];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;
            if (position == 0) {
                getStarts.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                getStarts.setVisibility(View.INVISIBLE);
            } else if (position == 2) {
                getStarts.setVisibility(View.INVISIBLE);
            } else {
                getStarts.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}