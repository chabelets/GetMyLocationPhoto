package com.example.tom.registrationokhttp.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class NetSubscription implements Net {
    private List<Subscriber> subscribers = new ArrayList<>();

    @Override
    public void addSubscriber(Subscriber newSubscriber) {
        if (!subscribers.contains(newSubscriber)) {
            subscribers.add(newSubscriber);
        }
    }

    @Override
    public void removeSubscriber(Subscriber removedSubscriber) {
        if (subscribers.contains(removedSubscriber)) {
            subscribers.remove(removedSubscriber);
        }
    }

    @Override
    public void notifySuccess(String tag) {
        for (Subscriber subscriber : subscribers) {
            if (!subscribers.isEmpty()) {
                subscriber.onSuccess(tag);
            }
        }
    }

    @Override
    public void notifyError(String tag) {
        for (Subscriber subscriber : subscribers) {
            if (!subscribers.isEmpty()) {
                subscriber.onError(tag);
            }
        }
    }
}
