package com.example.marvin.http_requests;

public class APIUtils {

        private static final String BASE_URL = "https://api.stackexchange.com/2.2/";

        public static SOService getSOService() {
            return RetrofitClient.getClient(BASE_URL).create(SOService.class);
        }
    }