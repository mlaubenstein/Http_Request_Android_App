package com.example.marvin.http_requests;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String USER_AGENT ="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0.2 Safari/604.4.7";
    private TextView textview;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         textview       = findViewById(R.id.textView);
         progressBar    = findViewById(R.id.progressBar);

         progressBar.setVisibility(View.INVISIBLE);

         //progressBar.setVisibility(View.VISIBLE);


        setHasOptionMenu (true);
    }



    @Override
    public boolean onCreateOptionsMenu (Menu menu){


        getMenuInflater().inflate(R.menu.menu_getrequest,   menu);
        getMenuInflater().inflate(R.menu.menu_deleterequest,menu);
        getMenuInflater().inflate(R.menu.menu_postrequest,  menu);
        getMenuInflater().inflate(R.menu.menu_scanner,      menu);

        return true;

    }



    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        String urlString = "https://jsonplaceholder.typicode.com/posts";//TODO
        String id = "1";

        switch(item.getItemId()){

            case R.id.getRequest:
                    progressBar.setVisibility(View.VISIBLE );
                    textview.setText("GET Request...");
                    new HttpTask().execute(urlString);
                    progressBar.setVisibility(View.INVISIBLE);


            case R.id.postRequest:
                    progressBar.setVisibility(View.VISIBLE);
                    textview.setText("POST Request...");

                    progressBar.setVisibility(View.INVISIBLE);


            case R.id.deleteRequest:
                    progressBar.setVisibility(View.VISIBLE);
                    textview.setText("DELETE Request...");
                    progressBar.setVisibility(View.INVISIBLE);

            case R.id.scannerRequest:
                    progressBar.setVisibility(View.VISIBLE);
                    textview.setText("INFORAMTION...");
                    progressBar.setVisibility(View.INVISIBLE);

        }

        return super.onOptionsItemSelected(item);
    }


    private class HttpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strURLs) {
            URL url = null;
            HttpsURLConnection httpsURLConnection = null;
            int responseCode;
            String line;
            StringBuffer responseBuffer = null;
            StringBuilder  result;
            BufferedReader reader;
            InputStream    inputStream;

            try {
                url = new URL(strURLs[0]);

                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setRequestProperty("User-Agent",USER_AGENT);

                responseCode = httpsURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = url.openStream();
                    reader     = new BufferedReader(new InputStreamReader(inputStream));
                    result = new StringBuilder();


                    responseBuffer = getInputStream(httpsURLConnection);
                    textview.setText(responseBuffer.toString());

                }
                return responseBuffer.toString();
            } catch (IOException e) {
                return "Unable to connect";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            textview.setText(result);
        }
    }




    public void setHasOptionMenu(boolean b) {

    }


    public static StringBuffer getInputStream(HttpsURLConnection connection) throws IOException {

        StringBuffer responseBuffer;
        BufferedReader inBufferedReader;
        String inputLine;

        inBufferedReader = new BufferedReader ( new InputStreamReader (connection.getInputStream()));
        //System.out.println ( "inBufferedReader : " + inBufferedReader );
        responseBuffer = new StringBuffer();
        //System.out.println ( "responseBuffer : " + responseBuffer );

        //writing inBufferReader line by line into the responseBuffer
        while ((inputLine = inBufferedReader.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        inBufferedReader.close();

        return responseBuffer;

    }



}
