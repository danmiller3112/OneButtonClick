package com.com.ulike_app.onebuttonclick.model;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by RDL on 26/02/2017.
 */

public class HttpProvider {
    public static final String BASE_URL = "https://androidexam.s3.amazonaws.com";
    public static final String JSON_URL = "/butto_to_action_config.json";

    private static HttpProvider ourInstance = new HttpProvider();

    public HttpProvider() {
    }

    public static HttpProvider getInstance() {
        return ourInstance;
    }

    public String getJsonConfig() throws Exception {
        String result = "";
        Request.Builder builder = new Request.Builder();
        builder.url(HttpProvider.BASE_URL + JSON_URL);
        builder.get();
        Request request = builder.build();

        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(15, TimeUnit.SECONDS);
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        Response response = client.newCall(request).execute();
        if (response.code() < 400) {
            result = response.body().string();
            Log.d("Get config", result);
        } else if (response.code() == 401) {
            throw new Exception("Wrong authorization!");
        } else {
            String error = response.body().string();
            Log.e("Get config", error);
            throw new Exception("Server ERROR!");
        }
        return result;
    }


}
