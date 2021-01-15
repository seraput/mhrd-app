package com.example.mhrd.Views.Admin.Master.Outlet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhrd.Helper.ArrayData;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.HashMap;
import java.util.Map;

public class NewOutletActivity extends AppCompatActivity {

    MaterialEditText mtNama, mtTelp, mtKec, mtAlamat;
    Spinner Provinsi, Kota, tipe;
    private String InsertEmploye = Server.URL_API + "insertOutlet.php";
    Button simpan;
    private ArrayData arrayData = new ArrayData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_outlet);

        Provinsi = findViewById(R.id.spnProv);
        Kota = findViewById(R.id.spnKota);
        tipe = findViewById(R.id.spnType);
        
        mtNama = findViewById(R.id.namaOutlet);
        mtTelp = findViewById(R.id.mteTelp);
        mtKec = findViewById(R.id.mteKec);
        mtAlamat = findViewById(R.id.mteAlamat);
        simpan = findViewById(R.id.btnSimpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayData.provinsi);
        Provinsi.setAdapter(adapter);


        Provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelect = arrayData.provinsi[position];
//                Toast.makeText(MainActivity.this, "Select Item :"+itemSelect, Toast.LENGTH_SHORT).show();
                System.out.println(itemSelect);
                if (position == 0) {
                    ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Pilih);
                    Kota.setAdapter(adapter0);
                }
                if (position == 1) {
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Aceh);
                    Kota.setAdapter(adapter1);
                }
                if (position == 2) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Bali);
                    Kota.setAdapter(adapter2);
                }
                if (position == 3) {
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Banten);
                    Kota.setAdapter(adapter3);
                }
                if (position == 4) {
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Bengkulu);
                    Kota.setAdapter(adapter4);
                }
                if (position == 5) {
                    ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Yogyakarta);
                    Kota.setAdapter(adapter5);
                }
                if (position == 6) {
                    ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.DKI_Jakarta);
                    Kota.setAdapter(adapter6);
                }
                if (position == 7) {
                    ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Gorontalo);
                    Kota.setAdapter(adapter7);
                }
                if (position == 8) {
                    ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Jambi);
                    Kota.setAdapter(adapter8);
                }
                if (position == 9) {
                    ArrayAdapter<String> adapter9 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Jawa_Barat);
                    Kota.setAdapter(adapter9);
                }
                if (position == 10) {
                    ArrayAdapter<String> adapter10 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Jawa_Tengah);
                    Kota.setAdapter(adapter10);
                }
                if (position == 11) {
                    ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Jawa_Timur);
                    Kota.setAdapter(adapter11);
                }
                if (position == 12) {
                    ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Bar);
                    Kota.setAdapter(adapter12);
                }
                if (position == 13) {
                    ArrayAdapter<String> adapter13 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Sel);
                    Kota.setAdapter(adapter13);
                }
                if (position == 14) {
                    ArrayAdapter<String> adapter14 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Teng);
                    Kota.setAdapter(adapter14);
                }
                if (position == 15) {
                    ArrayAdapter<String> adapter15 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Tim);
                    Kota.setAdapter(adapter15);
                }
                if (position == 16) {
                    ArrayAdapter<String> adapter16 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Utr);
                    Kota.setAdapter(adapter16);
                }
                if (position == 17) {
                    ArrayAdapter<String> adapter17 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kep_Belitung);
                    Kota.setAdapter(adapter17);
                }
                if (position == 18) {
                    ArrayAdapter<String> adapter18 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kep_Riau);
                    Kota.setAdapter(adapter18);
                }
                if (position == 19) {
                    ArrayAdapter<String> adapter19 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Lampung);
                    Kota.setAdapter(adapter19);
                }
                if (position == 20) {
                    ArrayAdapter<String> adapter20 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Maluku);
                    Kota.setAdapter(adapter20);
                }
                if (position == 21) {
                    ArrayAdapter<String> adapter21 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Maluku_Utr);
                    Kota.setAdapter(adapter21);
                }
                if (position == 22) {
                    ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Ntb);
                    Kota.setAdapter(adapter22);
                }
                if (position == 23) {
                    ArrayAdapter<String> adapter23 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Ntt);
                    Kota.setAdapter(adapter23);
                }
                if (position == 24) {
                    ArrayAdapter<String> adapter24 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Papua);
                    Kota.setAdapter(adapter24);
                }
                if (position == 25) {
                    ArrayAdapter<String> adapter25 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Papua_Bar);
                    Kota.setAdapter(adapter25);
                }
                if (position == 26) {
                    ArrayAdapter<String> adapter26 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Riau);
                    Kota.setAdapter(adapter26);
                }
                if (position == 27) {
                    ArrayAdapter<String> adapter27 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Bar);
                    Kota.setAdapter(adapter27);
                }
                if (position == 28) {
                    ArrayAdapter<String> adapter28 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Sel);
                    Kota.setAdapter(adapter28);
                }
                if (position == 29) {
                    ArrayAdapter<String> adapter29 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Teng);
                    Kota.setAdapter(adapter29);
                }
                if (position == 30) {
                    ArrayAdapter<String> adapter30 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Tgr);
                    Kota.setAdapter(adapter30);
                }
                if (position == 31) {
                    ArrayAdapter<String> adapter31 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Utr);
                    Kota.setAdapter(adapter31);
                }
                if (position == 32) {
                    ArrayAdapter<String> adapter32 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sum_Bar);
                    Kota.setAdapter(adapter32);
                }
                if (position == 33) {
                    ArrayAdapter<String> adapter33 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sum_Sel);
                    Kota.setAdapter(adapter33);
                }
                if (position == 34) {
                    ArrayAdapter<String> adapter34 = new ArrayAdapter<String>(NewOutletActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sum_Utr);
                    Kota.setAdapter(adapter34);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Kota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kotaValue = Kota.getSelectedItem().toString();
                System.out.println(kotaValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(NewOutletActivity.this, mOutletActivity.class));
        overridePendingTransition(0,0);
    }

    private void InsertData() {
        final String txtNama = mtNama.getText().toString().trim();
        final String txtTipe = tipe.getSelectedItem().toString().trim();
        final String txtTelp = mtTelp.getText().toString().trim();
        final String txtProv = Provinsi.getSelectedItem().toString().trim();
        final String txtKota = Kota.getSelectedItem().toString().trim();
        final String txtKec = mtKec.getText().toString().trim();
        final String txtAlamat = mtAlamat.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(NewOutletActivity.this);
        progressDialog.setMessage("Loading . . .");

        if (txtNama.isEmpty() || txtTipe.equals("Pilih") || txtTelp.isEmpty() || txtProv.equals("Pilih Provinsi") ||
                txtKota.equals("Pilih Kota") || txtAlamat.isEmpty() || txtKec.isEmpty()) {
            Toast.makeText(NewOutletActivity.this, "Lengkap Data!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, InsertEmploye,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("success")) {
                                Toast.makeText(NewOutletActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(NewOutletActivity.this, mOutletActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(NewOutletActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(NewOutletActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nama", txtNama);
                    params.put("type", txtTipe);
                    params.put("telp", txtTelp);
                    params.put("provinsi", txtProv);
                    params.put("kota", txtKota);
                    params.put("kec", txtKec);
                    params.put("alamat", txtAlamat);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(NewOutletActivity.this);
            requestQueue.add(request);
        }
    }
}