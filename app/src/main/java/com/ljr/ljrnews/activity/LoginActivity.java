package com.ljr.ljrnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ljr.ljrnews.R;
import com.ljr.ljrnews.bean.UserBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.username)
    EditText mUsername;
    @Bind(R.id.til_username)
    TextInputLayout mTilUsername;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.til_password)
    TextInputLayout mTilPassword;
    @Bind(R.id.login)
    Button mLogin;
    @Bind(R.id.register)
    TextView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(LoginActivity.this, "f83c7903132eea0295b25c60662fc548");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.login, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                String userName = mTilUsername.getEditText().getText().toString().trim();
                String passWord = mTilPassword.getEditText().getText().toString().trim();
                BmobUser.loginByAccount(userName, passWord, new LogInListener<UserBean>() {

                    @Override
                    public void done(UserBean userBean, BmobException e) {
                        if (userBean != null) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                overridePendingTransition(R.anim.push_up_in,
                        R.anim.push_up_out);
                finish();
                break;
        }
    }
}
