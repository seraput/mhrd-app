package com.example.mhrd.Views.Admin.Master.Employe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhrd.Controller.BranchAdapter;
import com.example.mhrd.Controller.EmployeAdapter;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.BranchData;
import com.example.mhrd.Models.EmployeData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.Absent.mAbsentActivity;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Admin.Master.Branch.mBranchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mEmployeeActivity extends AppCompatActivity {

    ListView listView;
    EmployeAdapter employeAdapter;
    public static ArrayList<EmployeData> employeDataArrayList = new ArrayList<>();
    private String getBranch = Server.URL_API + "getEmploye.php";
    EmployeData employeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_employee);

        listView = findViewById(R.id.karyawanList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

//                startActivity(new Intent(getApplicationContext(), GuruJawabDetail.class)
//                        .putExtra("position", position));
            }
        });

        employeAdapter = new EmployeAdapter(mEmployeeActivity.this, employeDataArrayList);
        listView.setAdapter(employeAdapter);

        receiveData();
    }

    public void Filter(View view) {
    }

    public void back(View view) {
        startActivity(new Intent(mEmployeeActivity.this, AdminMasterActivity.class));
    }

    public void showAll(View view) {
    }

    public void addUser(View view) {
        startActivity(new Intent(mEmployeeActivity.this, NewEmployeActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(mEmployeeActivity.this, AdminMasterActivity.class));
    }

    public void receiveData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . .");
        progressDialog.show();
        String txtstat = "Active";

        StringRequest request = new StringRequest(Request.Method.POST, getBranch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        employeDataArrayList.clear();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nik = object.getString("nik");
                                    String nama = object.getString("nama");
                                    String email = object.getString("email");
                                    String telp = object.getString("telp");
                                    String kelamin = object.getString("kelamin");
                                    String provinsi = object.getString("provinsi");
                                    String kota = object.getString("kota");
                                    String kec = object.getString("kec");
                                    String alamat = object.getString("alamat");
                                    String level = object.getString("level");
                                    String status = object.getString("status");

                                    if (jsonArray.length() < 1) {
                                        progressDialog.dismiss();
                                        Toast.makeText(mEmployeeActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        employeData = new EmployeData(nik, nama, email, telp, kelamin, provinsi, kota, kec, alamat, level, status);
                                        employeDataArrayList.add(employeData);
                                        employeAdapter.notifyDataSetChanged();
                                        progressDialog.dismiss();
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mEmployeeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("status", txtstat);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}