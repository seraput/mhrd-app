package com.example.mhrd;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.Views.Admin.AdminMainActivity;
import com.example.mhrd.Views.Spg.SpgMainActivity;
import com.example.mhrd.Views.Spv.SpvMainActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    Button btLogin;
    MaterialEditText mtEmail, mtPass;
    CheckBox loginstate;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    private String LoginAPI = Server.URL_API + "login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        mtEmail = findViewById(R.id.email_login);
        mtPass = findViewById(R.id.pass_login);
        btLogin = findViewById(R.id.btn_login);
        loginstate = findViewById(R.id.state);

        final MediaPlayer mpgreet = MediaPlayer.create(this, R.raw.welcome);

        String loginstatus = sharedPreferences.getString(getResources().getString(R.string.prefLoginState),"");
        if (loginstatus.equals("LoggedIn")){
            startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
        }
        else if (loginstatus.equals("LoggedOn")){
            startActivity(new Intent(LoginActivity.this, SpgMainActivity.class));
        }
        else if (loginstatus.equals("LoggedEn")){
            startActivity(new Intent(LoginActivity.this, SpvMainActivity.class));
        }

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtemail = mtEmail.getText().toString().trim();
                String txtpass = mtPass.getText().toString().trim();

                if (TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtpass)){
                    Toast.makeText(LoginActivity.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginProces(txtemail, txtpass);
                }
            }
        });

        checkPermission();
    }

    private void checkPermission(){

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(DashboardGuruActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(DashboardGuruActivity.this, "Permission not given", Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(LoginActivity.this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
                .check();
    }

    private void loginProces(final String email, final String password) {
        final MediaPlayer mpberhasil = MediaPlayer.create(this, R.raw.berhasil);
        final MediaPlayer mpgagal = MediaPlayer.create(this, R.raw.gagal);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        final Handler handler = new Handler();
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Tunggu Sebentar...");
        progressDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginAPI,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                                    if (success.equals("1")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String id = object.getString("id").trim();
                                            String email = object.getString("email").trim();
                                            String nama = object.getString("nama").trim();
                                            String branch = object.getString("branch").trim();
                                            String projectId = object.getString("project_id").trim();
                                            String project = object.getString("project").trim();
                                            String level = object.getString("level").trim();

                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            if (loginstate.isChecked() && level.equals("admin")){
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedIn");
                                            }else if (loginstate.isChecked() && level.equals("spg")){
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOn");
                                            }
                                            else if(loginstate.isChecked() && level.equals("spv")){
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedEn");
                                            }
                                            else {
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
                                            }

                                            if (level.equals("admin")) {
                                                sessionManager.createSession(id, email, nama, branch, projectId, project, level);
                                                editor.apply();
                                                mpberhasil.start();
                                                final Intent inte = new Intent(LoginActivity.this, AdminMainActivity.class);
                                                inte.putExtra("email", email);
                                                inte.putExtra("nama", nama);
                                                inte.putExtra("branch", branch);
                                                startActivity(inte);
                                                finish();
                                            }else if (level.equals("spg")){
                                                sessionManager.createSession(id, email, nama, branch, projectId, project, level);
                                                editor.apply();
                                                mpberhasil.start();
                                                final Intent in = new Intent(LoginActivity.this, SpgMainActivity.class);
                                                in.putExtra("email", email);
                                                in.putExtra("nama", nama);
                                                in.putExtra("branch", branch);
                                                startActivity(in);
                                                finish();
                                            }
                                            else if (level.equals("spv")){
                                                sessionManager.createSession(id, email, nama, branch, projectId, project, level);
                                                editor.apply();
                                                mpberhasil.start();
                                                final Intent on = new Intent(LoginActivity.this, SpvMainActivity.class);
                                                on.putExtra("email", email);
                                                on.putExtra("nama", nama);
                                                on.putExtra("branch", branch);
                                                startActivity(on);
                                                finish();
                                            }
                                            else{
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                    mpgagal.start();
                                    Toast.makeText(LoginActivity.this, "Error, Email Or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                mpgagal.start();
                                Toast.makeText(LoginActivity.this, "Error, Check Connection" +error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);
            }
        }, 2000);

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