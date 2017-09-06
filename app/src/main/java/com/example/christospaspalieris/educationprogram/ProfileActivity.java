package com.example.christospaspalieris.educationprogram;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity  {


    private static final String TAG = "ProfileActivity";

    private static final int GALLERY_REQUEST = 1;
    private static final int CAMERA_REQUEST_CODE = 2;


    private FirebaseAuth mAuth;
    private DatabaseReference dbReference;


    private EditText firstname;
    private EditText lastname;
    private EditText oldpass;
    private EditText newpass;
    private EditText age;
    private ImageButton profilephoto;
    private Button saveprofile;

    private boolean changedpass;
    UserInformation userInformation;

    private ToggleButton changepass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        firstname = (EditText) findViewById(R.id.ETfirstName);
        lastname = (EditText) findViewById(R.id.ETlastName);
        oldpass = (EditText) findViewById(R.id.EToldPass);
        newpass = (EditText) findViewById(R.id.ETnewPass);
        age = (EditText) findViewById(R.id.ETage);
        changepass = (ToggleButton) findViewById(R.id.changepassButton);
        profilephoto = (ImageButton) findViewById(R.id.imageSelect);
        saveprofile = (Button)findViewById(R.id.saveProfile);


        changepass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        dbReference = FirebaseDatabase.getInstance().getReference("USERS").child(mAuth.getCurrentUser().getUid());
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    userInformation = snapshot.getValue(UserInformation.class);
                    firstname.setText(userInformation.getFirstName());
                    lastname.setText(userInformation.getLastName());
                    age.setText(userInformation.getAge());


                    //setTitle("Welcome " + userInformation.getUsername());

                    //getSupportActionBar().setTitle(userInformation.getUsername() +" <3");

                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changepass.isChecked())
                {
                    if(newpass.getText().equals(""))
                    {
                        Toast.makeText(ProfileActivity.this,"New password can't be null", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(newpass.getText().length()<=6)
                    {
                        Toast.makeText(ProfileActivity.this,"Password needs to have at least 6 characters", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(oldpass.getText().equals(userInformation.getPassword()))
                    {
                        Toast.makeText(ProfileActivity.this,"Password changed", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {

                }
            }
        });

        profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ProfileActivity.this, profilephoto);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_list, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Browse"))
                        {
                            Intent galleryIntent = new Intent();
                            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                            galleryIntent.setType("image/*");
                            startActivityForResult(galleryIntent,GALLERY_REQUEST);
                        }
                        else if(item.getTitle().equals("Take a picture"))
                        {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(ProfileActivity.this.getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                            }
                        }
                        Toast.makeText(
                                ProfileActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu


            }
        });

    }



}
