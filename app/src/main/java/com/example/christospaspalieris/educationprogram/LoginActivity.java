package com.example.christospaspalieris.educationprogram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference dbReference;

    TextView SignUptv;
    EditText email,password;
    Button LoginBtn;
    Button goToBtn;

    private ProgressDialog progressLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        };*/

        goToBtn = (Button)findViewById(R.id.gotobtn);
        dbReference = FirebaseDatabase.getInstance().getReference("USERS");
        SignUptv=(TextView)findViewById(R.id.SIgnUp);

        email = (EditText) findViewById(R.id.EmailAddress);
        password = (EditText) findViewById(R.id.TextPassword);

        LoginBtn=(Button)findViewById(R.id.buttonSiginIn);

        progressLogin = new ProgressDialog(this);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLogin.setMessage("Signing In");
                progressLogin.show();
                checkLogin();
            }
        });

        goToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EducationalActivity.class));
            }
        });

        SignUptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onClick(View view) {
        /*if(view==textView_SignUP) {
            mAuth.addAuthStateListener(mAuthListener);
            mAuth.signOut();
        }*/
        if(view==LoginBtn){

        }
    }

    private void checkLogin() {
        String getemail = email.getText().toString().trim();
        String getpassword = password.getText().toString().trim();



        if(!TextUtils.isEmpty(getemail)&&!TextUtils.isEmpty(getpassword)){
            mAuth.signInWithEmailAndPassword(getemail,getpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        checkUserExist();
                        progressLogin.dismiss();
                    }else{
                        Toast.makeText(LoginActivity.this,"Error while Login",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void checkUserExist() {
        final String user_id = mAuth.getCurrentUser().getUid();
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){
                    Intent educationIntent = new Intent(LoginActivity.this,EducationalProgramActivity.class);
                    educationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(educationIntent);
                   // Toast.makeText(getApplicationContext(),"Hello !!!"+ user_id,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this,"You have to setup your account",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
