package com.example.christospaspalieris.educationprogram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EducationalProgramActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference dbReference;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_program);

        String[] foods = {"Δεκαδικοί", "Κλάσματα", "Μιγαδικοί", "Πρόσθεση", "Θεωρία της σχετικότητας","Κβαντομηχανική", "Ρευστά","Ο κόσμος μου"};
        ListAdapter adapter = new CustomAdapter(this,foods);
        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);


        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setIcon(R.drawable.ruler_pencil);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //toolbar =(Toolbar) findViewById(R.id.my_toolbar);
       // setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    Intent logregIntentt = new Intent(getApplicationContext(),LoginRegisterActivity.class);
                    logregIntentt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(logregIntentt);
                }
            }
        };

        dbReference = FirebaseDatabase.getInstance().getReference("USERS").child(mAuth.getCurrentUser().getUid());
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    UserInformation userInformation = snapshot.getValue(UserInformation.class);
                    //setTitle("Welcome " + userInformation.getUsername());

                    //getSupportActionBar().setTitle(userInformation.getUsername() +" <3");

                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_logout)
        {
            logout();
        }
        else if(item.getItemId() == R.id.profile)
        {
            Intent profileintent = new Intent(EducationalProgramActivity.this,ProfileActivity.class);
            startActivity(profileintent);
        }
        else if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
    }

    private void logout()
    {
        mAuth.signOut();
    }
}



