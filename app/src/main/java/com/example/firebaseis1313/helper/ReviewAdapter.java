package com.example.firebaseis1313.helper;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.Review;
import com.example.firebaseis1313.entity.Room;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Review> listReview;

    public ReviewAdapter(Activity activity, ArrayList<Review> listReview) {
        this.activity = activity;
        this.listReview = listReview;
    }

    @Override
    public int getCount() {
        return listReview.size();
    }

    @Override
    public Object getItem(int position) {
        return listReview.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CircleImageView imageView;

        ConstraintLayout constraintLayout;
        TextView userName;
        TextView comment;

        RatingBar other_rating;
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.comment_view, null);
            imageView = convertView.findViewById(R.id.userAvatar);
            constraintLayout=convertView.findViewById(R.id.comment_layout);
            convertView.setTag(R.id.userAvatar, imageView);
            convertView.setTag(R.id.comment_layout,constraintLayout);

        }
        else{
            imageView = (CircleImageView) convertView.getTag(R.id.userAvatar);
            constraintLayout = (ConstraintLayout) convertView.getTag(R.id.comment_layout);
        }
        userName=constraintLayout.findViewById(R.id.tvUserNameComment);
        comment=constraintLayout.findViewById(R.id.tvOtherComment);
        other_rating=constraintLayout.findViewById(R.id.other_rating);



        Review review=listReview.get(position);
        userName.setText(review.getUser().getDisplayName());
        comment.setText(review.getContent());
        other_rating.setRating(review.getRate());
        Picasso.get().load(review.getUser().getPhotoUrl()).into(imageView);

        return convertView;
    }
}
