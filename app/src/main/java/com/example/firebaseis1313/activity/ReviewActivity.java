
package com.example.firebaseis1313.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.Review;
import com.example.firebaseis1313.entity.Room;
import com.example.firebaseis1313.entity.User;
import com.example.firebaseis1313.fragment.ListRoomFragment;
import com.example.firebaseis1313.helper.OnFragmentInteractionListener;
import com.example.firebaseis1313.helper.ReviewAdapter;
import com.example.firebaseis1313.helper.RoomViewAdapter;
import com.example.firebaseis1313.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewActivity extends AppCompatActivity {
    private ListView listViewComment;
    private ArrayList<Review> listReview;

    private FirebaseFirestore db;

    private RatingBar ratingBar_user;
    private TextView tvRoomTitle;
    private CircleImageView hostAvatar;
    private TextView userName;
    private RatingBar ratingBar_room;
    private EditText etComment;
    private CircleImageView userAvatar;
    private TextView star_text;

    private Button add;

    private String room_id;
    private String userId;
    private String room_image;
    float starOfRoom;

    //add adapter
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        MainActivity m=new MainActivity();

            starOfRoom=0;
            db=FirebaseFirestore.getInstance();
            listViewComment=findViewById(R.id.list_review);
            ratingBar_room=findViewById(R.id.ratingBar_room);
            tvRoomTitle=findViewById(R.id.tvRoomTitle);
            hostAvatar=findViewById(R.id.imgHostAvatar);
            ratingBar_user=findViewById(R.id.ratingBar_user);
            etComment=findViewById(R.id.etComment);
            userAvatar=findViewById(R.id.userAvatar);
            userName=findViewById(R.id.tvUserName);
            add=findViewById(R.id.btn_comment);
//        delete=findViewById(R.id.btn_delete);
            add=findViewById(R.id.btn_comment);
            //get user_id
            SharedPreferences sharedPreferences =getSharedPreferences("isLogin", MODE_PRIVATE);
            userId = sharedPreferences.getString("userId", null);
            Intent intent=getIntent();
            room_id=intent.getStringExtra("room_id");
            room_image=intent.getStringExtra("room_image");

            Picasso.get().load(room_image).into(hostAvatar);

            setRoomTitle(room_id);
            // add list to ListView and adapter
            listReview =new ArrayList<>();
            reviewAdapter=new ReviewAdapter(ReviewActivity.this,listReview);
//        ReviewActivity.
            listViewComment.setAdapter(reviewAdapter);
            ReviewActivity.setListViewHeightBasedOnItems(listViewComment);

            // set onClick to delete item where user_id =user_id
            listViewComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Review review=(Review) listViewComment.getItemAtPosition(position);
                    if(review.getUser().getId().equals(userId)){
                        clickToDelete(review.getId());
                    }
                }
            });
            add.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        etComment.setError(null);
                    }

                }
            });
            // set onclick for button comment to add comment
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean result=addOnclick();
                    if(result==true){
                        finish();
                        startActivity(getIntent());
                    }

                }
            });

            setUserProfile();
            setListReivew(room_id);

    }

    boolean addOnclick(){
        String content =etComment.getText().toString().trim();
        Float rate=ratingBar_user.getRating();
            if((content ==null || content.trim().length() <=0) && (rate <=0)){
                    etComment.setError("Please enter some comment or rating star");
                    return  false;
            }else{
                Date currentTime = Calendar.getInstance().getTime();
                CollectionReference reviewDAO = db.collection("Review");
                Map<String, Object> data = new HashMap<>();
                data.put("content",content);
                data.put("rate",rate);
                data.put("room_id",room_id);
                data.put("user_id",userId);
                data.put("createdTime",currentTime);
                reviewDAO.add(data);
                return  true;
            }
    }

    void setRoomTitle(String room_id){
        db.collection("Motel").document(room_id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    tvRoomTitle.setText(task.getResult().get("title").toString());
                }
            }
        });
    }

    // setListView without scroll
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;
        } else {
            return false;
        }

    }

    void setUserProfile(){
        SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("isLogin", false);
        if(result==true){
            String displayName=sharedPreferences.getString("userDisplayName",null);
            String userAvatarUrl=sharedPreferences.getString("userAvatar",null);
            Picasso.get().load(userAvatarUrl).into(userAvatar);
            userName.setText(displayName);
        }
    }

    // delete comment
    void deleteReviewComment(String reviewId){
        db.collection("Review").document(reviewId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(getApplicationContext(), "Delete complete", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    // add delete button and
    void clickToDelete(final String review_id){
        final String[] items = {"Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteReviewComment(review_id);
                    }
                });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setBackgroundColor(Color.BLACK);
        button.setPadding(0, 0, 20, 0);
        button.setTextColor(Color.WHITE);
    }

    void setListReivew(String room_id){
        db.collection("Review").whereEqualTo("room_id",room_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            float totalStar=0;
                            float totalReview=0;
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                final Review review=new Review();
                                final Map<String, Object> list = document.getData();
                                final String user_id=list.get("user_id").toString();
                                    totalStar+=Float.parseFloat(list.get("rate").toString());
                                    if(Float.parseFloat(list.get("rate").toString()) !=0){
                                        totalReview+=1;
                                    }
                                    review.setContent(list.get("content").toString());
                                    review.setRate(Float.parseFloat(list.get("rate").toString()));
                                    review.setId(document.getId());

                                    db.collection("User").document(list.get("user_id").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                User user = new User();
                                                user.setDisplayName(task.getResult().get("displayName").toString());
                                                user.setPhotoUrl(task.getResult().get("photoUrl").toString());
                                                user.setId(task.getResult().getId());
                                                review.setUser(user);
                                                listReview.add(review);
                                                ReviewActivity.setListViewHeightBasedOnItems(listViewComment);
                                                reviewAdapter.notifyDataSetChanged();
                                            }
                                        }

                                    });
                            }
                            if(totalReview==0){
                                ratingBar_room.setVisibility(View.GONE);
                                star_text=findViewById(R.id.star_text);
                                star_text.setText(getString(R.string.none_rate));

                            }else{
                                ratingBar_room.setRating(totalStar/totalReview);
                            }
                        } else {
                        }
                    }
                });
    }

}