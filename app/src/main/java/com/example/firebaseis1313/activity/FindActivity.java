package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.fragment.SearchFragment;

import java.text.NumberFormat;
import java.util.Locale;

public class FindActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView txtOnclick;
    private TextView txtHeader;
    private TextView txtMin;
    private TextView txtMax;
    private TextView txtBottom;
    private Button btnApply;
    private Button btnCancel;
    String textValue="";
    int area ;
    int distance ;
    boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        seekBar = findViewById(R.id.seekBar2);
        txtOnclick = findViewById(R.id.txtOnclick);
        txtHeader = findViewById(R.id.txtHeader);
        txtMin = findViewById(R.id.txtMin);
        txtMax = findViewById(R.id.txtMax);
        txtBottom = findViewById(R.id.txtBottom);
        btnApply = findViewById(R.id.btnApply);
        btnCancel = findViewById(R.id.btnCancel);
        Intent intent = getIntent();
        final String type = intent.getStringExtra("type");
        //set on click for button "Hủy"
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Display type of search
        switch (type){
            case "2":
                //search for area
                area = intent.getIntExtra("area", 0);
                String text_area = intent.getStringExtra("textArea");
                seekBar.setProgress(area);
                txtOnclick.setText(text_area);
                int max = 50;
                txtHeader.setText("Diện tích");
                txtMin.setText("0 m²");
                txtMax.setText(max + " m²");
                seekBar.setMax(max);
                txtBottom.setText("Diện tích nhỏ hơn:");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        area = seekBar.getProgress();
                        textValue = String.valueOf(area)+ " m²";
                        txtOnclick.setText(textValue);
                        check= true;
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
            case "3":
                //search for Distance
                distance = intent.getIntExtra("distance", 0);
                String textDistance = intent.getStringExtra("textDistance");
                seekBar.setProgress(distance);
                txtOnclick.setText(textDistance);
                int max1 = 30;
                txtHeader.setText("Khoảng cách");
                txtMin.setText("0 km");
                txtMax.setText(max1 +" km");
                seekBar.setMax(max1);
                txtBottom.setText("Cách trường:");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        distance = seekBar.getProgress();
                        textValue = String.valueOf(distance)+ " km";
                        txtOnclick.setText(textValue);
                        check= true;
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
        }
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                SearchFragment searchFragment = new SearchFragment();
                if(check == false){
                    finish();
                }else {
                    myIntent.putExtra("check", check);
                    myIntent.putExtra("type", type);
                    myIntent.putExtra("area", area);
                    myIntent.putExtra("distance", distance);
                    myIntent.putExtra("textValue", textValue);
                    setResult(searchFragment.RESULT_CODE, myIntent);
                    finish();
                }
            }
        });
    }
}