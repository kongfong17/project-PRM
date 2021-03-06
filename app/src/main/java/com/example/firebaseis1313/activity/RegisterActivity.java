    package com.example.firebaseis1313.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.firebaseis1313.R;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

    public class  RegisterActivity extends AppCompatActivity {

        public static String md5(String str){
            String result = "";
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
                digest.update(str.getBytes());
                BigInteger bigInteger = new BigInteger(1,digest.digest());
                result = bigInteger.toString(16);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return result;
        }

    private Button btnRegister;
    private EditText etAccountR;
    private EditText etPasswordR;
    private EditText etPhoneR;
    private EditText etDisplayName;
    private EditText etEmailR;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnUpdate);
        etAccountR = findViewById(R.id.etAccountP);
        etPasswordR = findViewById(R.id.etPasswordR);
        etPhoneR = findViewById(R.id.etPhoneP);
        etDisplayName = findViewById(R.id.etDisplayNameR);
        etEmailR = findViewById(R.id.etEmailP);
        firebaseFirestore = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etAccountR.getText().toString();
              firebaseFirestore.collection("User").whereEqualTo("username",username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().size() == 1){
                                Toast.makeText(getApplicationContext(), "T??i kho???n ???? t???n t???i", Toast.LENGTH_SHORT).show();
                            }else{
                                Map<String, Object> user = new HashMap<>();
                                if (validateDisplayName(etDisplayName) && validateUsername(etAccountR) && validatePassword(etPasswordR) && validatePhone(etPhoneR) && validateEmail(etEmailR)) {
                                    user.put("displayName", etDisplayName.getText().toString());
                                    user.put("username",  etAccountR.getText().toString());
                                    user.put("password", md5(etPasswordR.getText().toString()));
                                    user.put("phone", etPhoneR.getText().toString());
                                    user.put("email", etEmailR.getText().toString());
                                    user.put("listSaveRoom", new ArrayList<>());
                                    user.put("photoUrl", "https://firebasestorage.googleapis.com/v0/b/is1313mk.appspot.com/o/user%20image%2Fmale.jpg?alt=media&token=a20ef42d-6747-433f-a819-fcd31e2da093");
                                    firebaseFirestore.collection("User").add(user);
                                    Toast.makeText(getApplicationContext(), "????ng k?? th??nh c??ng!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Th??ng tin kh??ng h???p l???!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                  }
              });
            }
        });
    }

    private boolean validateDisplayName(EditText dpName){
        if(dpName.getText().toString().isEmpty()){
            dpName.setError("Kh??ng ???????c ????? tr???ng!");
            return false;
        }else {
            dpName.setError(null);
            return true;
        }
    }

    public boolean validateUsername(EditText username){
        String regex ="\\A\\w{4,20}\\z";
        if(username.getText().toString().isEmpty()){
            username.setError("Kh??ng ???????c ????? tr???ng!");
            return false;
        }
        else if(username.getText().toString().length() <= 4 || username.getText().toString().length() >= 13){
            username.setError("T??i kho???n ph???i c?? ??t nh???t 4-13 k?? t???!");
            return false;
        }else if(!username.getText().toString().matches(regex)){
            username.setError("Kh??ng ???????c ch???a d???u c??ch!");
            return false;
        }
        else {
            username.setError(null);
            return true;
        }
    }

    public boolean validatePassword(EditText password){
        if(password.getText().toString().isEmpty()){
            password.setError("Kh??ng ???????c ????? tr???ng!");
            return false;
        }else if(password.getText().toString().length() <= 4  || password.getText().toString().length() > 13){
            password.setError("T??i kho???n ph???i c?? ??t nh???t 4-13 k?? t???!");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

    public boolean validatePhone(EditText phone){
        if(phone.getText().toString().isEmpty()){
            phone.setError("Kh??ng ???????c ????? tr???ng!");
            return false;
        }else if(!phone.getText().toString().matches("[0-9]+")){
            phone.setError("S??? ??i???n tho???i kh??ng ???????c c?? ch???!");
            return false;
        }
        else if(phone.getText().toString().length() != 10){
            phone.setError("S??? ??i???n tho???i ph???i c?? 10 s???!");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    public boolean validateEmail(EditText email){
        String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email.getText().toString().isEmpty()){
            email.setError("Kh??ng ???????c ????? tr???ng!");
            return false;
        }
        else if(!email.getText().toString().matches(regex)){
            email.setError("?????a ch??? email kh??ng h???p l???!");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    public void back(View view) {
        finish();
    }
}