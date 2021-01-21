package com.example.mhrd.Views.Admin.Laporan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.AdminMainActivity;
import com.example.mhrd.Views.Admin.Laporan.Ext.ItemClickListener;
import com.example.mhrd.Views.Admin.Laporan.Ext.PDFAdapter;
import com.example.mhrd.Views.Admin.Laporan.Ext.PDFModel;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AdminLaporanActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    private long backPressedTime;
    private Toast backToast;

    RecyclerView recyclerView;
    public static List<PDFModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_laporan);

        recyclerView = findViewById(R.id.RV);
        list = new ArrayList<>();
        list.add(new PDFModel("Data_Outlet","https://mydbskripsi.000webhostapp.com/hrd-pdf/files/dataOutlet.pdf"));
        list.add(new PDFModel("Data_Project", "https://mydbskripsi.000webhostapp.com/hrd-pdf/files/dataProject.pdf"));
        list.add(new PDFModel("Aktivitas_Spv", "https://mydbskripsi.000webhostapp.com/hrd-pdf/files/aktivitasSPV.pdf"));
        list.add(new PDFModel("Data_Karyawan", "https://mydbskripsi.000webhostapp.com/hrd-pdf/files/dataKaryawan.pdf"));

        recyclerView.setLayoutManager(new GridLayoutManager(AdminLaporanActivity.this, 2));

        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(AdminLaporanActivity.this,PDFActivity.class);
                //intent.putExtra("url",list.get(position).getPdfUrl());
                intent.putExtra("position",position);
                startActivity(intent);
            }
        };
        PDFAdapter adapter = new PDFAdapter(list,this,itemClickListener);
        recyclerView.setAdapter(adapter);

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.laporan);
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
                        startActivity(new Intent(getApplicationContext(),
                                AdminMasterActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.laporan:
//                        startActivity(new Intent(getApplicationContext(),
//                                RiwayatActivity.class));
//                        overridePendingTransition(0,0);
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