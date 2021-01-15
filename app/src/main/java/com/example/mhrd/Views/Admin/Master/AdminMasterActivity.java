package com.example.mhrd.Views.Admin.Master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.AdminMainActivity;
import com.example.mhrd.Views.Admin.Laporan.AdminLaporanActivity;
import com.example.mhrd.Views.Admin.Master.Absent.mAbsentActivity;
import com.example.mhrd.Views.Admin.Master.Branch.mBranchActivity;
import com.example.mhrd.Views.Admin.Master.Employe.mEmployeeActivity;
import com.example.mhrd.Views.Admin.Master.Jobs.mJobsActivity;
import com.example.mhrd.Views.Admin.Master.Outlet.mOutletActivity;
import com.example.mhrd.Views.Admin.Master.Project.mProjectActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMasterActivity extends AppCompatActivity {

    CardView cProject, cEmploye, cOutlet, cAbsen, cBranch, cJobs;
    AlertDialog.Builder builder;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_master);

        cProject = findViewById(R.id.card_project);
        cEmploye = findViewById(R.id.card_employe);
        cOutlet = findViewById(R.id.card_outlet);
        cAbsen = findViewById(R.id.card_absen);
        cBranch = findViewById(R.id.card_branch);
        cJobs = findViewById(R.id.card_jobs);

        cBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMasterActivity.this, mBranchActivity.class));
                overridePendingTransition(0,0);
            }
        });

        cProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMasterActivity.this, mProjectActivity.class));
                overridePendingTransition(0,0);
            }
        });


        cAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMasterActivity.this, mAbsentActivity.class));
                overridePendingTransition(0,0);
            }
        });


        cOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMasterActivity.this, mOutletActivity.class));
                overridePendingTransition(0,0);
            }
        });


        cEmploye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMasterActivity.this, mEmployeeActivity.class));
                overridePendingTransition(0,0);
            }
        });

        cJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMasterActivity.this, mJobsActivity.class));
                overridePendingTransition(0,0);
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
                                AdminMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.master:
//                        startActivity(new Intent(getApplicationContext(),
//                                AdminMasterActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.laporan:
                        startActivity(new Intent(getApplicationContext(),
                                AdminLaporanActivity.class));
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