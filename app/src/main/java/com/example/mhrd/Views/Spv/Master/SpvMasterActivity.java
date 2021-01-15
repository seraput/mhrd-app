package com.example.mhrd.Views.Spv.Master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mhrd.R;
import com.example.mhrd.Views.Spv.Activity.SpvTaskActivity;
import com.example.mhrd.Views.Spv.SpvMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpvMasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spv_master);

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.master);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),
                                SpvMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.master:
//                        startActivity(new Intent(getApplicationContext(),
//                                SpvMasterActivity.class));
//                        overridePendingTransition(0,0);
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
}