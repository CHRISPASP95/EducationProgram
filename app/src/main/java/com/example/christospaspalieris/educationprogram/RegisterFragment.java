package com.example.christospaspalieris.educationprogram;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by User on 2/28/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "RegisterFragment";

    private Button buttonRegister;
    private EditText editTextEmail,editTextPassword,editTextUserName,editTextFirstName,editTextLastName, editTextAge;
    private RadioButton male,female;
    private RadioGroup choice_sex;
    private String sex;
    private ImageButton imageButton;
    private Uri profile_pic;

    private ProgressDialog mProgress;

    private static final int GALLERY_REQUEST = 1;
    private static final int CAMERA_REQUEST_CODE = 2;

    private FirebaseAuth mAuth;
    private DatabaseReference dbReference;
    private StorageReference mStorageImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment,container,false);

        buttonRegister=(Button)view.findViewById(R.id.buttonRegister);

        editTextUserName = (EditText)view.findViewById(R.id.editTextUserName);
        editTextFirstName = (EditText)view.findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText)view.findViewById(R.id.editTextLastName);
        editTextEmail = (EditText)view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)view.findViewById(R.id.editTextPassword);
        editTextAge = (EditText)view.findViewById(R.id.editAge);

        male = (RadioButton)view. findViewById(R.id.radiomale);
        female = (RadioButton) view.findViewById(R.id.radiofemale);
        choice_sex = (RadioGroup) view.findViewById(R.id.radiogroup);

        imageButton = (ImageButton) view.findViewById(R.id.imageSelect);

        mProgress = new ProgressDialog(getActivity());

        buttonRegister.setOnClickListener(this);
        imageButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


        dbReference = FirebaseDatabase.getInstance().getReference("USERS");
        mStorageImage = FirebaseStorage.getInstance().getReference("Profile_images");

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view==buttonRegister){
            registerUser();

        }
        if(view==imageButton){


            PopupMenu popup = new PopupMenu(getActivity(), imageButton);
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
                        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                        }
                    }
                    Toast.makeText(
                            getActivity(),
                            "You Clicked : " + item.getTitle(),
                            Toast.LENGTH_SHORT
                    ).show();
                    return true;
                }
            });

            popup.show(); //showing popup menu
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED) {
            if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

                Uri imageUri = data.getData();
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(getActivity());
            } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {




                //imageButton.setImageURI(null);
                //imageButton.setImageURI(profile_pic);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageButton.setImageBitmap(photo);

                //profile_pic = getImageUri(getApplicationContext(), photo);


                if(profile_pic == null)
                {
                    System.out.println("yolo");
                }


            }
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
    }

    public Uri getImageUri(Context inContext, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }
    private void registerUser() {

        String username = editTextUserName.getText().toString().trim();
        String FirstName = editTextFirstName.getText().toString().trim();
        String LastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(getActivity(),"Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(FirstName)){
            Toast.makeText(getActivity(),"Please enter first name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(LastName)){
            Toast.makeText(getActivity(),"Please enter last name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(),"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(),"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(age)){
            Toast.makeText(getActivity(),"Please enter your age", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgress.setMessage("Registering User and\nSigning In");
        mProgress.show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = dbReference.child(user_id);
                    SaveUserInfo();

                    mProgress.dismiss();

                    Intent educationIntent = new Intent(getActivity(), EducationalProgramActivity.class);
                    educationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(educationIntent);

                   /* Toast.makeText(RegisterActivity.this,"Registerd success", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    SaveUserInfo();
                    Intent Login = new Intent(getApplicationContext(),LoginActivity.class);
                    Login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(Login);*/

                }
                else {
                    Toast.makeText(getActivity()
                            ,"error", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
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
        String default_image = "https://firebasestorage.googleapis.com/v0/b/my-projectsdb.appspot.com/o/Profile_images%2Fdefault_profile_icon.png?alt=media&token=58f16c42-f3c9-4f23-8e29-cbd462f4dc2d";


        if(male.isChecked())
            sex = "Male";
        if(female.isChecked())
            sex = "Female";

        UserInformation userInformation = new UserInformation(username,firstname,lastname,email_address,password,age,sex,default_image);
        FirebaseUser user = mAuth.getCurrentUser();

        dbReference.child(user.getUid()).child("Person's Info").setValue(userInformation);

        /*StorageReference filepath = mStorageImage.child(profile_pic.getLastPathSegment());
        filepath.putFile(profile_pic).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                FirebaseUser user = mAuth.getCurrentUser();
                @SuppressWarnings("VisibleForTests")
                String downloadUri = taskSnapshot.getDownloadUrl().toString();
                dbReference.child(user.getUid()).child("Person's Info").child("Profile_images").setValue(downloadUri);
            }
        });*/
    }
}
