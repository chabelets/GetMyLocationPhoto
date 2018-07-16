package com.example.tom.registrationokhttp.network;

import com.example.tom.registrationokhttp.pojo.UserLogin;
import com.example.tom.registrationokhttp.pojo.UserRegistry;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserAPI {

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json; charset=UTF-8"
        })
        @POST("users/login")
        Call<UserLogin> getLoginUser(@Body UserLogin user);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json; charset=UTF-8"
        })
        @POST("users/register")
        Call<UserRegistry> getRegisterUser(@Body UserRegistry user);
}

