package com.example.firebaseis1313.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.activity.DetailActivity;
import com.example.firebaseis1313.activity.RegisterActivity;
import com.example.firebaseis1313.entity.User;
import com.example.firebaseis1313.helper.OnFragmentInteractionListener;
import com.example.firebaseis1313.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profilefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profilefragment extends Fragment {
    private EditText displayName;
    private EditText phone;
    private EditText email;
    private EditText username;
    private Button btnUpdate;
    private TextView tvDisplayName;
    private String _DisplayName, _Phone, _Email;
    private CircleImageView avatar;
    private FirebaseFirestore db;
    private DatabaseReference reference;
    private RegisterActivity registerActivity;


    private OnFragmentInteractionListener onFragmentInteractionListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnLogout;
    private Button btnTest;

    public Profilefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profilefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profilefragment newInstance(String param1, String param2) {
        Profilefragment fragment = new Profilefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_profilefragment, container, false);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onFragmentInteractionListener=(OnFragmentInteractionListener)getActivity();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        btnLogout = view.findViewById(R.id.btnLogout);
        db=FirebaseFirestore.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("User");
        registerActivity = new RegisterActivity();
        displayName=view.findViewById(R.id.etDisplayP);
        phone=view.findViewById(R.id.etPhoneP);
        email=view.findViewById(R.id.etEmailP);
        username = view.findViewById(R.id.etAccountP);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        tvDisplayName = view.findViewById(R.id.tvDisplayName);
        avatar=view.findViewById(R.id.imgHostAvatar);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("isLogin", MODE_PRIVATE);
        final String result = sharedPreferences.getString("userId", null);
        username.setFocusable(false);
       


        if(onFragmentInteractionListener.isLogin()){
            setProfile(result);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update(result);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = view.getRootView().getContext().getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit();
                editor.putBoolean("isLogin", false);
                editor.putString("userId", "");
                editor.putString("userPassword", "");
                editor.putString("userAvatar", "");
                editor.putString("userDisplayName", "");
                editor.putString("userName", "");
                editor.commit();
                getActivity().finish();
                Intent intent = new Intent(view.getRootView().getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }


    public void setProfile(String userId){
        db.collection("User").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    final Map<String, Object> list = task.getResult().getData();
                    displayName.setText(list.get("displayName").toString());
                    _DisplayName = list.get("displayName").toString();
                    phone.setText(list.get("phone").toString());
                    _Phone = list.get("phone").toString();
                    email.setText(list.get("email").toString());
                    _Email = list.get("email").toString();
                    username.setText(list.get("username").toString());
                    tvDisplayName.setText(list.get("displayName").toString().replaceAll(" ", "_"));
                    Picasso.get().load(list.get("photoUrl").toString()).into(avatar);
                }else{
                    Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Update(String userId){
        final DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("User").document(userId);
        boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        if(isChangeDisplayName()){
            map.put("displayName", displayName.getText().toString());
            flag = true;
        }
        if(isChangeEmail()){
            map.put("email", email.getText().toString());
            flag = true;
        }
        if(isChangePhone()){
            map.put("phone", phone.getText().toString());
            flag = true;
        }

        if(!flag){
            Toast.makeText(getContext(), getString(R.string.update_mess_faiil), Toast.LENGTH_SHORT).show();
        }else {

            docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), getString(R.string.update_mess_sucess), Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Nothing", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private boolean isChangeDisplayName(){
        if(!_DisplayName.equals(displayName.getText().toString())){
            tvDisplayName.setText(displayName.getText().toString().replace(" ","_"));
            _DisplayName = displayName.getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isChangeEmail(){
        if(!_Email.equals(email.getText().toString()) && registerActivity.validateEmail(email)){
            _Email = email.getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isChangePhone(){
        if(!_Phone.equals(phone.getText().toString()) && registerActivity.validatePhone(phone)){
            _Phone = phone.getText().toString();
            return true;
        }else{
            return false;
        }
    }

}