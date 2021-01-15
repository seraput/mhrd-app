package com.example.mhrd.Views.Admin.Master.Jobs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.mhrd.Controller.JobsAdapter;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.BranchData;
import com.example.mhrd.Models.JobsData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Admin.Master.Branch.mBranchActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mJobsActivity extends AppCompatActivity {

    ImageView Add;
    MaterialEditText mtName, mtAddress;
    Button btCancel, btSimpan;
    private String InsertAPI = Server.URL_API + "insertBranch.php";
    private String getBranch = Server.URL_API + "getJobs.php";
    JobsAdapter jobsAdapter;
    public static ArrayList<JobsData> jobsDataArrayList = new ArrayList<>();
    JobsData jobsData;
    ListView list;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_jobs);


        list = findViewById(R.id.jobsList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

//                startActivity(new Intent(getApplicationContext(), GuruJawabDetail.class)
//                        .putExtra("position", position));
            }
        });

        jobsAdapter = new JobsAdapter(mJobsActivity.this, jobsDataArrayList);
        list.setAdapter(jobsAdapter);
        jobsDataArrayList.clear();

//        Add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(mJobsActivity.this, AddJobsActivity.class));
//                overridePendingTransition(0,0);
//            }
//        });
//
//        btCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

//        btSimpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String txtName = mtName.getText().toString();
//                String txtAddress = mtAddress.getText().toString();
//
//                if (txtName.isEmpty() || txtAddress.isEmpty()) {
//                    Toast.makeText(mJobsActivity.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Insert();
//                }
//            }
//        });
        //Get Data
        receiveData();
    }

    public void Filter(View view) {
        receiveData();
    }

    public void back(View view) {
        startActivity(new Intent(mJobsActivity.this, AdminMasterActivity.class));
        overridePendingTransition(0,0);
        jobsDataArrayList.clear();
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
                        jobsDataArrayList.clear();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String branch = object.getString("branch");
                                    String project = object.getString("project");
                                    String outlet = object.getString("outlet");
                                    String user_id = object.getString("user_id");
                                    String user_nama = object.getString("user_nama");
                                    String start = object.getString("start");
                                    String end = object.getString("end");
                                    String status = object.getString("status");

                                    if (jsonArray.length() < 1) {
                                        progressDialog.dismiss();
                                        Toast.makeText(mJobsActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        jobsData = new JobsData(id, branch, project, outlet, user_id, user_nama, start, end, status);
                                        jobsDataArrayList.add(jobsData);
                                        jobsAdapter.notifyDataSetChanged();
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
                        Toast.makeText(mJobsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

//    private void Insert() {
//        final String txtName = mtName.getText().toString();
//        final String txtAlamat = mtAddress.getText().toString();
//        final ProgressDialog progressDialog = new ProgressDialog(mJobsActivity.this);
//        progressDialog.setMessage("Loading . . .");
//        progressDialog.show();
//        StringRequest request = new StringRequest(Request.Method.POST, InsertAPI,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.equalsIgnoreCase("success")) {
//                            Toast.makeText(mJobsActivity.this, "Berhasil!", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                            receiveData();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(mJobsActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", txtName);
//                params.put("alamat", txtAlamat);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(mJobsActivity.this);
//        requestQueue.add(request);
//    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(mJobsActivity.this, AdminMasterActivity.class));
        overridePendingTransition(0,0);
    }

    public void addJobs(View view) {
        startActivity(new Intent(mJobsActivity.this, AddJobsActivity.class));
        overridePendingTransition(0,0);
    }
}