package com.example.mhrd.Views.Spv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mhrd.MenuTesting;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Laporan.AdminLaporanActivity;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Spg.SpgMainActivity;
import com.example.mhrd.Views.Spv.Activity.SpvTaskActivity;
import com.example.mhrd.Views.Spv.Master.SpvMasterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpvMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spv_main);


        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
//                        startActivity(new Intent(getApplicationContext(),
//                                AdminMainActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.master:
                        startActivity(new Intent(getApplicationContext(),
                                SpvMasterActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.activity:
                        startActivity(new Intent(getApplicationContext(),
                                SpvTaskActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //End ButtomNav
    }

    public void back(View view) {
        startActivity(new Intent(SpvMainActivity.this, MenuTesting.class));
        overridePendingTransition(0,0);
    }
}