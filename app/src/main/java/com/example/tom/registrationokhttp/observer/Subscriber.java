package com.example.tom.registrationokhttp.observer;


public interface Subscriber {

    void onSuccess(String tag);

    void onError(String tag);
}
