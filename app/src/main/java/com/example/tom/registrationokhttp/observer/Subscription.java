package com.example.tom.registrationokhttp.observer;

public interface Subscription {

    void addSubscriber(Subscriber subscriber);

    void removeSubscriber(Subscriber subscriber);

    void notifySuccess(String tag);

    void notifyError(String tag);
}

