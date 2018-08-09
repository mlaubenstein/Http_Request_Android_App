package com.example.marvin.http_requests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);


        TextView textViewName       = findViewById(R.id.textViewName);
        textViewName.setText    ( getIntent().getStringExtra(MainActivity.EXTRA_NAME));

        TextView textViewScore      = findViewById(R.id.textViewScore);
        textViewScore.setText   ( getIntent().getStringExtra(MainActivity.EXTRA_SCORE));

        TextView textViewAnswer     = findViewById(R.id.textViewAnswer);
        textViewAnswer.setText  ( getIntent().getStringExtra(MainActivity.EXTRA_ANSWERID));

        TextView textViewQuestion   = findViewById(R.id.textViewQuestion);
        textViewQuestion.setText( getIntent().getStringExtra(MainActivity.EXTRA_QUESTIONID));

        TextView textViewLAD        = findViewById(R.id.textViewLAD);
        textViewLAD.setText     ( getIntent().getStringExtra(MainActivity.EXTRA_LASTACTIVITYDATE));

        TextView textViewLED        = findViewById(R.id.textViewLED);
        textViewLED.setText     ( getIntent().getStringExtra(MainActivity.EXTRA_LASTEDITDATE));



        Button button               = findViewById(R.id.backbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




}
