package com.example.christospaspalieris.educationprogram;

import android.content.Intent;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private DatabaseReference dbUserReference;
    private FirebaseUser user;

    private TextView profile_label;
    private TextView firstName;
    private TextView lastName;
    private TextView Age;

    private EditText firstname;
    private EditText lastname;
    private EditText oldpass;
    private EditText newpass;
    private EditText age;

    private ImageButton profilephoto;

    private Button saveprofile;
    private Button editprofile;
    private Button canceledit;

    private boolean changedpass;
    UserInformation userInformation;

    private ToggleButton changepass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Typeface typeFace= Typeface.createFromAsset(getAssets(),"fonts/ComicBook.otf");

        profile_label = (TextView)findViewById(R.id.TVprofile);
        firstName = (TextView)findViewById((R.id.TVfirstName));
        lastName = (TextView)findViewById((R.id.TVlastName));
        Age = (TextView)findViewById((R.id.TVage));

        firstname = (EditText) findViewById(R.id.ETfirstName);
        lastname = (EditText) findViewById(R.id.ETlastName);
        oldpass = (EditText) findViewById(R.id.EToldPass);
        newpass = (EditText) findViewById(R.id.ETnewPass);
        age = (EditText) findViewById(R.id.ETage);

        changepass = (ToggleButton) findViewById(R.id.changepassButton);

        profilephoto = (ImageButton) findViewById(R.id.imageSelect);

        saveprofile = (Button)findViewById(R.id.saveProfile);
        editprofile = (Button)findViewById(R.id.editProfile);
        canceledit = (Button)findViewById(R.id.cancelEdit);

        profile_label.setTypeface(typeFace);
        firstName.setTypeface(typeFace);
        lastName.setTypeface(typeFace);
        Age.setTypeface(typeFace);
        firstname.setTypeface(typeFace);
        lastname.setTypeface(typeFace);
        oldpass.setTypeface(typeFace);
        newpass.setTypeface(typeFace);
        age.setTypeface(typeFace);

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

        dbUserReference = FirebaseDatabase.getInstance().getReference("USERS").child(user.getUid());
        dbUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userInformation = dataSnapshot.getValue(UserInformation.class);
                firstName.setText(userInformation.getFirstName());
                lastName.setText(userInformation.getLastName());
                Age.setText(userInformation.getAge());
                firstname.setHint(userInformation.getFirstName());
                lastname.setHint(userInformation.getLastName());
                age.setHint(userInformation.getAge());




            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName.setVisibility(View.GONE);
                lastName.setVisibility(View.GONE);
                Age.setVisibility(View.GONE);
                editprofile.setVisibility(View.GONE);

                canceledit.setVisibility(View.VISIBLE);
                firstname.setVisibility(View.VISIBLE);
                lastname.setVisibility(View.VISIBLE);
                age.setVisibility(View.VISIBLE);
                changepass.setVisibility(View.VISIBLE);
                saveprofile.setVisibility(View.VISIBLE);
            }
        });

        canceledit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
                Age.setVisibility(View.VISIBLE);
                editprofile.setVisibility(View.VISIBLE);


                canceledit.setVisibility(View.GONE);
                canceledit.setVisibility(View.GONE);
                firstname.setVisibility(View.GONE);
                lastname.setVisibility(View.GONE);
                age.setVisibility(View.GONE);
                changepass.setVisibility(View.GONE);
                oldpass.setVisibility(View.GONE);
                newpass.setVisibility(View.GONE);
                saveprofile.setVisibility(View.GONE);

                changepass.setChecked(false);
            }
        });


        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changepass.isChecked())
                {
                    if(newpass.getText().length()<=6)
                    {
                        Toast.makeText(ProfileActivity.this,"Password needs to have at least 6 characters", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.d(TAG,userInformation.getPassword());
                    if(String.valueOf(oldpass.getText()).equals(userInformation.getPassword()))
                    {
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(userInformation.getEmail(), userInformation.getPassword());

                        Log.d(TAG,userInformation.getEmail());
                        Log.d(TAG,userInformation.getPassword());
                        Log.d(TAG,String.valueOf(newpass.getText()));

                        // Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            dbUserReference.child("password").setValue(String.valueOf(newpass.getText()));
                                            Log.d(TAG,"elelelele");

                                            user.updatePassword(String.valueOf(newpass.getText())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "Password updated");
                                                    } else {
                                                        Log.d(TAG, "Error password not updated");
                                                    }
                                                }
                                            });
                                        } else {
                                            Log.d(TAG, "Error auth failed");
                                        }
                                    }
                                });
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
