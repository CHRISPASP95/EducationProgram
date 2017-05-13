package com.example.christospaspalieris.educationprogram;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EducationalProgramActivity extends ActionBarActivity  {

    private Toolbar toolbar;
    private FirebaseAuth userAyth;
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_program);

        toolbar =(Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        userAyth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference("USERS").child(userAyth.getCurrentUser().getUid());
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    UserInformation userInformation = snapshot.getValue(UserInformation.class);
                    getSupportActionBar().setTitle("Welcome " + userInformation.getUsername());
                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
    }
}
