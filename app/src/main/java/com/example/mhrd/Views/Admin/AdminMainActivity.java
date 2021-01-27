package com.example.mhrd.Views.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.LoginActivity;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Laporan.AdminLaporanActivity;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

import static com.example.mhrd.Helper.Volley.Server.URL_PDF;

public class AdminMainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    ImageView option;
    Dialog dialog;
    ProgressDialog progressDialog;
    Button btAddUser, btProfile, btLogout, btExit;
    String getNama;
    TextView tvName;

    private String createdData1 = "https://mydbskripsi.000webhostapp.com/hrd-pdf/pdf/aktivitas_spv.php";
    private String createdData2 = "https://mydbskripsi.000webhostapp.com/hrd-pdf/pdf/data_karyawan.php";
    private String createdData3 = "https://mydbskripsi.000webhostapp.com/hrd-pdf/pdf/data_outlet.php";
    private String createdData4 = "https://mydbskripsi.000webhostapp.com/hrd-pdf/pdf/data_project.php";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getNama = user.get(SessionManager.EMAIL);

        tvName = findViewById(R.id.txtName);
        tvName.setText(getNama);

        final MediaPlayer mpmaster = MediaPlayer.create(this, R.raw.master);
        final MediaPlayer mplaporan = MediaPlayer.create(this, R.raw.laporan);

        option = findViewById(R.id.menu);
        dialog = new Dialog(AdminMainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btProfile = dialog.findViewById(R.id.btProfile);
        btLogout = dialog.findViewById(R.id.btLogout);
        btExit = dialog.findViewById(R.id.btCancel);
        btAddUser = dialog.findViewById(R.id.btAddUser);

        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(AdminMainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
            }
        });

        btAddUser.setVisibility(View.GONE);

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
                        mpmaster.start();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.laporan:
                        final ProgressDialog progressDialog = new ProgressDialog(AdminMainActivity.this);
                        mplaporan.start();
                        progressDialog.setMessage("Tunggu Sebentar . . .");
                        progressDialog.show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Data4();
                            }
                        }, 3000);
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

    private void Data4(){
        StringRequest request = new StringRequest(Request.Method.POST, createdData1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Data1();
                        Data2();
                        Data3();
                        Toast.makeText(AdminMainActivity.this, "PDF Siap...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),
                                AdminLaporanActivity.class));
                        overridePendingTransition(0,0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
                        Toast.makeText(AdminMainActivity.this, "Error Connection 1" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMainActivity.this);
        requestQueue.add(request);
    }

    private void Data3(){
        StringRequest request = new StringRequest(Request.Method.POST, createdData3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(AdmDashboardActivity.this, "Laporan 3", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminMainActivity.this, "Error Connection 2" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMainActivity.this);
        requestQueue.add(request);
    }

    private void Data1(){
        StringRequest request = new StringRequest(Request.Method.POST, createdData2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(AdmDashboardActivity.this, "Laporan 3", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminMainActivity.this, "Error Connection 3" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMainActivity.this);
        requestQueue.add(request);
    }

    private void Data2(){
        StringRequest request = new StringRequest(Request.Method.POST, createdData4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(AdmDashboardActivity.this, "Laporan 4", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminMainActivity.this, "Error Connection 4" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMainActivity.this);
        requestQueue.add(request);
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