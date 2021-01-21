package com.example.mhrd.Views.Spv;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhrd.Helper.Retrofit.Absent;
import com.example.mhrd.Helper.Retrofit.ApiClient;
import com.example.mhrd.Helper.Retrofit.ApiInterface;
import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.LoginActivity;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.Employe.mEmployeeActivity;
import com.example.mhrd.Views.Spv.Activity.SpvTaskActivity;
import com.example.mhrd.Views.Spv.Master.SpvMasterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpvMainActivity extends AppCompatActivity {

    ImageView imgFoto;
    Button upload, ambil;
    Spinner spKet;
    TextView tvNama, tvTanggal, tvJam;
    String getId, getProjectId, getProject, getNama;

    private long backPressedTime;
    private Toast backToast;
    SharedPreferences sharedPreferences;

    private static final int CAMERA_PIC_REQUEST = 7;
    Uri imageUri;
    String myFormat = "dd MMMM yyyy";
    String myFormat2 = "HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2);
    private Bitmap bitmap;
    ApiInterface apiInterface;
    SessionManager sessionManager;
    private String masuk = Server.URL_API + "update_masuk.php";
    private String pulang = Server.URL_API + "update_pulang.php";
    ImageView option;
    Dialog dialog;
    Button btAddUser, btProfile, btLogout, btExit;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spv_main);

        tvTanggal = findViewById(R.id.spvTgl);
        tvJam = findViewById(R.id.spvJam);
        tvNama = findViewById(R.id.spvNama);

        option = findViewById(R.id.menu);
        dialog = new Dialog(SpvMainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btProfile = dialog.findViewById(R.id.btProfile);
        btLogout = dialog.findViewById(R.id.btLogout);
        btExit = dialog.findViewById(R.id.btCancel);
        btAddUser = dialog.findViewById(R.id.btAddUser);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        getId = user.get(SessionManager.ID);
        getProjectId = user.get(SessionManager.PROJECTID);
        getProject = user.get(SessionManager.PROJECT);
        getNama = user.get(SessionManager.EMAIL);

        tvNama.setText(getNama);

        btProfile.setVisibility(View.GONE);

        btAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpvMainActivity.this, mEmployeeActivity.class));
                overridePendingTransition(0,0);
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                final ProgressDialog progressDialog = new ProgressDialog(SpvMainActivity.this);
                progressDialog.setTitle("Terimakasih");
                progressDialog.setMessage("Tunggu Sebentar . . .");
                progressDialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Logo();
                    }

                }, 3000);
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdf.format(c1.getTime());
        String str2 = sdf2.format(c1.getTime());
        tvTanggal.setText(str1);
        tvJam.setText(str2);

        spKet = findViewById(R.id.spvKet);
        upload = findViewById(R.id.spvUpload);
        ambil = findViewById(R.id.spvAmbil);
        imgFoto = findViewById(R.id.imgFoto);

        ambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                chooseFile();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String ket = spKet.getSelectedItem().toString();
                if (ket.equals("Masuk")){
                    postData("insert");
                    StatusMasuk();
                }
                else if (ket.equals("Pulang")){
                    postData("insert");
                    StatusPulang();
                }
                else{
                    Toast.makeText(SpvMainActivity.this, "Periksa Foto & Deskripsi!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
//                        startActivity(new Intent(getApplicationContext(),
//                                AdminMainActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.master:
                        startActivity(new Intent(getApplicationContext(),
                                SpvMasterActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.activity:
                        startActivity(new Intent(getApplicationContext(),
                                SpvTaskActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //End ButtomNav
    }

    private void postData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading....");
        progressDialog.show();

        String userId = getId;
        String userNama = tvNama.getText().toString();
        String projectId = getProjectId;
        String projectNama = getProject;
        String tanggal = tvTanggal.getText().toString();
        String jam = tvJam.getText().toString();
        String image = null;
        String keterangan = spKet.getSelectedItem().toString();
        if (bitmap == null) {
            image = "";
        } else {
            image = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Absent> call = apiInterface.insertData(key, userId, userNama, projectId, projectNama, tanggal, jam, image, keterangan);

        call.enqueue(new Callback<Absent>() {
            @Override
            public void onResponse(Call<Absent> call, Response<Absent> response) {

                progressDialog.dismiss();

                Log.i(SpvMainActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")) {
                    Toast.makeText(SpvMainActivity.this, "Berhasil!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SpvMainActivity.this, SpvMainActivity.class));
                    overridePendingTransition(0,0);
//                    SaveEditDetail();
                } else {
                    Toast.makeText(SpvMainActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Absent> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SpvMainActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void chooseFile() {
        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK)
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }

                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(imageUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                getContentResolver().notifyChange(imageUri, null);
                ContentResolver cr = getContentResolver();

                try {
                    bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, imageUri);
                    imgFoto.setImageBitmap(bitmap);

                } catch (Exception e) {

                }
            } catch (Exception e) {

            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void StatusMasuk() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connecting...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, masuk,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
//                                Toast.makeText(DashboardGuruActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//                                getUserDetail();
//                                btMasuk.setVisibility(View.GONE);
//                                btPulang.setVisibility(View.VISIBLE);
//                                InsertRekap();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                            progressDialog.dismiss();
                            Toast.makeText(SpvMainActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(SpvMainActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void StatusPulang() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connecting...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, pulang,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
//                                Toast.makeText(DashboardGuruActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//                                getUserDetail();
//                                btPulang.setVisibility(View.GONE);
//                                btMasuk.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                            progressDialog.dismiss();
                            Toast.makeText(SpvMainActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(SpvMainActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void Logo(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
        editor.apply();
        Intent i = new Intent(SpvMainActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        } else {
            backToast = Toast.makeText(this, "Tekan Lagi Untuk Keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}