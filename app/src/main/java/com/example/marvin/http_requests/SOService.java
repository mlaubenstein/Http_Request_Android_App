package com.example.marvin.http_requests;

import com.example.marvin.http_requests.Data.SOAnswersResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SOService {

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers();

}