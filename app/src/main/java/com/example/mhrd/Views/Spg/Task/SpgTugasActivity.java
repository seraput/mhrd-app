package com.example.mhrd.Views.Spg.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhrd.Helper.Retrofit.ApiClient;
import com.example.mhrd.Helper.Retrofit.ApiInterfaceDaily;
import com.example.mhrd.Helper.Retrofit.DailyReport;
import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.R;
import com.example.mhrd.Views.Spg.Aktivitas.SpgAbsentActivity;
import com.example.mhrd.Views.Spg.SpgMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class SpgTugasActivity extends AppCompatActivity {
    String getId, getNama, getProject, getProjectId;
    private static final int CAMERA_PIC_REQUEST = 7;
    Uri imageUri;
    //    private String updateBarang = Api.URL_API + "updatePeminjaman.php";
    String myFormat = "dd MMMM yyyy";
    String myFormat2 = "HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2);
    private Bitmap bitmap;
    ApiInterfaceDaily apiInterface;
    SessionManager sessionManager;
    private String getJobs = Server.URL_API + "getJobs2.php";
    MaterialEditText tvBranch, tvProject, tvOutlet, tvJobsID, tvTgl, tvProduct, tvQty, tvJam;
    Button btKirim, btAmbil;
    ImageView imgFoto;

    private long backPressedTime;
    private Toast backToast;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spg_tugas);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        getNama = user.get(SessionManager.EMAIL);
        getProjectId = user.get(SessionManager.PROJECTID);
        getProject = user.get(SessionManager.PROJECT);

        tvBranch = findViewById(R.id.tugas_branch);
        tvProject = findViewById(R.id.tugas_project);
        tvOutlet = findViewById(R.id.tugas_outlet);
        tvProduct = findViewById(R.id.tugas_produk);
        tvJobsID = findViewById(R.id.tugas_jobs);
        tvQty = findViewById(R.id.tugas_qty);
        tvTgl = findViewById(R.id.tugas_tanggal);
        tvJam = findViewById(R.id.tugas_jam);

        btAmbil = findViewById(R.id.tugas_take);
        btKirim = findViewById(R.id.tugas_kirim);
        imgFoto = findViewById(R.id.tugas_gambar);


        final MediaPlayer mpabsen = MediaPlayer.create(this, R.raw.absensi);
        final MediaPlayer mpdashboard = MediaPlayer.create(this, R.raw.dashboard);
        final MediaPlayer mpaktivitas = MediaPlayer.create(this, R.raw.aktivitas);

        btAmbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ambil foto
                chooseFile();
            }
        });

        btKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // upload data
                String txtTgl = tvTgl.getText().toString();
                String txtJobs = tvJobsID.getText().toString();
                String txtbranch = tvBranch.getText().toString();
                String txtProj = tvProject.getText().toString();
                String txtProd = tvProduct.getText().toString();
                String txtOutlet = tvOutlet.getText().toString();
                String txtQty = tvQty.getText().toString();

                if (txtbranch.isEmpty() || txtJobs.isEmpty() || txtOutlet.isEmpty() || txtProd.isEmpty()
                || txtProj.isEmpty() || txtTgl.isEmpty() || txtQty.isEmpty()){
                    Toast.makeText(SpgTugasActivity.this, "Lengkapi Data!", Toast.LENGTH_SHORT).show();
                }
                else {
                    postData("insert");
                }
            }
        });

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),
                                SpgMainActivity.class));
                        overridePendingTransition(0,0);
                        mpdashboard.start();
                        return true;

                    case R.id.aktivitas:
                        startActivity(new Intent(getApplicationContext(),
                                SpgAbsentActivity.class));
                        overridePendingTransition(0,0);
                        mpabsen.start();
                        return true;

                    case R.id. history:
//                        startActivity(new Intent(getApplicationContext(),
//                                SpgHistoryActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //End ButtomNav

        //Set Tanggal
//        Calendar c1 = Calendar.getInstance();
//        String str1 = sdf.format(c1.getTime());
//        tvTgl.setText(str1);

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdf.format(c1.getTime());
        String str2 = sdf2.format(c1.getTime());
        tvTgl.setText(str1);
        tvJam.setText(str2);
    }

    private void getUserDetail(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getJobs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){

                                for (int i = 0; i < jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strID = object.getString("id").trim();
                                    String strPname = object.getString("p_id").trim();
                                    String strOutlet = object.getString("outlet_name").trim();
                                    String strBranch = object.getString("branch").trim();

                                    tvJobsID.setText(strID);
                                    tvBranch.setText(strBranch);
                                    tvProject.setText(strPname);
                                    tvOutlet.setText(strOutlet);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(SpgTugasActivity.this, "Bermasalah! "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SpgTugasActivity.this, "Koneksi Error! "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    private void postData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading....");
        progressDialog.show();
        final MediaPlayer mpdashoard = MediaPlayer.create(this, R.raw.berhasil);
        final MediaPlayer mplaporan = MediaPlayer.create(this, R.raw.gagal);
        String jobs_id = tvJobsID.getText().toString();
        String branch = tvBranch.getText().toString();
        String project = tvProject.getText().toString();
        String outlet = tvOutlet.getText().toString();
        String product = tvProduct.getText().toString();
        String qty = tvQty.getText().toString();
        String image = null;
        String tanggal = tvTgl.getText().toString();
        String jam = tvJam.getText().toString();
        String user_id = getId;
        String user_nama = getNama;
        if (bitmap == null) {
            image = "";
        } else {
            image = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterfaceDaily.class);

        Call<DailyReport> call = apiInterface.insertData(key, jobs_id, branch, project, outlet, product, qty, image, tanggal, jam, user_id, user_nama);

        call.enqueue(new Callback<DailyReport>() {
            @Override
            public void onResponse(Call<DailyReport> call, retrofit2.Response<DailyReport> response) {

                progressDialog.dismiss();

                Log.i(SpgAbsentActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")) {
                    startActivity(new Intent(SpgTugasActivity.this, SpgTugasActivity.class));
                    mpdashoard.start();
                    Toast.makeText(SpgTugasActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    mplaporan.start();
                    Toast.makeText(SpgTugasActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DailyReport> call, Throwable t) {
                progressDialog.dismiss();
                mplaporan.start();
                Toast.makeText(SpgTugasActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
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