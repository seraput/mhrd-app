package com.example.mhrd.Views.Spg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.LoginActivity;
import com.example.mhrd.MenuTesting;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.AdminMainActivity;
import com.example.mhrd.Views.Spg.Aktivitas.SpgAbsentActivity;
import com.example.mhrd.Views.Spg.Task.SpgTugasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SpgMainActivity extends AppCompatActivity {

    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    String getID, getNama, getProject, getBranch, getProjectID;
    TextView tvNama, tvID, tvNama2, tvBranch, tvPid, tvPnama, tvDate;
    String myFormat = "dd MMMM yyyy HH:mm";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    private long backPressedTime;
    private Toast backToast;
    ImageView option;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spg_main);

        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();

        getID = user.get(SessionManager.ID);
        getNama = user.get(SessionManager.EMAIL);
        getBranch = user.get(SessionManager.BRANCH);
        getProjectID  = user.get(SessionManager.PROJECTID);
        getProject = user.get(SessionManager.PROJECT);

        tvID = findViewById(R.id.txtId);
        tvNama = findViewById(R.id.txtNama);
        tvNama2 = findViewById(R.id.txtNama2);
        tvBranch = findViewById(R.id.txtBranch);
        tvPid = findViewById(R.id.txtProjectID);
        tvPnama = findViewById(R.id.txtProjectName);
        tvDate = findViewById(R.id.txtDate);

        tvID.setText(getID);
        tvNama.setText(getNama);
        tvNama2.setText(getNama);
        tvBranch.setText(getBranch);
        tvPid.setText(getProjectID);
        tvPnama.setText(getProject);

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
//                        startActivity(new Intent(getApplicationContext(),
//                                TransaksiActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.aktivitas:
                        startActivity(new Intent(getApplicationContext(),
                                SpgAbsentActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id. history:
                        startActivity(new Intent(getApplicationContext(),
                                SpgTugasActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //End ButtomNav

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdf.format(c1.getTime());
        tvDate.setText(str1);
    }

    public void back(View view) {
        final Handler handler = new Handler();
        final ProgressDialog progressDialog = new ProgressDialog(SpgMainActivity.this);
        progressDialog.setTitle("Terimakasih");
        progressDialog.setMessage("Tunggu Sebentar . . .");
        progressDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Logout();
            }

        }, 3000);
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

    private void Logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
        editor.apply();
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}