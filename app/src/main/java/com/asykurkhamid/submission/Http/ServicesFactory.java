package com.asykurkhamid.submission.Http;

import android.content.Context;

import com.asykurkhamid.submission.BuildConfig;
import com.readystatesoftware.chuck.ChuckInterceptor;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hendi on 9/15/15.
 */
public class ServicesFactory {
    private static boolean ENABLE_LOGGING = BuildConfig.DEBUG;
    private static String BASE_URL = "https://api.themoviedb.org/3/";

    public static Services getService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(generateClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(Services.class);
    }

    private static OkHttpClient generateClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        if (ENABLE_LOGGING) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
        }
        return clientBuilder.build();
    }


    public static Services getServiceChuck(Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(generateClientChuck(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(Services.class);
    }
    private static OkHttpClient generateClientChuck(Context context) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(context))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        if (ENABLE_LOGGING) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
        }
        return clientBuilder.build();
    }
}