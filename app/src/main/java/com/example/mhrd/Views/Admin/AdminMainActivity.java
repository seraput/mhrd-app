package com.example.mhrd.Views.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.LoginActivity;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Laporan.AdminLaporanActivity;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class AdminMainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    ImageView option;
    Dialog dialog;
    ProgressDialog progressDialog;
    Button btHelp, btProfile, btLogout, btExit;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);

        HashMap<String, String> user = sessionManager.getUserDetail();

        option = findViewById(R.id.menu);
        dialog = new Dialog(AdminMainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btHelp = dialog.findViewById(R.id.btBantuan);
        btProfile = dialog.findViewById(R.id.btProfile);
        btLogout = dialog.findViewById(R.id.btLogout);
        btExit = dialog.findViewById(R.id.btCancel);

        btHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(AdminMainActivity.this, "Bantuan", Toast.LENGTH_SHORT).show();
            }
        });

        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(AdminMainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                final ProgressDialog progressDialog = new ProgressDialog(AdminMainActivity.this);
                progressDialog.setTitle("Terimakasih");
                progressDialog.setMessage("Tunggu Sebentar . . .");
                progressDialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Logout();
                    }

                }, 3000);
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

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
                                AdminMasterActivity.class));
                        overridePendingTransition(0,0);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.bantuan){
            startActivity(new Intent(AdminMainActivity.this, AdminMasterActivity.class));
        }
        else if (item.getItemId()==R.id.profile){
            startActivity(new Intent(AdminMainActivity.this, AdminLaporanActivity.class));
        }
        else if (item.getItemId()==R.id.logout){
            Logout();
        }
        return true;
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