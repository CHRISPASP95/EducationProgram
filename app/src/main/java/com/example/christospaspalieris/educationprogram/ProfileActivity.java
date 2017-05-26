package com.example.christospaspalieris.educationprogram;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;

    private EditText firstname;
    private EditText lastname;
    private EditText oldpass;
    private EditText newpass;
    private EditText age;

    private ToggleButton resetpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("USERS");

        firstname = (EditText) findViewById(R.id.ETfirstName);
        lastname = (EditText) findViewById(R.id.ETlastName);
        oldpass = (EditText) findViewById(R.id.EToldPass);
        newpass = (EditText) findViewById(R.id.ETnewPass);
        age = (EditText) findViewById(R.id.ETage);
        resetpass = (ToggleButton) findViewById(R.id.resetpassButton);


        resetpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    oldpass.setVisibility(View.VISIBLE);
                    newpass.setVisibility(View.VISIBLE);
                }
                else
                {
                    oldpass.setVisibility(View.GONE);
                    newpass.setVisibility(View.GONE);
                    oldpass.setText("");
                    newpass.setText("");
                }
            }
        });

    }



}
