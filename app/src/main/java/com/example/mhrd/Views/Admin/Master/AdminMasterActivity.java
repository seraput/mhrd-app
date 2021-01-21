package com.example.mhrd.Views.Admin.Master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import static com.example.mhrd.Helper.Volley.Server.URL_PDF;

public class AdminMasterActivity extends AppCompatActivity {

    CardView cProject, cEmploye, cOutlet, cAbsen, cBranch, cJobs;
    AlertDialog.Builder builder;
    private long backPressedTime;
    private Toast backToast;

    private String createdData1 = "https://mydbskripsi.000webhostapp.com/hrd-pdf/pdf/aktivitas_spv.php";
    private String createdData2 = "https://mydbskripsi.000webhostapp.com/hrd-pdf/pdf/data_karyawan.php";
    private String createdData3 = "https://mydbskripsi.000webhostapp.com/hrd-pdf/pdf/data_outlet.php";
    private String createdData4 = "https://mydbskripsi.000webhostapp.com/hrd-pdf/pdf/data_project.php";

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
                        final ProgressDialog progressDialog = new ProgressDialog(AdminMasterActivity.this);
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


    private void Data4(){
        StringRequest request = new StringRequest(Request.Method.POST, createdData1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Data1();
                        Data2();
                        Data3();
                        Toast.makeText(AdminMasterActivity.this, "PDF Siap...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),
                                AdminLaporanActivity.class));
                        overridePendingTransition(0,0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
                        Toast.makeText(AdminMasterActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMasterActivity.this);
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
                        Toast.makeText(AdminMasterActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMasterActivity.this);
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
                        Toast.makeText(AdminMasterActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMasterActivity.this);
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
                        Toast.makeText(AdminMasterActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMasterActivity.this);
        requestQueue.add(request);
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