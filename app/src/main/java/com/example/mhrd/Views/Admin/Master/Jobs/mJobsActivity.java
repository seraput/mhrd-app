package com.example.mhrd.Views.Admin.Master.Jobs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.BranchData;
import com.example.mhrd.Models.JobsData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Admin.Master.Branch.mBranchActivity;
import com.example.mhrd.Views.Admin.Master.Project.mProjectActivity;
import com.example.mhrd.Views.Spv.Master.SpvMasterActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mJobsActivity extends AppCompatActivity {

    private String getBranch = Server.URL_API + "getJobs.php";
    private String NonActive = Server.URL_API + "jobs-nonactive.php";
    JobsAdapter jobsAdapter;
    public static ArrayList<JobsData> jobsDataArrayList = new ArrayList<>();
    JobsData jobsData;
    ListView list;
    Button closed;
    Dialog dialog;
    MaterialEditText jid, pname, oname, juser, jperiode;
    SessionManager sessionManager;
    String getLevel;
    TextView tvLevel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_jobs);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getLevel = user.get(SessionManager.LEVEL);

        list = findViewById(R.id.jobsList);

        tvLevel = findViewById(R.id.txtLevel);
        tvLevel.setText(getLevel);

        jobsAdapter = new JobsAdapter(mJobsActivity.this, jobsDataArrayList);
        list.setAdapter(jobsAdapter);
        jobsDataArrayList.clear();
        receiveData();

        dialog = new Dialog(mJobsActivity.this);
        dialog.setContentView(R.layout.cd_jobs_view);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        closed = dialog.findViewById(R.id.cdjClosed);
        jid = dialog.findViewById(R.id.cdjId);
        pname = dialog.findViewById(R.id.cdjProject);
        oname = dialog.findViewById(R.id.cdjOutlet);
        juser = dialog.findViewById(R.id.cdjUser);
        jperiode = dialog.findViewById(R.id.cdjPeriode);

        closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogItem = {"View Data", "Non-Active"};
                builder.setTitle(jobsDataArrayList.get(position).getP_name());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                dialog.show();
                                jid.setText(jobsDataArrayList.get(position).getId());
                                pname.setText(jobsDataArrayList.get(position).getP_name());
                                oname.setText(jobsDataArrayList.get(position).getOutlet_name());
                                juser.setText(jobsDataArrayList.get(position).getUser_nama());
                                jperiode.setText(jobsDataArrayList.get(position).getStart());
                                break;
                            case 1:
                                final String getID = jobsDataArrayList.get(position).getId();
                                final ProgressDialog progressDialog = new ProgressDialog(mJobsActivity.this);
                                progressDialog.setMessage("Connecting...");
                                progressDialog.show();

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, NonActive,
                                        new com.android.volley.Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                progressDialog.dismiss();
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    String success = jsonObject.getString("success");

                                                    if (success.equals("1")){
                                                        receiveData();
                                                        Toast.makeText(mJobsActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    System.out.println(e.toString());
                                                    progressDialog.dismiss();
                                                    Toast.makeText(mJobsActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new com.android.volley.Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println(error.toString());
                                                progressDialog.dismiss();
                                                Toast.makeText(mJobsActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("id", getID);
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(mJobsActivity.this);
                                requestQueue.add(stringRequest);

                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });


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
    }

    public void back(View view) {
//        startActivity(new Intent(mJobsActivity.this, AdminMasterActivity.class));
//        overridePendingTransition(0,0);

        final String txtLevel = tvLevel.getText().toString();
        if (txtLevel.equals("admin")){
            startActivity(new Intent(mJobsActivity.this, AdminMasterActivity.class));
            jobsDataArrayList.clear();
        }
        else if (txtLevel.equals("spv")){
            startActivity(new Intent(mJobsActivity.this, SpvMasterActivity.class));
            jobsDataArrayList.clear();
        }
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
                                    String p_id = object.getString("p_id");
                                    String p_name = object.getString("p_name");
                                    String branch = object.getString("branch");
                                    String user_id = object.getString("user_id");
                                    String user_nama = object.getString("user_nama");
                                    String telp = object.getString("telp");
                                    String outlet_id = object.getString("outlet_id");
                                    String outlet_name = object.getString("outlet_name");
                                    String alamat = object.getString("alamat");
                                    String kec = object.getString("kec");
                                    String kota = object.getString("kota");
                                    String provinsi = object.getString("provinsi");
                                    String start = object.getString("start");
                                    String status = object.getString("status");

                                    if (jsonArray.length() < 1) {
                                        progressDialog.dismiss();
                                        Toast.makeText(mJobsActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        jobsData = new JobsData(id, p_id, p_name, branch, user_id, user_nama, telp, outlet_id, outlet_name, alamat, kec, kota, provinsi, start, status);
                                        jobsDataArrayList.add(jobsData);
                                        jobsAdapter.notifyDataSetChanged();
                                        System.out.println("Nilai " + jsonArray.length());
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
        final String txtLevel = tvLevel.getText().toString();
        if (txtLevel.equals("admin")){
            startActivity(new Intent(mJobsActivity.this, AdminMasterActivity.class));
            jobsDataArrayList.clear();
        }
        else if (txtLevel.equals("spv")){
            startActivity(new Intent(mJobsActivity.this, SpvMasterActivity.class));
            jobsDataArrayList.clear();
        }
    }

    public void addJobs(View view) {
        startActivity(new Intent(mJobsActivity.this, AddJobsActivity.class));
        overridePendingTransition(0,0);
    }
}