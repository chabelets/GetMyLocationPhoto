package com.example.tom.registrationokhttp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tom.registrationokhttp.observer.Subscriber;
import com.example.tom.registrationokhttp.R;
import com.example.tom.registrationokhttp.app.App;
import com.example.tom.registrationokhttp.pojo.UserLogin;
import com.example.tom.registrationokhttp.utils.Starter;

public class LoginActivity extends AppCompatActivity implements Subscriber {

    public UserLogin user;
    EditText emailField;
    EditText passwordField;
    TextView registerTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (App.getAppSettings().isLogged()) {
            Starter.startMainActivity(this);
            Toast.makeText(LoginActivity.this, "Key values "
                    + App.getAppSettings().isLogged(),
                    Toast.LENGTH_SHORT).show();
        }

        emailField = findViewById(R.id.loginEditTextLoginActivity);
        passwordField = findViewById(R.id.passwordEditTextLoginActivity);
        emailField.requestFocus();

        Button loginButton = findViewById(R.id.loginButtonLoginActivity);
        loginButton.setOnClickListener(createLoginButtonListener());

        registerTextView = findViewById(R.id.registrationTextViewLoginActivity);
        registerTextView.setOnClickListener(createRegistrationListener());

        App.getObserver().addSubscriber(this);
    }

    private View.OnClickListener createRegistrationListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerTextView.getText() == getText(R.string.have_no_account_activity_login)) {
                    Starter.startSignInActivity(LoginActivity.this);
                }
            }
        };
    }

    private View.OnClickListener createLoginButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailField.getText().length() == 0 || passwordField.getText().length() == 0) {
                    registerTextView.setText(R.string.field_is_required_activity_login);
                } else {
                    CharSequence email = emailField.getText();
                    CharSequence password = passwordField.getText();

                    user = new UserLogin();
                    user.setEmail(email.toString());
                    user.setPassword(password.toString());

                    App.getObserver().loginUser(user);
                }
            }
        };
    }

    @Override
    public void onSuccess(String tag) {
        Starter.startMainActivity(this);
        Toast.makeText(LoginActivity.this, "notify SUCCESS" + tag, Toast.LENGTH_SHORT).show();
        Log.d("onSuccess", "notify SUCCESS"  + tag + ".");
    }

    @Override
    public void onError(String tag) {
        Toast.makeText(LoginActivity.this, "notify ERROR" + tag, Toast.LENGTH_SHORT).show();
        Log.d("onError", "notify ERROR"  + tag + ".");
    }

    @Override
    protected void onStop() {
        App.getObserver().removeSubscriber(this);
        App.getAppSettings().setIsLogged();
        finish();
        super.onStop();
    }

}


