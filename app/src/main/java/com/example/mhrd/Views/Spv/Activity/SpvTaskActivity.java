package com.example.mhrd.Views.Spv.Activity;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhrd.Helper.Retrofit.Absent;
import com.example.mhrd.Helper.Retrofit.ApiClient;
import com.example.mhrd.Helper.Retrofit.ApiInterface;
import com.example.mhrd.Helper.Retrofit.ApiInterfaceSpv;
import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.R;
import com.example.mhrd.Views.Spv.Master.SpvMasterActivity;
import com.example.mhrd.Views.Spv.SpvMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpvTaskActivity extends AppCompatActivity {

    TextView tvName, tvTgl, tvJam, tvID;
    ImageView imgFoto;
    Button btAmbil, btUpload;
    Spinner spKet;
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
    ApiInterfaceSpv apiInterfaceSpv;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spv_task);

        tvName = findViewById(R.id.spvNama);
        tvTgl = findViewById(R.id.spvTgl);
        tvJam = findViewById(R.id.spvJam);
        imgFoto = findViewById(R.id.imgFoto);
        btAmbil = findViewById(R.id.spvAmbil);
        btUpload = findViewById(R.id.spvUpload);
        spKet = findViewById(R.id.spvKet);
        tvID = findViewById(R.id.spvID);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        getProjectId = user.get(SessionManager.PROJECTID);
        getProject = user.get(SessionManager.PROJECT);
        getNama = user.get(SessionManager.EMAIL);

        final MediaPlayer mpmaster = MediaPlayer.create(this, R.raw.master);
        final MediaPlayer mpdashboard = MediaPlayer.create(this, R.raw.dashboard);
        final MediaPlayer mpaktivitas = MediaPlayer.create(this, R.raw.aktivitas);

        tvName.setText(getNama);
        tvID.setText(getId);

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdf.format(c1.getTime());
        String str2 = sdf2.format(c1.getTime());
        tvTgl.setText(str1);
        tvJam.setText(str2);

        btAmbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData("insert");
            }
        });

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),
                                SpvMainActivity.class));
                        overridePendingTransition(0,0);
                        mpdashboard.start();
                        return true;

                    case R.id.master:
                        startActivity(new Intent(getApplicationContext(),
                                SpvMasterActivity.class));
                        overridePendingTransition(0,0);
                        mpmaster.start();
                        return true;

                    case R.id.activity:
//                        startActivity(new Intent(getApplicationContext(),
//                                SpvTaskActivity.class));
//                        overridePendingTransition(0,0);
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
        final MediaPlayer mpdashoard = MediaPlayer.create(this, R.raw.berhasil);
        final MediaPlayer mplaporan = MediaPlayer.create(this, R.raw.gagal);
        String userId = tvID.getText().toString();
        String userNama = tvName.getText().toString();
        String tanggal = tvTgl.getText().toString();
        String jam = tvJam.getText().toString();
        String image = null;
        String keterangan = spKet.getSelectedItem().toString();
        if (bitmap == null) {
            image = "";
        }
        else {
            image = getStringImage(bitmap);
        }

        apiInterfaceSpv = ApiClient.getApiClient().create(ApiInterfaceSpv.class);

        Call<Absent> call = apiInterfaceSpv.insertData(key, userId, userNama, tanggal, jam, image, keterangan);

        call.enqueue(new Callback<Absent>() {
            @Override
            public void onResponse(Call<Absent> call, Response<Absent> response) {
                progressDialog.dismiss();

                Log.i(SpvMainActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")) {
                    Toast.makeText(SpvTaskActivity.this, "Berhasil!", Toast.LENGTH_SHORT).show();
                    mpdashoard.start();
                    startActivity(new Intent(SpvTaskActivity.this, SpvTaskActivity.class));
                    overridePendingTransition(0,0);
//                    SaveEditDetail();
                } else {
                    Toast.makeText(SpvTaskActivity.this, message, Toast.LENGTH_SHORT).show();
                    mplaporan.start();
                }

            }

            @Override
            public void onFailure(Call<Absent> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SpvTaskActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                mplaporan.start();
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
}