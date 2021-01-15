package com.example.mhrd.Views.Admin.Master.Absent;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.mhrd.Controller.AbsenAdapter;
import com.example.mhrd.Controller.BranchAdapter;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.AbsenData;
import com.example.mhrd.Models.BranchData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Admin.Master.Branch.mBranchActivity;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class mAbsentActivity extends AppCompatActivity {

    MaterialEditText mtTgl, mtUrl;
    ImageView imgDate, imgFoto;
    Spinner spProject;
    String myFormat = "MMMM yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    Calendar myCalendar;
    ListView list;
    Dialog dialog;
    Button show, close;

    AbsenAdapter absenAdapter;
    public static ArrayList<AbsenData> absenDataArrayList = new ArrayList<>();
    private String getAbsentAll = Server.URL_API + "getAbsentAll.php";
    private String getAbsentDate = Server.URL_API + "getAbsentDate.php";
    AbsenData absenData;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_absent);

        mtTgl = findViewById(R.id.absenTgl);
        list = findViewById(R.id.absenList);
        imgDate = findViewById(R.id.btDate);
        myCalendar = Calendar.getInstance();

        absenAdapter = new AbsenAdapter(mAbsentActivity.this, absenDataArrayList);
        list.setAdapter(absenAdapter);
        absenDataArrayList.clear();

        dialog = new Dialog(mAbsentActivity.this);
        dialog.setContentView(R.layout.custom_show_absen);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        mtUrl = dialog.findViewById(R.id.urlImage);
        imgFoto = dialog.findViewById(R.id.imgAbsen);
        show = dialog.findViewById(R.id.btShow);
        close = dialog.findViewById(R.id.btClose);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
//                CharSequence[] dialogItem = {"View Data", "Delete Data"};
//                builder.setTitle(absenDataArrayList.get(position).getU_nama());
//
//                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface di, int i) {
//                        switch (i) {
//                            case 0:
//                                dialog.show();
////                                        .putExtra("position", position));
////                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
                dialog.show();
                mtUrl.setText(absenDataArrayList.get(position).getImage());

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String URL = mtUrl.getText().toString().trim();
                String U_R_L = Server.URL_IMAGE + URL;
                RequestOptions requestOptions = new RequestOptions()
                        .centerCrop()
                        .timeout(5000)
                        .error(R.drawable.ic_error);
                // Menjalankan proses dengan metode AsyncTask
//                new DownloadImage().execute(URL);

                Glide.with(mAbsentActivity.this)
                        .load(U_R_L)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                contentLoadingProgressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                contentLoadingProgressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .apply(requestOptions)
                        .into(imgFoto);
            }
        });


        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar today = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(mAbsentActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                myCalendar.set(Calendar.YEAR, selectedYear);
                                myCalendar.set(Calendar.MONTH, selectedMonth);
                                mtTgl.setText(sdf.format(myCalendar.getTime()));
                            }
                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setActivatedMonth(today.get(Calendar.MONTH))
                        .setMinYear(2019)
                        .setActivatedYear(today.get(Calendar.YEAR))
                        .setMaxYear(2030)
                        .setTitle("Select month years")
                        .build().show();
            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(mAbsentActivity.this, AdminMasterActivity.class));
        absenDataArrayList.clear();
    }

    public void Filter(View view) {
        String tgl = mtTgl.getText().toString();

        if (tgl.isEmpty()) {
            Toast.makeText(this, "Semua Data", Toast.LENGTH_SHORT).show();
            receiveAll();
        }
        else{
            receiveDate();
            Toast.makeText(this, "Parameter Tanggal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(mAbsentActivity.this, AdminMasterActivity.class));
    }
//
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
                                        Toast.makeText(mAbsentActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mAbsentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void receiveDate() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . .");
        progressDialog.show();
        String txtstat = mtTgl.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, getAbsentDate,
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
                                        Toast.makeText(mAbsentActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mAbsentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tanggal", txtstat);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}