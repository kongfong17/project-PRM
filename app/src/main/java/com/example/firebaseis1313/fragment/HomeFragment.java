package com.example.firebaseis1313.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.activity.DetailActivity;
import com.example.firebaseis1313.entity.Home;
import com.example.firebaseis1313.entity.Image;
import com.example.firebaseis1313.entity.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db;
    private ArrayList<Room> list_room_order_by_date;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void setListRoom(final View view) {
//        final ArrayList<Room> listRoom =new ArrayList<>();
        final int[] position = {0};
        db.collection("Motel")
                .orderBy("price", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(final QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()){
                            final int sizeOfMotel=documentSnapshots.size();
                            for (final DocumentSnapshot document : documentSnapshots.getDocuments()) {
                                final Map<String, Object> list = document.getData();
                                final Room e = new Room();
                                e.setId(document.getId());
                                e.setPrice(Float.parseFloat(list.get("price").toString()));
                                e.setArea(Float.parseFloat(list.get("area").toString()));
                                e.setDescription(list.get("title").toString());
                                e.setPrice(Float.parseFloat(list.get("price").toString()));
                                Timestamp timestamp= (Timestamp) list.get("createdTime");
                                e.setCreatedTime(timestamp.getSeconds());


                                String image_id=list.get("image_id").toString();

                                db.collection("Image").document(image_id)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if(!documentSnapshots.isEmpty()){
                                                    ArrayList<String> listImageUrl=(ArrayList<String>)documentSnapshot.getData().get("url");
                                                    Image image =new Image();
                                                    image.setId(list.get("image_id").toString());
                                                    image.setListImageUrl(listImageUrl);
                                                    e.setImage(image);
                                                    final Home home =new Home();
                                                    home.setId(list.get("home_id").toString());
                                                    db.collection("Home").document(list.get("home_id").toString())
                                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            if(!documentSnapshots.isEmpty()){
                                                                home.setAddress(documentSnapshot.getData().get("address").toString());
                                                                e.setHome(home);
                                                                position[0] = position[0] +1;
                                                                ListRoomFragment listRoomFragment = (ListRoomFragment) getChildFragmentManager().findFragmentById(R.id.list_room_frag);
                                                                if(position[0]==sizeOfMotel){
                                                                    listRoomFragment.receiveData(e,0);
                                                                }else{
                                                                    listRoomFragment.receiveData(e,-1);

                                                                }
//                                                                System.out.println(e.getPrice());
//                                                                listRoom.add(e);
                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        });
                            }
                        }
                    }
                });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       
        Intent mainIntent=getActivity().getIntent();
        String room_id=mainIntent.getStringExtra("room_id");
        int current_tab=mainIntent.getIntExtra("currentTab",0);
        String messFromLogin=mainIntent.getStringExtra("mess_from_login");
        setListRoom(view);

        if(messFromLogin !=null && messFromLogin.equals("review") && current_tab==0){
            Intent new_intent = new Intent(getContext(), DetailActivity.class);
            new_intent.putExtra("room_id", room_id);
            new_intent.putExtra("mess_from_list","review");
            mainIntent.removeExtra("mess_from_login");
            startActivity(new_intent);
        }else if(messFromLogin !=null && messFromLogin.equals("saveWithoutLogin") && current_tab==0){
            Intent new_intent = new Intent(getContext(), DetailActivity.class);
            new_intent.putExtra("room_id", room_id);
            new_intent.putExtra("mess_from_list","saveWithoutLogin");
            mainIntent.removeExtra("mess_from_login");
            startActivity(new_intent);
        }
        super.onViewCreated(view, savedInstanceState);

    }
}