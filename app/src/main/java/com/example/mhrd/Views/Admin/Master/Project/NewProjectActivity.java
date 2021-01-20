package com.example.mhrd.Views.Admin.Master.Project;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhrd.Helper.ArrayData;
import com.example.mhrd.Helper.Volley.Config;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.R;
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

public class NewProjectActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    Spinner Provinsi, Branch;
    ImageView imgDate;
    TextView tvBranchID, tvBranchName;
    MaterialEditText mtTgl, mProject;
    private String InsertProject = Server.URL_API + "insertProject.php";
    Button simpan;
    private ArrayData arrayData = new ArrayData();
    String myFormat = "MMMM yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    Calendar myCalendar;

    //An ArrayList for Spinner Items
    private ArrayList<String> students;
    //JSON Array
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        Provinsi = findViewById(R.id.spnProv);

        students = new ArrayList<String>();

        mtTgl = findViewById(R.id.dateProject);
        imgDate = findViewById(R.id.btDate);
        myCalendar = Calendar.getInstance();
        Branch = findViewById(R.id.spBranch);
        tvBranchID = findViewById(R.id.branchID);
        tvBranchName = findViewById(R.id.branchName);
        mProject = findViewById(R.id.namaProject);
        simpan = findViewById(R.id.btnSimpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        Branch.setOnItemSelectedListener(this);

        mProject.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        getData();

        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar today = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(NewProjectActivity.this,
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayData.provinsi);
        Provinsi.setAdapter(adapter);


        Provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelect = arrayData.provinsi[position];
//                Toast.makeText(MainActivity.this, "Select Item :"+itemSelect, Toast.LENGTH_SHORT).show();
                System.out.println(itemSelect);
                if (position == 0) {
                    ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Pilih);
                }
                if (position == 1) {
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Aceh);
                }
                if (position == 2) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Bali);
                }
                if (position == 3) {
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Banten);
                }
                if (position == 4) {
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Bengkulu);
                }
                if (position == 5) {
                    ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Yogyakarta);
                }
                if (position == 6) {
                    ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.DKI_Jakarta);
                }
                if (position == 7) {
                    ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Gorontalo);
                }
                if (position == 8) {
                    ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Jambi);
                }
                if (position == 9) {
                    ArrayAdapter<String> adapter9 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Jawa_Barat);
                }
                if (position == 10) {
                    ArrayAdapter<String> adapter10 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Jawa_Tengah);
                }
                if (position == 11) {
                    ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Jawa_Timur);
                }
                if (position == 12) {
                    ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Bar);
                }
                if (position == 13) {
                    ArrayAdapter<String> adapter13 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Sel);

                }
                if (position == 14) {
                    ArrayAdapter<String> adapter14 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Teng);

                }
                if (position == 15) {
                    ArrayAdapter<String> adapter15 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Tim);

                }
                if (position == 16) {
                    ArrayAdapter<String> adapter16 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kal_Utr);

                }
                if (position == 17) {
                    ArrayAdapter<String> adapter17 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kep_Belitung);

                }
                if (position == 18) {
                    ArrayAdapter<String> adapter18 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Kep_Riau);

                }
                if (position == 19) {
                    ArrayAdapter<String> adapter19 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Lampung);

                }
                if (position == 20) {
                    ArrayAdapter<String> adapter20 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Maluku);

                }
                if (position == 21) {
                    ArrayAdapter<String> adapter21 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Maluku_Utr);

                }
                if (position == 22) {
                    ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Ntb);

                }
                if (position == 23) {
                    ArrayAdapter<String> adapter23 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Ntt);

                }
                if (position == 24) {
                    ArrayAdapter<String> adapter24 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Papua);

                }
                if (position == 25) {
                    ArrayAdapter<String> adapter25 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Papua_Bar);

                }
                if (position == 26) {
                    ArrayAdapter<String> adapter26 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Riau);

                }
                if (position == 27) {
                    ArrayAdapter<String> adapter27 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Bar);

                }
                if (position == 28) {
                    ArrayAdapter<String> adapter28 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Sel);

                }
                if (position == 29) {
                    ArrayAdapter<String> adapter29 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Teng);

                }
                if (position == 30) {
                    ArrayAdapter<String> adapter30 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Tgr);

                }
                if (position == 31) {
                    ArrayAdapter<String> adapter31 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sul_Utr);

                }
                if (position == 32) {
                    ArrayAdapter<String> adapter32 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sum_Bar);

                }
                if (position == 33) {
                    ArrayAdapter<String> adapter33 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sum_Sel);

                }
                if (position == 34) {
                    ArrayAdapter<String> adapter34 = new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayData.Sum_Utr);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void back(View view) {
    }

    private void getData(){
        //Creating a string request
        ProgressDialog dialog = new ProgressDialog(NewProjectActivity.this);
        dialog.setTitle("Tunggu Sebentar");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Config.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getBranch(result);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getBranch(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                students.add(json.getString(Config.TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        Branch.setAdapter(new ArrayAdapter<String>(NewProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, students));
    }

    //Method to get student name of a particular position
    private String getId(int position){
        String id="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            id = json.getString(Config.TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return id;
    }

    //Method to get student name of a particular position
    private String getName(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString(Config.TAG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        tvBranchID.setText(getId(position));
        tvBranchName.setText(getName(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        tvBranchID.setText("");
        tvBranchName.setText("");
    }


    private void InsertData() {
        final String txtID = tvBranchID.getText().toString();
        final String txtName = tvBranchName.getText().toString();
        final String txtPname = mProject.getText().toString().trim();
        final String txtTgl = mtTgl.getText().toString().trim();
        final String txtProv = Provinsi.getSelectedItem().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(NewProjectActivity.this);
        progressDialog.setMessage("Tunggu Sebentar . . .");

        if (txtID.isEmpty() || txtName.isEmpty() || txtPname.isEmpty() || txtProv.equals("Pilih Provinsi") ||
                txtTgl.isEmpty()) {
            Toast.makeText(NewProjectActivity.this, "Lengkap Data!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, InsertProject,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("success")) {
                                Toast.makeText(NewProjectActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(NewProjectActivity.this, mProjectActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(NewProjectActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(NewProjectActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nama", txtPname);
                    params.put("b_code", txtID);
                    params.put("b_name", txtName);
                    params.put("area", txtProv);
                    params.put("bulan", txtTgl);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(NewProjectActivity.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}