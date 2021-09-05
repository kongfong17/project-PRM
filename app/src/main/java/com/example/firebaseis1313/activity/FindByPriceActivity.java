package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.firebaseis1313.R;
import com.example.firebaseis1313.fragment.SearchFragment;
import org.florescu.android.rangeseekbar.RangeSeekBar;
import java.text.NumberFormat;
import java.util.Locale;

public class FindByPriceActivity extends AppCompatActivity {
    RangeSeekBar rangeSeekBar;
    private TextView txtOnclick;
    private TextView txtHeader;
    private TextView txtMin;
    private TextView txtMax;
    private TextView txtBottom;
    private Button btnApply;
    private Button btnCancel;
    int maxValue ;
    int minValue ;
    int min;
    int max;
    String textValue="";
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_price);
        rangeSeekBar = findViewById(R.id.rangeSeekBar);
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
        switch (type) {
            case "1":
                //search for Price
                minValue = intent.getIntExtra("min", 0);
                maxValue = intent.getIntExtra("max", 6000000);
                String textPrice = intent.getStringExtra("textPrice");
                txtOnclick.setText(textPrice);
                rangeSeekBar.setRangeValues(0,6000000);
                rangeSeekBar.setSelectedMinValue(minValue);
                rangeSeekBar.setSelectedMaxValue(maxValue);
                txtHeader.setText("Chọn Khoảng Giá");
                txtMin.setText(currencyVN.format(0));
                txtMax.setText(currencyVN.format(6000000));
                txtBottom.setText("Khoảng:");
                rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                    @Override
                    public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                        Number min_value = rangeSeekBar.getSelectedMinValue();
                        Number max_value = rangeSeekBar.getSelectedMaxValue();
                         min =(int) min_value;
                         max =(int) max_value;
                        min = min / 100000;
                        min = min * 100000;
                        max = max / 100000;
                        max = max * 100000;
                        String formatMoneyMin = currencyVN.format(min);
                        String formatMoneyMax = currencyVN.format(max);
                        textValue = formatMoneyMin+" - "+formatMoneyMax;
                        txtOnclick.setText(textValue);
                        check= true;
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
                    myIntent.putExtra("textValue", textValue);
                    myIntent.putExtra("min", min);
                    myIntent.putExtra("max", max);
                    setResult(searchFragment.RESULT_CODE, myIntent);
                    finish();
                }
            }
        });
    }

}