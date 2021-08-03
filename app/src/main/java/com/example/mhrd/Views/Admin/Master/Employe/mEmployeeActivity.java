package com.example.mhrd.Views.Admin.Master.Employe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.mhrd.Controller.EmployeAdapter;
import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.BranchData;
import com.example.mhrd.Models.EmployeData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.Absent.mAbsentActivity;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Admin.Master.Branch.mBranchActivity;
import com.example.mhrd.Views.Admin.Master.Jobs.mJobsActivity;
import com.example.mhrd.Views.Spv.Master.SpvMasterActivity;
import com.example.mhrd.Views.Spv.SpvMainActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

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
    private String NonActive = Server.URL_API + "nonActiveEmploye.php";
    private String update = Server.URL_API + "update_employe.php";
    EmployeData employeData;
    SessionManager sessionManager;
    String getLevel;
    TextView tvLevel;
    Button closed, submit;
    Dialog dialog;
    MaterialEditText eId, eName, eEmail, eTelp, eAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_employee);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getLevel = user.get(SessionManager.LEVEL);

        tvLevel = findViewById(R.id.txtLevel);
        tvLevel.setText(getLevel);

        listView = findViewById(R.id.karyawanList);
        employeAdapter = new EmployeAdapter(mEmployeeActivity.this, employeDataArrayList);
        listView.setAdapter(employeAdapter);
        receiveData();

        dialog = new Dialog(mEmployeeActivity.this);
        dialog.setContentView(R.layout.cd_employe);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        closed = dialog.findViewById(R.id.cdEmployeClose);
        submit = dialog.findViewById(R.id.cdEmployeSubmit);
        eId = dialog.findViewById(R.id.cdEmployeId);
        eName = dialog.findViewById(R.id.cdEmployeNama);
        eEmail = dialog.findViewById(R.id.cdEmployeEmail);
        eTelp = dialog.findViewById(R.id.cdEmployeTelp);
        eAlamat = dialog.findViewById(R.id.cdEmployeAlamat);

        closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmploye();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogItem = {"View Data", "Non-Active"};
                builder.setTitle(employeDataArrayList.get(position).getNama());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                dialog.show();
                                eId.setText(employeDataArrayList.get(position).getNik());
                                eName.setText(employeDataArrayList.get(position).getNama());
                                eEmail.setText(employeDataArrayList.get(position).getEmail());
                                eTelp.setText(employeDataArrayList.get(position).getTelp());
                                eAlamat.setText(employeDataArrayList.get(position).getAlamat());
                                break;
                            case 1:
                                final String getID = employeDataArrayList.get(position).getNik();
                                final ProgressDialog progressDialog = new ProgressDialog(mEmployeeActivity.this);
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

                                                    if (success.equals("1")) {
                                                        receiveData();
                                                        progressDialog.dismiss();
                                                        Toast.makeText(mEmployeeActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    System.out.println(e.toString());
                                                    progressDialog.dismiss();
                                                    Toast.makeText(mEmployeeActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new com.android.volley.Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println(error.toString());
                                                progressDialog.dismiss();
                                                Toast.makeText(mEmployeeActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("nik", getID);
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(mEmployeeActivity.this);
                                requestQueue.add(stringRequest);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    public void updateEmploye(){
        final String getID = eId.getText().toString();
        final String getNama = eName.getText().toString();
        final String getEmail = eEmail.getText().toString();
        final String getTelp = eTelp.getText().toString();
        final String getAlamat = eAlamat.getText().toString();
//        Toast.makeText(this, "ID : "+getID, Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = new ProgressDialog(mEmployeeActivity.this);
        progressDialog.setMessage("Connecting...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, update,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                receiveData();
                                dialog.dismiss();
                                Toast.makeText(mEmployeeActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                            progressDialog.dismiss();
                            Toast.makeText(mEmployeeActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(mEmployeeActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nik", getID);
                params.put("nama", getNama);
                params.put("email", getEmail);
                params.put("telp", getTelp);
                params.put("alamat", getAlamat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mEmployeeActivity.this);
        requestQueue.add(stringRequest);

    }


    public void back(View view) {
        final String txtLevel = tvLevel.getText().toString();
        if (txtLevel.equals("admin")){
            startActivity(new Intent(mEmployeeActivity.this, AdminMasterActivity.class));
        }
        else if (txtLevel.equals("spv")){
            startActivity(new Intent(mEmployeeActivity.this, SpvMainActivity.class));
        }
    }


    public void addUser(View view) {
        startActivity(new Intent(mEmployeeActivity.this, NewEmployeActivity.class));
    }

    @Override
    public void onBackPressed() {
        final String txtLevel = tvLevel.getText().toString();
        if (txtLevel.equals("admin")){
            startActivity(new Intent(mEmployeeActivity.this, AdminMasterActivity.class));
        }
        else if (txtLevel.equals("spv")){
            startActivity(new Intent(mEmployeeActivity.this, SpvMasterActivity.class));
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