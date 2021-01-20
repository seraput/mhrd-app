package com.example.mhrd.Views.Admin.Master.Jobs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.example.mhrd.Controller.AbsenAdapter;
import com.example.mhrd.Helper.ArrayData;
import com.example.mhrd.Helper.SessionManager;
import com.example.mhrd.Helper.Volley.AddGetEmploye;
import com.example.mhrd.Helper.Volley.AddGetOutlet;
import com.example.mhrd.Helper.Volley.AddGetProject;
import com.example.mhrd.Helper.Volley.Config;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.AbsenData;
import com.example.mhrd.R;
import com.example.mhrd.Views.Admin.Master.Absent.mAbsentActivity;
import com.example.mhrd.Views.Admin.Master.AdminMasterActivity;
import com.example.mhrd.Views.Admin.Master.Employe.mEmployeeActivity;
import com.example.mhrd.Views.Admin.Master.Project.NewProjectActivity;
import com.example.mhrd.Views.Admin.Master.Project.mProjectActivity;
import com.example.mhrd.Views.Spv.Master.SpvMasterActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.mhrd.Helper.Volley.AddGetEmploye.DATA_URL2;
import static com.example.mhrd.Helper.Volley.AddGetOutlet.DATA_URL;

public class AddJobsActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    Spinner spjProject, spjOutlet, spjEmploye;
    ImageView btCari, btCariEmploye, btCariOutlet;
    Dialog dialog, dialog2, dialog3;
    RelativeLayout rlbtClose, rlbtClose2, rlbtClose3;
    Button btUploadJobs;
    MaterialEditText mtPcode,  mtPname, mtPbranch, mtParea, mtPStart, mtEcode, mtEname, mtEemail, mtEtelp, mtOcode, mtOname, mtOkota, mtOkec, mtOalamat;

    private ArrayData arrayData = new ArrayData();
    //An ArrayList for Spinner Items
    private ArrayList<String> students;
    private ArrayList<String> employe;
    private ArrayList<String> outlet;
    //JSON Array
    private JSONArray result, result2, result3;


    private String insertJobs = Server.URL_API + "insertJp.php";
    private String insertUser = Server.URL_API + "insertUs.php";

    SessionManager sessionManager;
    String getLevel;
    TextView tvLevel;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jobs);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getLevel = user.get(SessionManager.LEVEL);
