package com.example.tom.registrationokhttp.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.tom.registrationokhttp.observer.Net;
import com.example.tom.registrationokhttp.observer.NetSubscription;
import com.example.tom.registrationokhttp.pojo.UserLogin;
import com.example.tom.registrationokhttp.pojo.UserRegistry;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.tom.registrationokhttp.app.Constants.TAG_LOGIN;
import static com.example.tom.registrationokhttp.app.Constants.TAG_REGISTRY;

public class Network extends NetSubscription implements Net {
    private static final String BASE_URL = "https://api.backendless.com/";
    private static final String APPLICATION_ID = "D0C2329C-1475-7BEC-FF56-E36FFAC2D600" + "/";
    private static final String REST_SECRET_KEY = "1C02B61E-F38A-0219-FFA7-760BB45A7000" + "/";
    private static final String URL = BASE_URL + APPLICATION_ID + REST_SECRET_KEY;
    private static UserAPI userAPI;

    public Network() {
        retrofitBuilder();
    }

    @Override
    public void loginUser(final UserLogin userLogin) {
        userAPI.getLoginUser(userLogin).enqueue(new retrofit2.Callback<UserLogin>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<UserLogin> call,
                                   @NonNull retrofit2.Response<UserLogin> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TAG", "Call " + response);
                    notifySuccess(TAG_LOGIN);
                } else {
                    notifyError(TAG_LOGIN);
                }
                Log.d("TAG", "Call " + response);
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<UserLogin> call,
                                  @NonNull Throwable t) {
                notifyError(TAG_LOGIN);
                Log.d("TAG", "Call " + t);
            }
        });
    }

    @Override
    public void registrationUser(final UserRegistry userRegistry) {
        userAPI.getRegisterUser(userRegistry).enqueue(new retrofit2.Callback<UserRegistry>() {
            @Override
            public void onResponse(@NonNull Call<UserRegistry> call,
                                   @NonNull retrofit2.Response<UserRegistry> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserLogin userLogin = new UserLogin();
                    userLogin.setEmail(userRegistry.getEmail());
                    userLogin.setPassword(userRegistry.getPassword());
                    notifySuccess(TAG_REGISTRY);
                    loginUser(userLogin);

                } else {
                    notifyError(TAG_REGISTRY);
                }
                Log.d("TAG", "Call " + response);
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<UserRegistry> call,
                                  @NonNull Throwable t) {
                notifyError(TAG_REGISTRY);
                Log.d("TAG", "Call " + t);
            }
        });
    }

    private void retrofitBuilder() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Log.d("LOG", "interceptor "+ interceptor);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL) // Full base url part
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        userAPI = retrofit.create(UserAPI.class);
    }
}
