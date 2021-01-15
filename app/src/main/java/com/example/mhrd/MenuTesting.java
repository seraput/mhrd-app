package com.example.mhrd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mhrd.Views.Admin.AdminMainActivity;
import com.example.mhrd.Views.Spg.SpgMainActivity;
import com.example.mhrd.Views.Spv.SpvMainActivity;

public class MenuTesting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_testing);
    }

    public void login(View view) {
        startActivity(new Intent(MenuTesting.this, LoginActivity.class));
        overridePendingTransition(0,0);
    }

    public void adm(View view) {
        startActivity(new Intent(MenuTesting.this, AdminMainActivity.class));
        overridePendingTransition(0,0);
    }

    public void spg(View view) {
        startActivity(new Intent(MenuTesting.this, SpgMainActivity.class));
        overridePendingTransition(0,0);
    }

    public void spv(View view) {
        startActivity(new Intent(MenuTesting.this, SpvMainActivity.class));
        overridePendingTransition(0,0);
    }
}