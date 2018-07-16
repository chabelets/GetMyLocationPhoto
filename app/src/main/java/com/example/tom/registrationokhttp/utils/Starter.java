package com.example.tom.registrationokhttp.utils;

import android.content.Context;
import android.content.Intent;

import com.example.tom.registrationokhttp.ui.AddPhotoActivityTest;
import com.example.tom.registrationokhttp.ui.LoginActivity;
import com.example.tom.registrationokhttp.ui.MainActivity;
import com.example.tom.registrationokhttp.ui.SignInActivity;

public class Starter {

    public static void startLoginActivity(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    public static void startSignInActivity(Context context) {
        Intent starter = new Intent(context, SignInActivity.class);
        context.startActivity(starter);
    }

    public static void startMainActivity(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    public static void startAddPhotoActivity(Context context) {
        Intent starter = new Intent(context, AddPhotoActivityTest.class);
        context.startActivity(starter);
    }
}
