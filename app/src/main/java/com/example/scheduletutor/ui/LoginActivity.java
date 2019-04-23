package com.example.scheduletutor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scheduletutor.R;
import com.example.scheduletutor.application.LoginApplication;
import com.example.scheduletutor.base.LoginPresenter;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.User.UserLocalStore;
import com.example.scheduletutor.model.UserResponse;
import com.example.scheduletutor.service.Login.LoginViewInterface;
import com.example.scheduletutor.service.LoginService;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface {

    @Inject
    LoginService loginService;
    private LoginPresenter presenter;

    @BindView(R.id.etUserName)
    EditText etUsername;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
        resolveDependency();
        initConfigView();
        presenter = new LoginPresenter(this);
    }

    private void resolveDependency() {
        ((LoginApplication) getApplication())
                .getLoginComponent()
                .Inject(this);
    }

    private void initConfigView() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserLogin();
            }
        });
    }

    private void checkUserLogin() {
        presenter.onCreate();
    }

    @Override
    public void complete(List<User> users) {
        Intent i =new Intent(LoginActivity.this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        UserLocalStore userLocalStore = new UserLocalStore(this);
        userLocalStore.storeUserData(users.get(0));
        userLocalStore.setUserLoggedIn(true);
        Toast.makeText(this, userLocalStore.getUserLoggedIn()+"", Toast.LENGTH_SHORT).show();
        i.putExtra("role",users.get(0).getUserRoleID());
        startActivity(i);
    }

    @Override
    public void error(String message) {
        Toast.makeText(this, "UserName atau password salah", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addUser(List<User> users) {

    }

    @Override
    public Observable<UserResponse> getUser() {
        JsonObject objAdd = new JsonObject();
        try {
            JsonArray arrData = new JsonArray();
            JsonObject objDetail = new JsonObject();
            objDetail.addProperty("userName", etUsername.getText().toString().trim());
            objDetail.addProperty("userPassword", etPassword.getText().toString().trim());
            arrData.add(objDetail);
            objAdd.add("data", arrData);
        } catch (JsonIOException e1) {
            e1.printStackTrace();
            Toast.makeText(this, e1+"", Toast.LENGTH_SHORT).show();
        }
        Log.d("apikey",objAdd.toString());
        return loginService.getUser(objAdd);
    }
}
