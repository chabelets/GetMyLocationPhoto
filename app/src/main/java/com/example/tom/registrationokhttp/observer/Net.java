package com.example.tom.registrationokhttp.observer;

import com.example.tom.registrationokhttp.pojo.UserLogin;
import com.example.tom.registrationokhttp.pojo.UserRegistry;

public interface Net extends Subscription {

    void loginUser(UserLogin userLogin);

    void registrationUser(UserRegistry userRegistry);
}

