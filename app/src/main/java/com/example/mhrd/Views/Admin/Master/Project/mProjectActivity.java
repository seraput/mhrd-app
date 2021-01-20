package com.example.mhrd.Views.Admin.Master.Project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhrd.Controller.ProjectAdapter;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.ProjectData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.Absent.mAbsentActivity;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Spg.Aktivitas.SpgAbsentActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mProjectActivity extends AppCompatActivity {

    ListView list;
    ProjectAdapter projectAdapter;
    public static ArrayList<ProjectData> projectDataArrayList = new ArrayList<>();
    private String getProjectAll = Server.URL_API + "getProjectAll.php";
    private String NonActive = Server.URL_API + "nonActive.php";
    ProjectData projectData;
    Dialog dialog;
    Button saved, closed;
    MaterialEditText pid, pname, bname, area, periode;
    Spinner status;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_project);

        list = findViewById(R.id.list_project);

        projectAdapter = new ProjectAdapter(mProjectActivity.this, projectDataArrayList);
        list.setAdapter(projectAdapter);
        projectDataArrayList.clear();
        receiveDate();

        dialog = new Dialog(mProjectActivity.this);
        dialog.setContentView(R.layout.cd_project_view);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        closed = dialog.findViewById(R.id.cdpClosed);
        pid = dialog.findViewById(R.id.cdpId);
        pname = dialog.findViewById(R.id.cdpName);
        bname = dialog.findViewById(R.id.cdpBranch);
        area = dialog.findViewById(R.id.cdpArea);
        periode = dialog.findViewById(R.id.cdpPeriode);

        closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogItem = {"View Data", "Non-Active"};
                builder.setTitle(projectDataArrayList.get(position).getNama());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                dialog.show();
                                pid.setText(projectDataArrayList.get(position).getId());
                                pname.setText(projectDataArrayList.get(position).getNama());
                                bname.setText(projectDataArrayList.get(position).getB_name());
                                area.setText(projectDataArrayList.get(position).getArea());
                                periode.setText(projectDataArrayList.get(position).getBulan());
                                break;
                            case 1:
                                final String getID = projectDataArrayList.get(position).getId();
                                final ProgressDialog progressDialog = new ProgressDialog(mProjectActivity.this);
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
                                                        receiveDate();
                                                        Toast.makeText(mProjectActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    System.out.println(e.toString());
                                                    progressDialog.dismiss();
                                                    Toast.makeText(mProjectActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new com.android.volley.Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println(error.toString());
                                                progressDialog.dismiss();
                                                Toast.makeText(mProjectActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
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
                                RequestQueue requestQueue = Volley.newRequestQueue(mProjectActivity.this);
                                requestQueue.add(stringRequest);

                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

    }

    private void SaveEditDetail() {

    }

    public void receiveDate() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . .");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, getProjectAll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        projectDataArrayList.clear();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String nama = object.getString("nama");
                                    String b_code = object.getString("b_code");
                                    String b_name = object.getString("b_name");
                                    String area = object.getString("area");
                                    String bulan = object.getString("bulan");
                                    String status = object.getString("status");

                                    if (jsonArray.length() < 1) {
                                        progressDialog.dismiss();
                                        Toast.makeText(mProjectActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        projectData = new ProjectData(id, nama, b_code, b_name, area, bulan, status);
                                        projectDataArrayList.add(projectData);
                                        projectAdapter.notifyDataSetChanged();
                                        progressDialog.dismiss();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mProjectActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
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