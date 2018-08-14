package com.example.marvin.http_requests;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class SecondMain extends AppCompatActivity {

    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;
    private ImageView imageView;



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

        //Bild anzeigen, welches aus URL geladen wurde
        new DownloadImageFromURL((ImageView)findViewById(R.id.imageView))
                                            .execute(getIntent()
                                            .getStringExtra(MainActivity.EXTRA_IMAGE));
        imageView = findViewById(R.id.imageView);
        //BILD ZOOMEN EINMALIG MIT REVERSE EFFEKT
       // final Animation zoomAnimation = AnimationUtils.loadAnimation(this,R.anim.zoom);
       // imageView.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         imageView.startAnimation(zoomAnimation);
       //     }
       // });
        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());


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




    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;



    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            scaleFactor *= scaleGestureDetector.getScaleFactor();
            scaleFactor  =   Math.max(0.1f, Math.min(scaleFactor, 10.0f));

            imageView.setScaleX(scaleFactor);
            imageView.setScaleY(scaleFactor);
            return true;
        }
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
            Bitmap bimage   = null;
            InputStream inputStream;

            try {
                inputStream = new java.net.URL(imageURL).openStream();
                bimage      = BitmapFactory.decodeStream(inputStream);
            }
            catch (Exception e) {
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
