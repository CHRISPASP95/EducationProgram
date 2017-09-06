package com.example.christospaspalieris.educationprogram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class PostActivity extends AppCompatActivity {

    //private ImageButton mSelectImage;

    private static final String TAG = "PostActivity";
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitBtn;

    private String posterImage;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private UserInformation userinfo;
    private ProgressDialog mProgressDialog;

    private FirebaseUser mCurrentUser;

    private DatabaseReference mDatabaseUser;



    private static final int GALLERY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mStorage = FirebaseStorage.getInstance().getReference();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Forum");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("USERS").child(mCurrentUser.getUid());

        //mSelectImage = (ImageButton)findViewById(R.id.imageSelect);

        mPostTitle = (EditText)findViewById(R.id.titleField);
        mPostDesc = (EditText)findViewById(R.id.descField);
        mSubmitBtn = (Button)findViewById(R.id.submitBtn);

        mProgressDialog = new ProgressDialog(this);






        //Log.d(TAG, posterImage);




        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {

        mProgressDialog.setMessage("Posting to Forum...");
        mProgressDialog.show();
        String title_val = mPostTitle.getText().toString().trim();
        String desc_val = mPostDesc.getText().toString().trim();

        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val))
        {
            DatabaseReference newPost = mDatabase.push();
            newPost.child("title").setValue(title_val);
            newPost.child("desc").setValue(desc_val);
            newPost.child("uid").setValue(mCurrentUser.getUid());

            Log.d(TAG,"yolyoylyo");

             //StorageReference filepath = mStorage.child("Post_Images");





        }
        mProgressDialog.dismiss();

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
//        {
//            mImageUri = data.getData();
//            mSelectImage.setImageURI(mImageUri);
//        }
//    }
}
