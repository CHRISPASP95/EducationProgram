package com.example.christospaspalieris.educationprogram;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class QuizActivity extends AppCompatActivity {


    private String test_key;
    private Fragment QuizFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        QuizFragment = new QuizFragment();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            test_key = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        Bundle bundle = new Bundle();
        bundle.putString("key", test_key);
        QuizFragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_container, QuizFragment);// or ft.add(R.id.your_placeholder, new FooFragment());
        ft.commit();// Complete the changes added above



    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
