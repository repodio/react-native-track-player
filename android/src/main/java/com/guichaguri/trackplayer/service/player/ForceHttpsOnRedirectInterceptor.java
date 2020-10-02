package com.guichaguri.trackplayer.service.player;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ForceHttpsOnRedirectInterceptor implements Interceptor {

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d("Test", String.format("Sending request %s", request.url()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d("Test", String.format("Received response for %s", response.request().url()));

        if (!response.request().url().isHttps()) {
            Request originalRequest = chain.request();
            HttpUrl newUrl = response.request().url().newBuilder()
                    .scheme("https")
                    .build();
            Request updatedRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build();
            response = response.newBuilder()
                    .request(updatedRequest)
                    .build();
            Log.d("Test", String.format("Updated Redirect response for %s", response.request().url()));
        } else {
            Log.d("Test", String.format("URL was already https"));
        }
        return response;
    }
}