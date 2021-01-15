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
import android.widget.ImageView;
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
import com.example.mhrd.Controller.AbsenAdapter;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.AbsenData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.Absent.mAbsentActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddJobsActivity extends AppCompatActivity {

    Spinner spinner;
    ImageView btCari;
    Dialog dialog;
    ListView list;
    MaterialEditText mtPid;

    AbsenAdapter absenAdapter;
    public static ArrayList<AbsenData> absenDataArrayList = new ArrayList<>();
    private String getAbsentAll = Server.URL_API + "getAbsentAll.php";
    AbsenData absenData;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jobs);

        dialog = new Dialog(AddJobsActivity.this);
        dialog.setContentView(R.layout.custom_show_project);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        mtPid = findViewById(R.id.project_code);
        btCari = findViewById(R.id.btCari);

        list = dialog.findViewById(R.id.jobsList);
        absenAdapter = new AbsenAdapter(AddJobsActivity.this, absenDataArrayList);
        list.setAdapter(absenAdapter);
        btCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveAll();
                dialog.show();
            }
        });
//        absenDataArrayList.clear();

//        receiveAll();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(getApplicationContext(), PengajuanBmnActivity.class)
//                        .putExtra("position", position));
                mtPid.setText(absenDataArrayList.get(position).getP_nama());
            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(AddJobsActivity.this, mJobsActivity.class));
        overridePendingTransition(0,0);
    }

    public void receiveAll() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . .");
        progressDialog.show();
        String txtstat = "Active";

        StringRequest request = new StringRequest(Request.Method.POST, getAbsentAll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        absenDataArrayList.clear();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String u_id = object.getString("u_id");
                                    String u_nama = object.getString("u_nama");
                                    String p_id = object.getString("p_id");
                                    String p_nama = object.getString("p_nama");
                                    String tanggal = object.getString("tanggal");
                                    String jam = object.getString("jam");
                                    String image = object.getString("image");
                                    String keterangan = object.getString("keterangan");

                                    if (jsonArray.length() < 1) {
                                        progressDialog.dismiss();
                                        Toast.makeText(AddJobsActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        absenData = new AbsenData(id, u_id, u_nama, p_id, p_nama, tanggal, jam, image, keterangan);
                                        absenDataArrayList.add(absenData);
                                        absenAdapter.notifyDataSetChanged();
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
                        Toast.makeText(AddJobsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
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