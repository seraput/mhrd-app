package com.example.mhrd.Views.Spv.Master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.Absent.mAbsentActivity;
import com.example.mhrd.Views.Admin.Master.Employe.mEmployeeActivity;
import com.example.mhrd.Views.Admin.Master.Jobs.mJobsActivity;
import com.example.mhrd.Views.Admin.Master.Outlet.mOutletActivity;
import com.example.mhrd.Views.Spv.Activity.SpvTaskActivity;
import com.example.mhrd.Views.Spv.SpvMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpvMasterActivity extends AppCompatActivity {

    CardView cvDaily, cvOutlet, cvAbsen, cvJobs;

    private long backPressedTime;
    private Toast backToast;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spv_master);

        cvDaily = findViewById(R.id.card_daily);
        cvOutlet = findViewById(R.id.card_outlet);
        cvAbsen = findViewById(R.id.card_absen);
        cvJobs = findViewById(R.id.card_jobs);

        cvDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                startActivity(new Intent(SpvMasterActivity.this, SpgDailyActivity.class));
            }
        });

        cvJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                startActivity(new Intent(SpvMasterActivity.this, mJobsActivity.class));
            }
        });

        cvAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpvMasterActivity.this, mAbsentActivity.class));
            }
        });

        cvOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpvMasterActivity.this, mOutletActivity.class));
                //
            }
        });

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

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        } else {
            backToast = Toast.makeText(this, "Tekan Lagi Untuk Keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}