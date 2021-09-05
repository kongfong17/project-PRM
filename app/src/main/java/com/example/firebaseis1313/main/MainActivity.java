package com.example.firebaseis1313.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.activity.DetailActivity;
import com.example.firebaseis1313.fragment.LoginFragment;
import com.example.firebaseis1313.fragment.HomeFragment;
import com.example.firebaseis1313.fragment.ListRoomFragment;
import com.example.firebaseis1313.fragment.MidmanFragment;
import com.example.firebaseis1313.fragment.Profilefragment;
import com.example.firebaseis1313.fragment.SavedFragment;
import com.example.firebaseis1313.fragment.SearchFragment;
import com.example.firebaseis1313.helper.OnFragmentInteractionListener;
import com.example.firebaseis1313.helper.ViewPageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    public static int REQUEST_CODE = 300;
    public static int RESULT_CODE = 400;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView textView;
    //Fragement
    private ListRoomFragment listRoomFragment;
    private SearchFragment searchFragment;
    private LoginFragment loginFragment;
    private SavedFragment savedFragment;
    private HomeFragment homeFragment;
    private Profilefragment profilefragment;
    private MidmanFragment midmanFragment;

    private int numberOfSaved;

    //dv
    private FirebaseFirestore db;




    @Override
    protected void onResume() {
        super.onResume();

//        SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);
//        String result = sharedPreferences.getString("destroy", null);
//        if(result !=null){
//            this.finish();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=FirebaseFirestore.getInstance();
        viewPager=findViewById(R.id.view_page);
        tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        // Khai báo Activity --------------
        listRoomFragment =new ListRoomFragment();
        searchFragment =new SearchFragment();
        loginFragment =new LoginFragment();
        savedFragment =new SavedFragment();
        homeFragment=new HomeFragment();
        profilefragment=new Profilefragment();
        midmanFragment=new MidmanFragment();

        //---------------------
        ///----
        db=FirebaseFirestore.getInstance();
        initMain();
    }

    public void initMain(){
        SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);
        final String user_id = sharedPreferences.getString("userId", null);
        String userName=sharedPreferences.getString("userName",null);
        String password=sharedPreferences.getString("userPassword",null);
        db.collection("User").whereEqualTo("username", userName).whereEqualTo("password", password).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() == 1) {
                        setTabLayout(1,user_id);
                    } else{
                        SharedPreferences.Editor editor = getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit();
                        editor.putBoolean("isLogin", false);
                        editor.putString("userId", "");
                        editor.putString("userAvatar","");
                        editor.putString("userDisplayName","");
                        editor.putString("userName","");
                        editor.putString("userPassword","");
                        editor.commit();
                        setTabLayout(2,user_id);
                    }
                } else {
                    setTabLayout(2,user_id);
                }
            }

        });
    }

// 1la dang nhap roi 2 la chua
    void  setTabLayout(int type,String user_id){
        ViewPageAdapter viewPageApdater =new ViewPageAdapter(getSupportFragmentManager(),0);
        // add to apdater
        viewPageApdater.addFragment(homeFragment,getString(R.string.home));
        viewPageApdater.addFragment(searchFragment,getString(R.string.search));
        Intent intent = getIntent();
        if(type==1){
            viewPageApdater.addFragment(profilefragment,getString(R.string.account));
            viewPageApdater.addFragment(savedFragment,getString(R.string.saved));
            setSavedRoom(user_id);
        }else if(type==2){
            viewPageApdater.addFragment(loginFragment, getString(R.string.account));
            viewPageApdater.addFragment(midmanFragment,getString(R.string.saved));
        }



        // add to tab_layout
        // Đối vs
        viewPager.setAdapter(viewPageApdater);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.search);
        tabLayout.getTabAt(2).setIcon(R.drawable.user);
        tabLayout.getTabAt(3).setIcon(R.drawable.apartment);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(
                                Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(
                        tabLayout.getWindowToken(), 0);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
               
            }
        });

        // set current page befor login
        int index=intent.getIntExtra("currentTab",0);
        setSelectedTab(index);
    }

    public void setSelectedTab(int index){
            tabLayout.getTabAt(index).select();
    }

    // set total of saved rooom
    public void setSavedRoom(String userId) {
        db.collection("User").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<String> list_room_Id=(ArrayList<String>)  task.getResult().get("listSaveRoom");
                    BadgeDrawable badgeDrawable = tabLayout.getTabAt(3).getOrCreateBadge();
                    if(list_room_Id.size()>0){


                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(list_room_Id.size());
                    }else{
                        badgeDrawable.setVisible(false);
                    }
                }
            }
        });
    }


    @Override
    public boolean isLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("isLogin", false);
        return result;
    }
}






