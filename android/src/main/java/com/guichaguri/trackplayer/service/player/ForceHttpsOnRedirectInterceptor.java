package com.guichaguri.trackplayer.service.player;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ForceHttpsOnRedirectInterceptor implements Interceptor {

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String location = response.headers().get("Location");
        if (location != null) {
            HttpUrl httpUrlLocation = HttpUrl.parse(location);
            if (httpUrlLocation != null && !httpUrlLocation.isHttps()) {
                response = response.newBuilder()
                        .addHeader(
                                "Location",
                                httpUrlLocation.newBuilder().scheme("https").build().toString()
                        )
                        .build();
            }
        }
        return response;
    }
}