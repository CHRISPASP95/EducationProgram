package com.example.christospaspalieris.educationprogram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister, btn2;
    private EditText editTextEmail,editTextPassword,editTextUserName,editTextFirstName,editTextLastName, editTextAge;
    private RadioGroup choice_sex;
    private RadioButton male,female;
    private TextView textViewSignin;
    private ImageButton imageButton;
    private Uri profile_pic;
    private Uri camera_uri;

    private boolean camera = false;

    private ProgressDialog progressdialog;

    private static final int GALLERY_REQUEST = 1;
    private static final int CAMERA_REQUEST_CODE = 2;


    private FirebaseAuth firebaseAyth;
    private DatabaseReference dbReference;
    private StorageReference mStorageImage;

    String sex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegister=(Button)findViewById(R.id.buttonRegister);
        btn2= (Button)findViewById(R.id.button2);

        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText)findViewById(R.id.editTextLastName);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextAge = (EditText)findViewById(R.id.editAge);

        choice_sex = (RadioGroup) findViewById(R.id.radiogroup);
        male = (RadioButton) findViewById(R.id.radiomale);
        female = (RadioButton) findViewById(R.id.radiofemale);

        imageButton = (ImageButton) findViewById(R.id.imageButton);

        textViewSignin=(TextView)findViewById(R.id.textViewSignin);

        progressdialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        firebaseAyth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference("USERS");
        mStorageImage = FirebaseStorage.getInstance().getReference("Profile_images");


    }



    @Override
    public void onClick(View view) {
        if(view==buttonRegister){
            registerUser();

        }
        if(view==imageButton){
            camera=false;
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_REQUEST);
        }
        if(view==textViewSignin){
            /*FirebaseUser user = firebaseAyth.getCurrentUser();
            if(user!=null)*/
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        if(view==btn2){
            camera=true;
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }

        int checkRadioButton = choice_sex.getCheckedRadioButtonId();

        switch (checkRadioButton){
            case R.id.radiomale:
                if(male.isChecked()){
                    sex = "Male";
                }
                break;
            case R.id.radiofemale:
                if(female.isChecked()){
                    sex = "Female";
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){

            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                profile_pic = result.getUri();
                imageButton.setImageURI(profile_pic);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            profile_pic = data.getData();
            imageButton.setImageURI(profile_pic);
        }
    }



    private void registerUser() {

        String username = editTextUserName.getText().toString().trim();
        String FirstName = editTextFirstName.getText().toString().trim();
        String LastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(FirstName)){
            Toast.makeText(this,"Please enter first name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(LastName)){
            Toast.makeText(this,"Please enter last name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(age)){
            Toast.makeText(this,"Please enter your age", Toast.LENGTH_SHORT).show();
            return;
        }

        progressdialog.setMessage("Registering User...");
        progressdialog.show();

        firebaseAyth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Registerd success", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                    SaveUserInfo();
                    Intent Login = new Intent(getApplicationContext(),LoginActivity.class);
                    Login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(Login);

                }
                else {
                    Toast.makeText(MainActivity.this,"error", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                }

            }
        });



    }

    private void SaveUserInfo(){
        String username = editTextUserName.getText().toString().trim();
        String firstname = editTextFirstName.getText().toString().trim();
        String lastname = editTextLastName.getText().toString().trim();
        String email_address = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();






        UserInformation userInformation = new UserInformation(username,firstname,lastname,email_address,password,age,sex);
        FirebaseUser user = firebaseAyth.getCurrentUser();

        dbReference.child(user.getUid()).child("Person's Info").setValue(userInformation);
        StorageReference filepath = mStorageImage.child(profile_pic.getLastPathSegment());
        filepath.putFile(profile_pic).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                FirebaseUser user = firebaseAyth.getCurrentUser();
                @SuppressWarnings("VisibleForTests")
                String downloadUri = taskSnapshot.getDownloadUrl().toString();
                dbReference.child(user.getUid()).child("Person's Info").child("Profile_images").setValue(downloadUri);

            }
        });
               /* StorageReference filepath = mStorageImage.child("camera").child(camera_uri.getLastPathSegment());
                filepath.putFile(camera_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        FirebaseUser user = firebaseAyth.getCurrentUser();
                        @SuppressWarnings("VisibleForTests")
                        String downloadUri = taskSnapshot.getDownloadUrl().toString();
                        dbReference.child(user.getUid()).child("Person's Info").child("Profile_images").setValue(downloadUri);
                    }
                });*/

    }
}
