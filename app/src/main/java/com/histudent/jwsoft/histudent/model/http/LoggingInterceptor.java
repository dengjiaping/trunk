package com.histudent.jwsoft.histudent.model.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by huyg on 2017/11/2.
 */

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d(getClass().getSimpleName(), String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        long t2 = System.nanoTime();
        Log.d(getClass().getSimpleName(), String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        Log.d(getClass().getSimpleName(),"/n"+responseBody.string());

        return response;
    }
}
