package com.example.christospaspalieris.educationprogram;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class WallActivity extends AppCompatActivity {

    private final static String TAG = "WallActivity";

    private RecyclerView mForumList;

    private DatabaseReference mDatabase;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Forum");
        mForumList = (RecyclerView)findViewById(R.id.forum_list);
        mForumList.setHasFixedSize(true);
        mForumList.setLayoutManager(new LinearLayoutManager(this));

        // Toolbar

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //---

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Post,PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(
                Post.class,
                R.layout.forum_row,
                PostViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, Post model, int position) {
                String userid = model.getUid();
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("USERS").child(userid).child("Person's Info");
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String profile_image = dataSnapshot.child("image").getValue(String.class);
                        String username = dataSnapshot.child("username").getValue(String.class);

                        viewHolder.setImage(getApplicationContext(),profile_image);
                        viewHolder.setUsername(username);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());

            }
        };

        mForumList.setAdapter(firebaseRecyclerAdapter);

    }




    public static class PostViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public PostViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title)
        {
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String desc)
        {
            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx,String image)
        {
            ImageView poster_image = (ImageView)mView.findViewById(R.id.poster_image);
            Picasso.with(ctx).load(image).into(poster_image);
        }

        public void setUsername(String username)
        {
            TextView username_tv = (TextView)mView.findViewById(R.id.username_tv);
            username_tv.setText(username);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.wall_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_add)
        {
            startActivity(new Intent(getApplicationContext(),PostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