//
//        tvLevel = findViewById(R.id.txtLevel);
//        tvLevel.setText(getLevel);

        dialog = new Dialog(AddJobsActivity.this);
        dialog.setContentView(R.layout.jobs_project);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        dialog2 = new Dialog(AddJobsActivity.this);
        dialog2.setContentView(R.layout.jobs_employe);
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false);

        dialog3 = new Dialog(AddJobsActivity.this);
        dialog3.setContentView(R.layout.jobs_outlet);
        dialog3.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog3.setCancelable(false);

        spjProject = dialog.findViewById(R.id.spj_project);
        spjEmploye = dialog2.findViewById(R.id.spj_employe);
        spjOutlet = dialog3.findViewById(R.id.spj_outlet);

        btCari = findViewById(R.id.aj_btCari);
        mtPbranch = findViewById(R.id.aj_bName);
        mtPcode = findViewById(R.id.aj_pCode);
        mtPname = findViewById(R.id.aj_pName);
        mtParea = findViewById(R.id.aj_area);
        mtPStart = findViewById(R.id.aj_start);
        mtEcode = findViewById(R.id.mtEcode);
        mtEname = findViewById(R.id.mtEname);
        mtEemail = findViewById(R.id.mtEemail);
        mtEtelp = findViewById(R.id.mtEtelp);
        mtOcode = findViewById(R.id.mtOcode);
        mtOname = findViewById(R.id.mtOname);
        mtOkota = findViewById(R.id.mtOkota);
        mtOkec = findViewById(R.id.mtOkec);
        mtOalamat = findViewById(R.id.mtOalamat);
        btCariEmploye = findViewById(R.id.aj_btCode);
        btCariOutlet = findViewById(R.id.btOcode);
        btUploadJobs = findViewById(R.id.btnSimpanJobs);

        rlbtClose = dialog.findViewById(R.id.btSelect);
        rlbtClose2 = dialog2.findViewById(R.id.btESelect);
        rlbtClose3 = dialog3.findViewById(R.id.btOSelect);

        students = new ArrayList<String>();
        outlet = new ArrayList<String>();
        employe = new ArrayList<String>();

        spjProject.setOnItemSelectedListener(this);
        spjEmploye.setOnItemSelectedListener(this);
        spjOutlet.setOnItemSelectedListener(this);

        btCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                receiveAll();
                dialog.show();
                getData();
            }
        });

        rlbtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                students.clear();
            }
        });

        btCariEmploye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog2.show();
                    getDataEmploye();
            }
        });

        rlbtClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                employe.clear();
            }
        });

        btCariOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog3.show();
                    getDataOutlet();
            }
        });

        rlbtClose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
                outlet.clear();
            }
        });

        btUploadJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Pid = mtPcode.getText().toString();
                final String Pname = mtPname.getText().toString();
                final String Pbranch = mtPbranch.getText().toString().trim();
                final String Pprov = mtParea.getText().toString().trim();
                final String Pstart = mtPStart.getText().toString().trim();
                final String Ecode = mtEcode.getText().toString().trim();
                final String Ename = mtEname.getText().toString().trim();
                final String Eemail = mtEemail.getText().toString().trim();
                final String Etelp = mtEtelp.getText().toString().trim();
                final String Ocode = mtOcode.getText().toString().trim();
                final String Oname = mtOname.getText().toString().trim();
                final String Okota = mtOkota.getText().toString().trim();
                final String Okec = mtOkec.getText().toString().trim();
                final String Oalamat = mtOalamat.getText().toString().trim();

                if (Pid.isEmpty() || Pname.isEmpty() || Pbranch.isEmpty() || Pprov.isEmpty() || Pstart.isEmpty() ||
                    Ecode.isEmpty() || Eemail.isEmpty() || Ename.isEmpty() || Etelp.isEmpty() || Ocode.isEmpty() ||
                    Oname.isEmpty() || Okota.isEmpty() || Okec.isEmpty() || Oalamat.isEmpty())
                {
                    Toast.makeText(AddJobsActivity.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    insertJobs();
                }

            }
        });

    }

    private void getData(){
        //Creating a string request
        ProgressDialog dialog = new ProgressDialog(AddJobsActivity.this);
        dialog.setTitle("Tunggu Sebentar");
        dialog.show();
        StringRequest stringRequest = new StringRequest(AddGetProject.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(AddGetProject.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getProject(result);
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

    private void getProject(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                students.add(json.getString(AddGetProject.TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spjProject.setAdapter(new ArrayAdapter<String>(AddJobsActivity.this, android.R.layout.simple_spinner_dropdown_item, students));
    }

    //Method to get student name of a particular position
    private String getId(int position){
        String id="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            id = json.getString(AddGetProject.TAG_CODE);
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
            name = json.getString(AddGetProject.TAG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    private String getBranch(int position){
        String branch="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            branch = json.getString(AddGetProject.TAG_BRANCH);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return branch;
    }

    private String getArea(int position){
        String area="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            area = json.getString(AddGetProject.TAG_AREA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return area;
    }

    private String getStart(int position){
        String bulan="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            bulan = json.getString(AddGetProject.TAG_START);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return bulan;
    }

    private void getDataEmploye(){
        //Creating a string request
        final String area = mtParea.getText().toString();
        ProgressDialog dialog = new ProgressDialog(AddJobsActivity.this);
        dialog.setTitle("Tunggu Sebentar");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(AddGetEmploye.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getEmploye(result);
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
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("provinsi", area);
//                params.put("status", status);
                return params;
            }
        }
        ;

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getEmploye(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                employe.add(json.getString(AddGetEmploye.TAG_NAMA));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spjEmploye.setAdapter(new ArrayAdapter<String>(AddJobsActivity.this, android.R.layout.simple_spinner_dropdown_item, employe));
    }

    //Method to get student name of a particular position
    private String getEnik(int position){
        String nik="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            nik = json.getString(AddGetEmploye.TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return nik;
    }

    //Method to get student name of a particular position
    private String getENama(int position){
        String enama="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            enama = json.getString(AddGetEmploye.TAG_NAMA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return enama;
    }

    private String getEemail(int position){
        String email="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            email = json.getString(AddGetEmploye.TAG_EMAIL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return email;
    }

    private String getEtelp(int position){
        String etelp="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            etelp = json.getString(AddGetEmploye.TAG_TELP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return etelp;
    }

    private void getDataOutlet(){
        //Creating a string request
        final String area = mtParea.getText().toString();
        ProgressDialog dialog = new ProgressDialog(AddJobsActivity.this);
        dialog.setTitle("Tunggu Sebentar");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(AddGetOutlet.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getOutlet(result);
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
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("provinsi", area);
//                params.put("status", status);
                return params;
            }
        }
        ;

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getOutlet(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                outlet.add(json.getString(AddGetOutlet.TAG_NAMA));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spjOutlet.setAdapter(new ArrayAdapter<String>(AddJobsActivity.this, android.R.layout.simple_spinner_dropdown_item, outlet));
    }

    //Method to get student name of a particular position
    private String getOid(int position){
        String oid="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            oid = json.getString(AddGetOutlet.TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return oid;
    }

    //Method to get student name of a particular position
    private String getOnama(int position){
        String onama="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            onama = json.getString(AddGetOutlet.TAG_NAMA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return onama;
    }

    private String getOkota(int position){
        String okota="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            okota = json.getString(AddGetOutlet.TAG_KOTA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return okota;
    }

    private String getOkec(int position){
        String okec="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            okec = json.getString(AddGetOutlet.TAG_KEC);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return okec;
    }

    private String getOalamat(int position){
        String oalamat="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            oalamat = json.getString(AddGetOutlet.TAG_ALAMAT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return oalamat;
    }

    public void back(View view) {
        startActivity(new Intent(AddJobsActivity.this, mJobsActivity.class));
        overridePendingTransition(0,0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spj_project){
            mtPcode.setText(getId(position));
            mtPname.setText(getName(position));
            mtPbranch.setText(getBranch(position));
            mtParea.setText(getArea(position));
            mtPStart.setText(getStart(position));
        }
        else if(parent.getId() == R.id.spj_employe){
            mtEcode.setText(getEnik(position));
            mtEname.setText(getENama(position));
            mtEemail.setText(getEemail(position));
            mtEtelp.setText(getEtelp(position));
        }
        else if(parent.getId() == R.id.spj_outlet){
            mtOcode.setText(getOid(position));
            mtOname.setText(getOnama(position));
            mtOkota.setText(getOkota(position));
            mtOkec.setText(getOkec(position));
            mtOalamat.setText(getOalamat(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mtPcode.setText("");
        mtPname.setText("");
        mtPbranch.setText("");
    }

    private void insertJobs() {
        final String Pid = mtPcode.getText().toString();
        final String Pname = mtPname.getText().toString();
        final String Pbranch = mtPbranch.getText().toString().trim();
        final String Pprov = mtParea.getText().toString().trim();
        final String Pstart = mtPStart.getText().toString().trim();
        final String Ecode = mtEcode.getText().toString().trim();
        final String Ename = mtEname.getText().toString().trim();
        final String Etelp = mtEtelp.getText().toString().trim();
        final String Ocode = mtOcode.getText().toString().trim();
        final String Oname = mtOname.getText().toString().trim();
        final String Okota = mtOkota.getText().toString().trim();
        final String Okec = mtOkec.getText().toString().trim();
        final String Oalamat = mtOalamat.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(AddJobsActivity.this);
        progressDialog.setMessage("Tunggu Sebentar . . .");
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, insertJobs,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("success")) {
                                Toast.makeText(AddJobsActivity.this, "Jobs Created!", Toast.LENGTH_SHORT).show();
                                insertUser();
                                progressDialog.dismiss();
                                Intent intent = new Intent(AddJobsActivity.this, mJobsActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AddJobsActivity.this, "Jobs Failed!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddJobsActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("p_id", Pid);
                    params.put("p_name", Pname);
                    params.put("branch", Pbranch);
                    params.put("user_id", Ecode);
                    params.put("user_nama", Ename);
                    params.put("telp", Etelp);
                    params.put("outlet_id", Ocode);
                    params.put("outlet_name", Oname);
                    params.put("alamat", Oalamat);
                    params.put("kec", Okec);
                    params.put("kota", Okota);
                    params.put("provinsi", Pprov);
                    params.put("start", Pstart);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(AddJobsActivity.this);
            requestQueue.add(request);
    }

    private void insertUser() {
        final String Pid = mtPcode.getText().toString();
        final String Pname = mtPname.getText().toString();
        final String Pbranch = mtPbranch.getText().toString().trim();
        final String Ecode = mtEcode.getText().toString().trim();
        final String Ename = mtEname.getText().toString().trim();
        final String Eemail = mtEemail.getText().toString().trim();
        final String Etelp = mtEtelp.getText().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(AddJobsActivity.this);
        progressDialog.setMessage("Tunggu Sebentar . . .");
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, insertUser,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("success")) {
                                Toast.makeText(AddJobsActivity.this, "User Active!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(AddJobsActivity.this, "User Failed!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddJobsActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", Ecode);
                    params.put("email", Eemail);
                    params.put("nama", Ename);
                    params.put("branch", Pbranch);
                    params.put("project_id", Pid);
                    params.put("project", Pname);
                    params.put("telp", Etelp);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(AddJobsActivity.this);
            requestQueue.add(request);
    }

}