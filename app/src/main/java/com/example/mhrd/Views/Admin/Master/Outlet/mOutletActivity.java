package com.example.mhrd.Views.Admin.Master.Outlet;

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
import com.example.mhrd.Controller.OutletAdapter;
import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.BranchData;
import com.example.mhrd.Models.EmployeData;
import com.example.mhrd.Models.OutletData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Admin.Master.Branch.mBranchActivity;
import com.example.mhrd.Views.Admin.Master.Employe.mEmployeeActivity;
import com.example.mhrd.Views.Spv.Master.SpvMasterActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mOutletActivity extends AppCompatActivity {

    ListView listView;
    OutletAdapter outletAdapter;
    public static ArrayList<OutletData> outletDataArrayList = new ArrayList<>();
    private String getBranch = Server.URL_API + "getOutlet.php";
    private String NonActive = Server.URL_API + "nonActiveOutlet.php";
    private String update = Server.URL_API + "update_outlet.php";
    OutletData outletData;
    SessionManager sessionManager;
    String getLevel;
    TextView tvLevel;
    Button closed, submit;
    Dialog dialog;
    MaterialEditText oId, oName, oEmail, oTelp, oAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_outlet);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getLevel = user.get(SessionManager.LEVEL);

        tvLevel = findViewById(R.id.txtLevel);
        tvLevel.setText(getLevel);

        listView = findViewById(R.id.outletList);
        outletAdapter = new OutletAdapter(mOutletActivity.this, outletDataArrayList);
        listView.setAdapter(outletAdapter);
        receiveData();

        dialog = new Dialog(mOutletActivity.this);
        dialog.setContentView(R.layout.cd_employe);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        closed = dialog.findViewById(R.id.cdEmployeClose);
        submit = dialog.findViewById(R.id.cdEmployeSubmit);
        oId = dialog.findViewById(R.id.cdEmployeId);
        oName = dialog.findViewById(R.id.cdEmployeNama);
        oEmail = dialog.findViewById(R.id.cdEmployeEmail);
        oTelp = dialog.findViewById(R.id.cdEmployeTelp);
        oAlamat = dialog.findViewById(R.id.cdEmployeAlamat);

        closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOutlet();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogItem = {"View Data", "Non-Active"};
                builder.setTitle(outletDataArrayList.get(position).getNama());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                dialog.show();
                                oId.setText(outletDataArrayList.get(position).getId());
                                oName.setText(outletDataArrayList.get(position).getNama());
                                oEmail.setText(outletDataArrayList.get(position).getType());
                                oTelp.setText(outletDataArrayList.get(position).getTelp());
                                oAlamat.setText(outletDataArrayList.get(position).getAlamat());
                                break;
                            case 1:
                                final String getID = outletDataArrayList.get(position).getId();
                                final ProgressDialog progressDialog = new ProgressDialog(mOutletActivity.this);
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
                                                        Toast.makeText(mOutletActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    System.out.println(e.toString());
                                                    progressDialog.dismiss();
                                                    Toast.makeText(mOutletActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new com.android.volley.Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println(error.toString());
                                                progressDialog.dismiss();
                                                Toast.makeText(mOutletActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("id", getID);
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(mOutletActivity.this);
                                requestQueue.add(stringRequest);

                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

    }

    public void updateOutlet(){
        final String getID = oId.getText().toString();
        final String getNama = oName.getText().toString();
        final String getEmail = oEmail.getText().toString();
        final String getTelp = oTelp.getText().toString();
        final String getAlamat = oAlamat.getText().toString();
//        Toast.makeText(this, "ID : "+getID, Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = new ProgressDialog(mOutletActivity.this);
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
                                Toast.makeText(mOutletActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                            progressDialog.dismiss();
                            Toast.makeText(mOutletActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(mOutletActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getID);
                params.put("nama", getNama);
                params.put("type", getEmail);
                params.put("telp", getTelp);
                params.put("alamat", getAlamat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mOutletActivity.this);
        requestQueue.add(stringRequest);
    }

    public void back(View view) {
//        startActivity(new Intent(mOutletActivity.this, AdminMasterActivity.class));
        final String txtLevel = tvLevel.getText().toString();
        if (txtLevel.equals("admin")){
            startActivity(new Intent(mOutletActivity.this, AdminMasterActivity.class));
        }
        else if (txtLevel.equals("spv")){
            startActivity(new Intent(mOutletActivity.this, SpvMasterActivity.class));
        }
    }

    public void addOutlet(View view) {
        startActivity(new Intent(mOutletActivity.this, NewOutletActivity.class));
    }

    public void Filter(View view) {
    }

    public void showAll(View view) {
    }

    @Override
    public void onBackPressed() {
//        startActivity(new Intent(mOutletActivity.this, AdminMasterActivity.class));
        final String txtLevel = tvLevel.getText().toString();
        if (txtLevel.equals("admin")){
            startActivity(new Intent(mOutletActivity.this, AdminMasterActivity.class));
        }
        else if (txtLevel.equals("spv")){
            startActivity(new Intent(mOutletActivity.this, SpvMasterActivity.class));
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
                        outletDataArrayList.clear();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String nama = object.getString("nama");
                                    String type = object.getString("type");
                                    String telp = object.getString("telp");
                                    String provinsi = object.getString("provinsi");
                                    String kota = object.getString("kota");
                                    String kec = object.getString("kec");
                                    String alamat = object.getString("alamat");
                                    String status = object.getString("status");

                                    if (jsonArray.length() < 1) {
                                        progressDialog.dismiss();
                                        Toast.makeText(mOutletActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        outletData = new OutletData(id, nama, type, telp, provinsi, kota, kec, alamat, status);
                                        outletDataArrayList.add(outletData);
                                        outletAdapter.notifyDataSetChanged();
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
                        Toast.makeText(mOutletActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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