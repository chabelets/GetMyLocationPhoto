package com.example.tom.registrationokhttp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tom.registrationokhttp.observer.Subscriber;
import com.example.tom.registrationokhttp.R;
import com.example.tom.registrationokhttp.app.App;
import com.example.tom.registrationokhttp.app.Constants;
import com.example.tom.registrationokhttp.pojo.UserRegistry;
import com.example.tom.registrationokhttp.utils.Starter;

public class SignInActivity extends AppCompatActivity implements Subscriber {

    public UserRegistry user;
    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        Button registerButton = findViewById(R.id.registerButton);
        View.OnClickListener registerButtonClickListener = createRegisterButtonClickListener();
        registerButton.setOnClickListener(registerButtonClickListener);
        App.getObserver().addSubscriber(this);
    }

    public View.OnClickListener createRegisterButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailField.getText().length() == 0 || passwordField.getText().length() == 0) {
                    Toast.makeText(SignInActivity.this,
                            R.string.field_is_required_activity_login,
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    CharSequence email = emailField.getText();
                    CharSequence password = passwordField.getText();
                    user = new UserRegistry();
                    user.setEmail(email.toString());
                    user.setPassword(password.toString());
                    App.getObserver().registrationUser(user);
                }
            }

        };
    }

    @Override
    public void onSuccess(String tag) {
        switch (tag) {
            case Constants.TAG_LOGIN:
                Starter.startMainActivity(this);
                Toast.makeText(SignInActivity.this, "notify Success " + tag, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        Log.d("TAG", "notify SUCCESS SignIn " + tag);
    }

    @Override
    public void onError(String tag) {
        Toast.makeText(SignInActivity.this, "notify Error " + tag, Toast.LENGTH_SHORT).show();
        Log.d("TAG", "notify ERROR SignIn" + tag);
    }

    @Override
    protected void onDestroy() {
        App.getObserver().removeSubscriber(this);
        super.onDestroy();
    }
}







