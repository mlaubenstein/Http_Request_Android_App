package com.example.marvin.http_requests;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.marvin.http_requests.Data.Item;
import com.example.marvin.http_requests.Data.SOAnswersResponse;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AnswersAdapter Adapter;
    private SOService Service;

    public static final String EXTRA_SCORE              = "com.example.marvin.http_request.EXTRA_SCORE";
    public static final String EXTRA_ANSWERID           = "com.example.marvin.http_request.EXTRA_ANSWERID";
    public static final String EXTRA_LASTACTIVITYDATE   = "com.example.marvin.http_request.EXTRA_LASTACTIVITYDATE";
    public static final String EXTRA_LASTEDITDATE       = "com.example.marvin.http_request.EXTRA_LASTEDITDATE";
    public static final String EXTRA_QUESTIONID         = "com.example.marvin.http_request.EXTRA_QUESTIONID";
    public static final String EXTRA_NAME               = "com.example.marvin.http_request.EXTRA_NAME";
    public static final String EXTRA_IMAGE              = "com.example.marvin.http_request.EXTRA_IMAGE";
    public static final String EXTRA_LINK               = "com.example.marvin.http_request.EXTRA_LINK";

    @Override
    protected void onCreate (Bundle savedInstanceState)  {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_main );

        this.Service = APIUtils.getSOService();
        final android.support.v7.widget.RecyclerView recyclerView = findViewById(R.id.rv_answers);
        this.Adapter = new AnswersAdapter(this, new ArrayList<Item>(0), new AnswersAdapter.ItemClickListener() {

            @Override
            public void onItemClick(Item item) {

                Intent intent = new Intent(MainActivity.this,SecondMain.class);
                recyclerView.getSolidColor();
                intent.putExtra(EXTRA_NAME,             String.valueOf( item.getOwner().getDisplayName()    ));
                intent.putExtra(EXTRA_SCORE,            String.valueOf( item.getScore()                     ));
                intent.putExtra(EXTRA_ANSWERID,         String.valueOf( item.getAnswerId()                  ));
                intent.putExtra(EXTRA_QUESTIONID,       String.valueOf( item.getQuestionId()                ));
                intent.putExtra(EXTRA_LASTACTIVITYDATE, String.valueOf( item.getLastActivityDate()          ));
                intent.putExtra(EXTRA_LASTEDITDATE,     String.valueOf( item.getLastEditDate()              ));
                intent.putExtra(EXTRA_IMAGE,            String.valueOf( item.getOwner().getProfileImage()   ));
                intent.putExtra(EXTRA_LINK,             String.valueOf( item.getOwner().getLink()           ));


                startActivity(intent);

            }
        });

         RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setAdapter(this.Adapter);
         recyclerView.setHasFixedSize(true);
         RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

         recyclerView.addItemDecoration(itemDecoration);

        loadAnswers();
    }


    public void loadAnswers() {
        this.Service.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<SOAnswersResponse> call, @NonNull Response<SOAnswersResponse> response) {

                if(response.isSuccessful()) {
                    Adapter.updateAnswers(Objects.requireNonNull(response.body()).getItems());
                    Log.d("MainActivity", "posts loaded from API");
                }
            }

            @Override
            public void onFailure(@NonNull Call<SOAnswersResponse> call, @NonNull Throwable throwable) {
                showErrorMessage();
                Log.d("MainActivity", "error loading from API");

            }
        });
    }

    private void showErrorMessage() {
    }


}
