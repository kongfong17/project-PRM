package com.example.firebaseis1313.helper;


import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.Room;
import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RoomViewAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Room> listRoom;

    public RoomViewAdapter(Activity activity, ArrayList<Room> listRoom) {
        this.activity = activity;
        this.listRoom = listRoom;
    }

    @Override
    public int getCount() {
        return listRoom.size();
    }

    @Override
    public Object getItem(int position) {
        return listRoom.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public String getItemIdByMe(int position){
        return listRoom.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView descresption;
        TextView price;
        TextView addrress;
        TextView tv_createTime;
        ConstraintLayout constraintLayout;
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.room_view, null);
            imageView = convertView.findViewById(R.id.imageView);
            constraintLayout=convertView.findViewById(R.id.viewDes);
//            descresption=constraintLayout.findViewById(R.id.tvDes);
//            price=constraintLayout.findViewById(R.id.tvPrice);
            convertView.setTag(R.id.imageView, imageView);
            convertView.setTag(R.id.viewDes,constraintLayout);

        }
        else{
            imageView = (ImageView) convertView.getTag(R.id.imageView);
            constraintLayout = (ConstraintLayout) convertView.getTag(R.id.viewDes);
        }
        descresption=constraintLayout.findViewById(R.id.tvDes);
        price=constraintLayout.findViewById(R.id.tvPrice);
        addrress=constraintLayout.findViewById(R.id.tvAddress);
        tv_createTime=constraintLayout.findViewById(R.id.tv_createTime);
        Room room =listRoom.get(position);
        descresption.setText("\t"+room.getDescription());
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String formatMoneyMin = currencyVN.format(room.getPrice());
        price.setText(formatMoneyMin);
        addrress.setText(room.getHome().getAddress());

        // set create Date
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(room.getCreatedTime() * 1000L);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        tv_createTime.setText(date);


        Picasso.get().load(room.getImage().getListImageUrl().get(0)).into(imageView);
        return convertView;
    }
}
