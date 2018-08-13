package com.example.marvin.http_requests;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class SecondMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);


        TextView textViewName       = findViewById( R.id.textViewName );
        textViewName.setText    ( getIntent().getStringExtra(MainActivity.EXTRA_NAME));

        TextView textViewScore      = findViewById( R.id.textViewScore );
        textViewScore.setText   ( getIntent().getStringExtra(MainActivity.EXTRA_SCORE));

        TextView textViewAnswer     = findViewById( R.id.textViewAnswer );
        textViewAnswer.setText  ( getIntent().getStringExtra(MainActivity.EXTRA_ANSWERID));

        TextView textViewQuestion   = findViewById( R.id.textViewQuestion );
        textViewQuestion.setText( getIntent().getStringExtra(MainActivity.EXTRA_QUESTIONID));

        TextView textViewLAD        = findViewById( R.id.textViewLAD );
        textViewLAD.setText     ( getIntent().getStringExtra(MainActivity.EXTRA_LASTACTIVITYDATE));

        TextView textViewLED        = findViewById( R.id.textViewLED );
        textViewLED.setText     ( getIntent().getStringExtra(MainActivity.EXTRA_LASTEDITDATE));



        new DownloadImageFromURL((ImageView)findViewById(R.id.imageView))
                                            .execute(getIntent()
                                            .getStringExtra(MainActivity.EXTRA_IMAGE));
        final ImageView imageView = findViewById(R.id.imageView);
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this,R.anim.zoom);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(zoomAnimation);
            }
        });




        TextView link               = findViewById( R.id.link );
        link.setText            ( getIntent().getStringExtra(MainActivity.EXTRA_LINK) );
        link.setMovementMethod  ( LinkMovementMethod.getInstance() );


        Button button               = findViewById( R.id.backbutton );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageFromURL extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        DownloadImageFromURL(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getApplicationContext(), "Hier die gewünschten Informationen...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

    }
}
