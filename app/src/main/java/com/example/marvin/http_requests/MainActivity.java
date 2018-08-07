package com.example.marvin.http_requests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    textview.setText("GET Request...");
                    sendGET(USER_AGENT,urlString,id);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            case R.id.postRequest:
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    textview.setText("POST Request...");
                    sendPOST(USER_AGENT,urlString,id);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            case R.id.deleteRequest:
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    textview.setText("DELETE Request...");
                    sendDELETE(USER_AGENT,urlString,id);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case R.id.scannerRequest:

        }

        return super.onOptionsItemSelected(item);
    }



    public void sendGET(String USER_AGENT, String urlString, String ID) throws IOException {

        URL url = new URL(urlString + "/" + ID);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();//make sure it's HTTP and not https
        int responseCode;
        StringBuffer responseBuffer;

        try{
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);
        }catch (IOException e){
            //TODO
        }

        //responseCode = connection.getResponseCode();
        textview.setText("\nSending 'GET' request to URL : "  + url);
        //textview.setText("\nResponse Code : "                 + responseCode);

        responseBuffer = getInputStream ( connection );

        print (responseBuffer.toString());

        boolean finished = true;
    }



    public void sendDELETE(String USER_AGENT, String urlString, String ID) throws IOException {

        URL url = new URL(urlString + "/" + ID);
        int responseCode;
        StringBuffer responseBuffer;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("UserAgent", USER_AGENT);

        } catch (IOException e) {
            //TODO
        }

        //responseCode = connection.getResponseCode();
        textview.setText("\nSending 'DELETE' request to URL : " + url);
        //textview.setText("\nResponse Code : " + responseCode);

        responseBuffer = getInputStream(connection);
        textview.setText(responseBuffer.toString());
    }



    private boolean finished = false;

    public void sendPOST(String USER_AGENT, String urlString, String ID) throws IOException {

        URL url;
        int responseCode;
        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        StringBuffer responseBuffer;
        String author,title,random;

        url = new URL( urlString );
        connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod     ( "POST" );//
            connection.setRequestProperty   ( "User-Agent", USER_AGENT );
            connection.setRequestProperty   ( "Content-Type", "application/json" );

            connection.setDoInput   ( true );
            connection.setDoOutput  ( true );
        }catch (IOException e){
            //TODO
        }

        title  = scanTitle  ();
        author = scanAuthor ();
        random = scanRandom ();
        dataOutputStream = new DataOutputStream(connection.getOutputStream()); //IOException needed
        dataOutputStream.writeBytes ( "{ \"title\":          \"" +title+"\","+
                "\"author\":        \"" +author+"\","+
                "\"Keine Ahnung\":  \"" +random+"\","+
                "\"id\":            " +ID    +"}"
        );
        dataOutputStream.flush();
        dataOutputStream.close();


       // responseCode = connection.getResponseCode();
        textview.setText( "\nSending 'POST' request to URL : "    + url);
       // textview.setText( "\nResponse Code : "                    + responseCode);
        textview.setText("\nResponse Message : "                 + connection.getResponseMessage () );

        responseBuffer = getInputStream ( connection );

        print ( responseBuffer.toString() );
        //TODO : make output readable, by line break, after every comma

        //connection.disconnect ();//Tried
        dataOutputStream.close ();

        //Try to solve that todo in the main
        finished = true;
        finished ();
    }

    private boolean finished(){
        return finished;
    }

    public static StringBuffer getInputStream(HttpURLConnection connection) throws IOException {

        StringBuffer responseBuffer;
        BufferedReader inBufferedReader;
        String inputLine;

        inBufferedReader = new BufferedReader ( new InputStreamReader(connection.getInputStream()));
        responseBuffer = new StringBuffer();

        while ((inputLine = inBufferedReader.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        inBufferedReader.close();

        return responseBuffer;

    }

    public String scanTitle () {

        textview.setText ( "\nSet a Title : " );
        Scanner scanner = new Scanner ( System.in );
        String TitleReturnValue = scanner.nextLine ();
        return TitleReturnValue;
    }
    public String scanAuthor () {

        textview.setText ( "\nSet a Author : " );
        Scanner scanner = new Scanner ( System.in );
        String AuthorReturnValue = scanner.nextLine ();
        return AuthorReturnValue;
    }
    public String scanRandom() {

        textview.setText ( "\nSet a Random Property : " );
        Scanner scanner = new Scanner ( System.in );
        String RandomReturnValue = scanner.nextLine ();
        return RandomReturnValue;
    }

    public void print(String InputStream){

        for (int i = 0; i<InputStream.length (); i++){
            String SingleSign = String.valueOf ( InputStream.charAt ( i ) );
            if (SingleSign.equals (",") ) {
                textview.setText ( ",");
                textview.setText( "\t" );
            }
            else if (SingleSign.equals ( "}" )){
                textview.setText ( "" );
                textview.setText ( "}" );
                i++;
                textview.setText ( "" );
            }
            else if (SingleSign.equals ( "{" )){
                textview.setText ( "{" );
                textview.setText ( "\t" );
            }
            else {
                textview.setText( SingleSign );
            }
        }
    }

        public void setHasOptionMenu(boolean b) {

    }
}
