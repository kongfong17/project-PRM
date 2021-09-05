package com.example.firebaseis1313.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.activity.DetailActivity;
import com.example.firebaseis1313.activity.LoginActivity;
import com.example.firebaseis1313.entity.Room;
import com.example.firebaseis1313.helper.OnFragmentInteractionListener;
import com.example.firebaseis1313.helper.RoomViewAdapter;
import com.example.firebaseis1313.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.firebaseis1313.main.MainActivity.REQUEST_CODE;
import static com.example.firebaseis1313.main.MainActivity.RESULT_CODE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListRoomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TabLayout tabLayout;
    private RoomViewAdapter room_view_apdapter;
    private ListView list_view_room;
    private ArrayList<Room> list_room;

    private FirebaseFirestore db;

    private int room_index;

    private OnFragmentInteractionListener onFragmentInteractionListener;


    int Querytype = -1;

    // quy dinh 0 : home 1 search 2 dien tich 3 khoang cach 4 saved
    public ListRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListRoomFragment newInstance(String param1, String param2) {
        ListRoomFragment fragment = new ListRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onFragmentInteractionListener=(OnFragmentInteractionListener)getActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(list_view_room.getSelectedItem());
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            boolean isSaved =data.getBooleanExtra("isChange",false);
            String userId=data.getStringExtra("userId");
            if(isSaved==true){
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
                Fragment frg = null;
                frg = getParentFragment();

                if(frg.getView().getRootView().findViewById(R.id.saveFragment) != null){
                   final FragmentTransaction ft = frg.getFragmentManager().beginTransaction();
                   ft.detach(frg);
                   ft.attach(frg);
                   ft.commit();
                }
            }

        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_room, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db= FirebaseFirestore.getInstance();
        list_room = new ArrayList<>();
        list_view_room = view.findViewById(R.id.list_room);
        room_view_apdapter = new RoomViewAdapter(getActivity(), list_room);
        list_view_room.setAdapter(room_view_apdapter);
        list_view_room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                room_index =list_view_room.getSelectedItemPosition();
                MainActivity m=new MainActivity();
                String room_id = list_room.get(i).getId();
                Intent intent = new Intent(view.getRootView().getContext(), DetailActivity.class);
                intent.putExtra("room_id",room_id);
                intent.putExtra("room_image",list_room.get(i).getImage().getListImageUrl().get(0));
                tabLayout =view.getRootView().findViewById(R.id.tab_layout);
                // serach
                if(tabLayout.getSelectedTabPosition()==1 && onFragmentInteractionListener.isLogin()==false){
                    Button price=view.getRootView().findViewById(R.id.btnPrice);
                    Button area =view.getRootView().findViewById(R.id.btnArea);
                    Button distance=view.getRootView().findViewById(R.id.btnDistance);
                    SearchFragment searchFragment=(SearchFragment) getParentFragment();
                    intent.putExtra("minPrice",searchFragment.minPrice);
                    intent.putExtra("maxPrice",searchFragment.maxPrice);
                    intent.putExtra("area",searchFragment.area);
                    intent.putExtra("distance",searchFragment.distance);

                    intent.putExtra("textPrice",searchFragment.text_price);
                    intent.putExtra("textArea",searchFragment.text_area);
                    intent.putExtra("textDistance",searchFragment.text_distance);

                }
                intent.putExtra("indexOfCurrentTab",tabLayout.getSelectedTabPosition());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void clearData() {
        list_room.clear();
        room_view_apdapter.notifyDataSetChanged();
    }

    public void orderByDate(ArrayList<Room> list_room){
        Collections.sort(list_room, new Comparator<Room>() {
            @Override
            public int compare(Room o1, Room o2) {
                return (int) (o2.getCreatedTime()-o1.getCreatedTime());
            }
        });
    }
    public void receiveData(Room room,int position) {
        list_room.add(room);
        if(position==0){
            orderByDate(list_room);
            room_view_apdapter.notifyDataSetChanged();
        }else if(position==3){
            orderByDate(list_room);
            room_view_apdapter.notifyDataSetChanged();
        }else if(position==1){
            room_view_apdapter.notifyDataSetChanged();
        }
//        else if(position==4){
//            //Thay cai orderByDate
//            // Muon order cai gi thi ben search phai lay ve
//            orderByDate(list_room);
//            room_view_apdapter.notifyDataSetChanged();
//            // viet ham sort ngoai o day
//        }else if(position==5){
//            orderByDate(list_room);
//            room_view_apdapter.notifyDataSetChanged();
//            // viet ham sort ngoai o day
//        }else if(position==6){
//            orderByDate(list_room);
//            room_view_apdapter.notifyDataSetChanged();
//            // viet ham sort ngoai o day
//        }else if(position==7){
//            orderByDate(list_room);
//            room_view_apdapter.notifyDataSetChanged();
//            // viet ham sort ngoai o day
//        }

    }
    public boolean isEmpty(){
        if(list_room.isEmpty()) {
            return true;
        }
        return false;
    }
}