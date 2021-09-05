package com.example.firebaseis1313.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.helper.OnFragmentInteractionListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MidmanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MidmanFragment extends Fragment {

    private OnFragmentInteractionListener onFragmentInteractionListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MidmanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MidmanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MidmanFragment newInstance(String param1, String param2) {
        MidmanFragment fragment = new MidmanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        onFragmentInteractionListener=(OnFragmentInteractionListener)getActivity();
        super.onAttach(context);
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

        return inflater.inflate(R.layout.fragment_midman, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoginFragment loginFragment=new LoginFragment();
        SavedFragment savedFragment=new SavedFragment();


//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("isLogin", MODE_PRIVATE);
//        String result = sharedPreferences.getString("OkIamIn",null);
//
//        if(onFragmentInteractionListener.isLogin() && result.equals("Join")){
//            SharedPreferences.Editor editor =getContext().getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit();
//            editor.putString("OkIamIn", "Joined");
//            editor.commit();
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.mid_man_frag, savedFragment)
//                    .commit();
//        }else
            if(onFragmentInteractionListener.isLogin()==false){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mid_man_frag, loginFragment)
                    .commit();
        }

    }
}