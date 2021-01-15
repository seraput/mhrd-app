package com.example.mhrd.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mhrd.Models.BranchData;
import com.example.mhrd.Models.EmployeData;
import com.example.mhrd.R;

import java.util.List;

public class EmployeAdapter extends ArrayAdapter<EmployeData> {

    Context context;
    List<EmployeData> arrayListEmploye;

    public EmployeAdapter(@NonNull Context context, List<EmployeData> arrayListEmploye) {
        super(context, R.layout.custom_list_employe, arrayListEmploye);

        this.context = context;
        this.arrayListEmploye = arrayListEmploye;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_employe, null, true);

        TextView tvId = view.findViewById(R.id.c_emp_id);
        TextView tvName = view.findViewById(R.id.c_emp_nama);
        TextView tvTelp = view.findViewById(R.id.c_emp_telp);
        TextView tvKota = view.findViewById(R.id.c_emp_kota);
        TextView tvstatus = view.findViewById(R.id.c_emp_status);

        tvId.setText(arrayListEmploye.get(position).getNik());
        tvName.setText(arrayListEmploye.get(position).getNama());
        tvTelp.setText(arrayListEmploye.get(position).getTelp());
        tvKota.setText(arrayListEmploye.get(position).getKota());
        tvstatus.setText(arrayListEmploye.get(position).getStatus());

        return view;
    }
}
