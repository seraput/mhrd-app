package com.example.mhrd.Views.Spg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mhrd.MenuTesting;
import com.example.mhrd.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpgTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spg_task);

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.aktivitas);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),
                                SpgMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.aktivitas:
//                        startActivity(new Intent(getApplicationContext(),
//                                SpgTaskActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id. history:
                        startActivity(new Intent(getApplicationContext(),
                                SpgHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //End ButtomNav
    }

}