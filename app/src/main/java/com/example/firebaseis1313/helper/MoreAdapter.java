package com.example.firebaseis1313.helper;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.firebaseis1313.R;

import android.widget.TextView;

import com.example.firebaseis1313.entity.More;

import java.util.List;

public class MoreAdapter extends BaseAdapter {
    private Activity activity;
    private List<More> moreList;

    public MoreAdapter(Activity activity, List<More> moreList) {
        this.activity = activity;
        this.moreList = moreList;
    }

    @Override
    public int getCount() {
        return moreList.size();
    }

    @Override
    public Object getItem(int position) {
        return moreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return moreList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView textView;

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.more_layout, null);

            imageView = convertView.findViewById(R.id.imageView);
            textView = convertView.findViewById(R.id.textView);

            convertView.setTag(R.id.imageView, imageView);
            convertView.setTag(R.id.textView, textView);
        } else {
            imageView = (ImageView) convertView.getTag(R.id.imageView);
            textView = (TextView) convertView.getTag(R.id.textView);
        }

        More more = moreList.get(position);
        textView.setText(more.getName());

        switch (more.getId()) {
            case 1:
                imageView.setImageResource(R.drawable.ic_baseline_call);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_baseline_message);
                break;
            case 3:
                imageView.setImageResource(R.drawable.ic_baseline_location_on);
                break;
        }

        return convertView;
    }
}
