package com.example.christospaspalieris.educationprogram;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    Button answer1, answer2, answer3, answer4;
    TextView score, question, plus2;
    Button start_test;
    //TextView timer;
    private DatabaseReference db_questions,db_scores,current_user;
    private Questions questions_obj;
    private String mAnswer;
    private int mScore = 0;
    private int key = 0;
    private String value;
    private MediaPlayer correct_sound;
    private CountDownTimer countDownTimer;
    private String user;
    private boolean isPaused = false;
    private long user_time = 0;
    private final static String TAG = "QuizActivity";
    private static final int TIMER_LENGTH = 10;
    final float startSize = 14; // Size in pixels
    final float endSize = 38;
    final int animationDuration = 400; // Animation duration in ms
    ValueAnimator plus2_animator;

    private TimerView mTimerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        db_questions = FirebaseDatabase.getInstance().getReference("Questions");
        db_scores = FirebaseDatabase.getInstance().getReference("Scores");

        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);

        countDownTimer = new CountDownTimer(11*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isPaused)
                    cancel();
                else
                {
                    user_time = ((11*1000) - millisUntilFinished)/1000;
                }
            }

            @Override
            public void onFinish() {
                GameOver();
            }
        };


        current_user = FirebaseDatabase.getInstance().getReference("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        current_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    UserInformation userInformation = snapshot.getValue(UserInformation.class);
                    user = userInformation.getUsername();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        plus2 = (TextView)findViewById(R.id.plus2textView);
        plus2_animator = ValueAnimator.ofFloat(startSize, endSize);
        plus2_animator.setDuration(animationDuration);

        plus2_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                plus2.setVisibility(View.VISIBLE);
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                plus2.setTextSize(animatedValue);


            }



        });

        plus2_animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                plus2.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });



        mTimerView = (TimerView) findViewById(R.id.timer);

        start_test = (Button) findViewById(R.id.start_test);
        start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
                mTimerView.start(TIMER_LENGTH);
                start_test.setVisibility(View.INVISIBLE);
                question.setVisibility(View.VISIBLE);
                answer1.setVisibility(View.VISIBLE);
                answer2.setVisibility(View.VISIBLE);
                answer3.setVisibility(View.VISIBLE);
                answer4.setVisibility(View.VISIBLE);

            }
        });

        //correct_sound = MediaPlayer.create(this,R.raw.correct);

        Intent intent = getIntent();
        value = intent.getExtras().getString("item");




        score = (TextView) findViewById(R.id.score);
        question = (TextView) findViewById(R.id.question);
        //timer = (TextView) findViewById(R.id.time);




        score.setText("Score " + mScore);
        LoadNextQuestion();

    }



    @Override
    public void onClick(View view) {
        if(view==answer1)
        {
            if(answer1.getText().equals(mAnswer))
            {
                mScore++;
                score.setText("Score " + mScore);
                plus2_animator.start();
                mTimerView.right_answer = true;
                //correct_sound.start();
                LoadNextQuestion();
            }
            else
                GameOver();
        }

        if(view==answer2)
        {
            if(answer2.getText().equals(mAnswer))
            {
                mScore++;
                score.setText("Score " + mScore);
                plus2_animator.start();
                //correct_sound.start();
                LoadNextQuestion();
            }
            else
                GameOver();
        }

        if(view==answer3)
        {
            if(answer3.getText().equals(mAnswer))
            {
                mScore++;
                score.setText("Score " + mScore);
                plus2_animator.start();
                //correct_sound.start();
                LoadNextQuestion();
            }
            else
                GameOver();
        }

        if(view==answer4)
        {
            if(answer4.getText().equals(mAnswer))
            {
                mScore++;
                score.setText("Score " + mScore);
                plus2_animator.start();
                //correct_sound.start();
                LoadNextQuestion();
            }
            else
                GameOver();
        }

    }

    private void LoadNextQuestion() {
        key++;
        if(key == 11)
            Success();
        else
        {
            db_questions.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    questions_obj = dataSnapshot.child(value).child(String.valueOf(key)).getValue(Questions.class);
                    question.setText(questions_obj.getQuestionText());
                    answer1.setText(questions_obj.getChoiceA());
                    answer2.setText(questions_obj.getChoiceB());
                    answer3.setText(questions_obj.getChoiceC());
                    answer4.setText(questions_obj.getChoiceD());
                    mAnswer = questions_obj.getCorrectAnswer();

              /*questions.add(questions_obj.getQuestionText());
                questions.add(questions_obj.getChoiceA());
                questions.add(questions_obj.getChoiceB());
                questions.add(questions_obj.getChoiceC());
                questions.add(questions_obj.getChoiceD());
                questions.add(questions_obj.getCorrectAnswer());*/
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }



    private void Success() {
        isPaused = true;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder
                .setMessage("Congratulations!!! "+ user + " You've made it with score " + mScore + " points")
                .setCancelable(false)
                .setPositiveButton("Save my score", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db_scores.child(value).child(user).child("Score").setValue(mScore);
                        db_scores.child(value).child(user).child("Time").setValue(user_time);
                        finish();
                    }
                })
                .setNegativeButton("Go back to solve more", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),EducationalProgramActivity.class));
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void GameOver(){
        isPaused = true;


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder
                .setMessage("Game Over Your score is " + mScore + " points")
                .setCancelable(false)
                .setPositiveButton("NEW GAME", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        key = 0;
                        mScore = 0;
                        start_test.setVisibility(View.VISIBLE);
                        question.setVisibility(View.INVISIBLE);
                        answer1.setVisibility(View.INVISIBLE);
                        answer2.setVisibility(View.INVISIBLE);
                        answer3.setVisibility(View.INVISIBLE);
                        answer4.setVisibility(View.INVISIBLE);
                        LoadNextQuestion();
                        isPaused = false;

                        score.setText("Score: " + mScore);
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db_scores.child(value).child(user).child("Score").setValue(mScore);
                        db_scores.child(value).child(user).child("Time").setValue(user_time);
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        mTimerView.stop();
        countDownTimer.cancel();
        super.onPause();
    }
}

