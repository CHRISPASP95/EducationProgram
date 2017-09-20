package com.example.christospaspalieris.educationprogram;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TheoryFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference mDatabaseTheory;

    String subject;
    String role;
    TextView theoryTitle;
    TextView theory;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.theory_fragment,container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            subject = bundle.getString("subject", "nothing");
            role = bundle.getString("role", "nothing");
        }

        mDatabaseTheory = FirebaseDatabase.getInstance().getReference("Theory").child(subject);

        theoryTitle = (TextView) view.findViewById(R.id.theoryTitle);
        theoryTitle.setText(subject);
        theory = (TextView) view.findViewById(R.id.theory);

        mDatabaseTheory.child("Text").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String text = snapshot.getValue().toString();
                if (Build.VERSION.SDK_INT >= 24) {
                    theory.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)); // for 24 api and more
                } else {
                    theory.setText(Html.fromHtml(text)); // or for older api
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}