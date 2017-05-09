package com.app.tychodemo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.tychodemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        getmToolbar().setVisibility(View.GONE);
        ButterKnife.bind(this);
    }



    @OnClick(R.id.login_login)
    public void onClick() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }
}
