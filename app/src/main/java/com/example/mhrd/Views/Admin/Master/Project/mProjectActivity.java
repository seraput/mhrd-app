package com.example.mhrd.Views.Admin.Master.Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Admin.Master.Employe.mEmployeeActivity;

public class mProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_project);
    }

    public void back(View view) {
        startActivity(new Intent(mProjectActivity.this, AdminMasterActivity.class));
    }

    public void newProect(View view) {
        startActivity(new Intent(mProjectActivity.this, NewProjectActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(mProjectActivity.this, AdminMasterActivity.class));
    }
}