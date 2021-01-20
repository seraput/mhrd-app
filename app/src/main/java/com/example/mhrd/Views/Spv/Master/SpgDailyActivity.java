package com.example.mhrd.Views.Spv.Master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.mhrd.Controller.DailyAdapter;
import com.example.mhrd.Controller.EmployeAdapter;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.BranchData;
import com.example.mhrd.Models.DailyData;
import com.example.mhrd.Models.EmployeData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.Branch.mBranchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpgDailyActivity extends AppCompatActivity {

    ListView list;
    DailyAdapter dailyAdapter;
    public static ArrayList<DailyData> dailyDataArrayList = new ArrayList<>();
    private String getBranch = Server.URL_API + "getDaily.php";
    DailyData dailyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spg_daily);

        list = findViewById(R.id.listDaily);

        dailyAdapter = new DailyAdapter(SpgDailyActivity.this, dailyDataArrayList);
        list.setAdapter(dailyAdapter);

        receiveData();
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
                        dailyDataArrayList.clear();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String jobs_id = object.getString("jobs_id");
                                    String branch = object.getString("branch");
                                    String project = object.getString("project");
                                    String outlet = object.getString("outlet");
                                    String product = object.getString("product");
                                    String qty = object.getString("qty");
                                    String image = object.getString("image");
                                    String tanggal = object.getString("tanggal");
                                    String user_id = object.getString("user_id");
                                    String user_nama = object.getString("user_nama");

                                    if (jsonArray.length() < 1) {
                                        progressDialog.dismiss();
                                        Toast.makeText(SpgDailyActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dailyData = new DailyData(id, jobs_id, branch, project, outlet, product, qty, image, tanggal, user_id, user_nama);
                                        dailyDataArrayList.add(dailyData);
                                        dailyAdapter.notifyDataSetChanged();
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
                        Toast.makeText(SpgDailyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void back(View view) {
        startActivity(new Intent(SpgDailyActivity.this, SpvMasterActivity.class));
        overridePendingTransition(0,0);
    }
}