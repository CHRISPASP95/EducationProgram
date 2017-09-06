package com.example.christospaspalieris.educationprogram;

import android.support.annotation.IntegerRes;
//import android.support.design.ViewPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TheoryActivity extends AppCompatActivity {

    private static final String TAG = "TheoryActivity";

    private DatabaseReference mDatabaseNotes;

    ImageView iv;
    RelativeLayout rl;
    LinearLayout ll;
    int counter;
    LayoutInflater inflator;

    String item = "Δεκαδικοί";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);

        mDatabaseNotes = FirebaseDatabase.getInstance().getReference().child("Theory").child(item).child("notes");

        rl = (RelativeLayout) findViewById(R.id.my_relative_layout);
        ll = (LinearLayout) findViewById(R.id.my_linear_layout);
        iv = new ImageView(this);
        iv.setBackgroundResource(R.drawable.calculator);

        mDatabaseNotes.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    Note note = postSnapshot.getValue(Note.class);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 85);
                    params.leftMargin = note.getCoordinatesX();
                    params.topMargin = note.getCoordinatesY();
                    inflator = LayoutInflater.from(getBaseContext());
                    View item = inflator.inflate(R.layout.note_item,null);
                    rl.addView(item, params);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                            Log.d(TAG,String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 85);
//                    params.leftMargin = (int)event.getX();
//                    params.topMargin = (int)event.getY();
//                    inflator = LayoutInflater.from(getBaseContext());
//                    View item = inflator.inflate(R.layout.note_item,null);
//                   //iv.setSingleLine(false);
//                    //iv.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
//                    rl.addView(item, params);
                    DatabaseReference newnote = mDatabaseNotes.push();
                    newnote.child("coordinatesX").setValue((int)event.getX());
                    newnote.child("coordinatesY").setValue((int)event.getY());



                    //((ViewGroup)iv.getParent()).removeView(iv);
                }
                return true;
            }
        });




    }
}


