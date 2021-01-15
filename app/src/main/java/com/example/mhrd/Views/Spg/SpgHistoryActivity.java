package com.example.mhrd.Views.Spg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.mhrd.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpgHistoryActivity extends AppCompatActivity {

    ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spg_history);

        myList = findViewById(R.id.list_history);

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);
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
                        startActivity(new Intent(getApplicationContext(),
                                SpgTaskActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id. history:
//                        startActivity(new Intent(getApplicationContext(),
//                                SpgHistoryActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //End ButtomNav
    }
}