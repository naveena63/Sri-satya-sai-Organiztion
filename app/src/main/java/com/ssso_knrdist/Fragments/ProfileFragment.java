package com.ssso_knrdist.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ssso_knrdist.Activties.LoginActivity;
import com.ssso_knrdist.R;
import com.ssso_knrdist.Utils.PrefManager;


public class ProfileFragment extends Fragment {

    View view;
    PrefManager prefManager;
    TextView userName, mobileNumber, mailTv;
    Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.username);
        mobileNumber = view.findViewById(R.id.mobileTv);
        mailTv = view.findViewById(R.id.emailTv);
        logout = view.findViewById(R.id.logout);

        prefManager = new PrefManager(getContext());

        userName.setText(prefManager.getUsername());
        mobileNumber.setText(prefManager.getPhoneNumber());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}