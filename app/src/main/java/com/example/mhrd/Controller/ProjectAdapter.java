package com.example.mhrd.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mhrd.Models.OutletData;
import com.example.mhrd.Models.ProjectData;
import com.example.mhrd.R;

import java.util.List;

public class ProjectAdapter extends ArrayAdapter<ProjectData> {

    Context context;
    List<ProjectData> arrayListProject;

    public ProjectAdapter(@NonNull Context context, List<ProjectData> arrayListProject) {
        super(context, R.layout.custom_list_proect, arrayListProject);

        this.context = context;
        this.arrayListProject = arrayListProject;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_proect, null, true);

        TextView tvId = view.findViewById(R.id.id_project);
        TextView tvProject = view.findViewById(R.id.nama_project);
        TextView tvBranch = view.findViewById(R.id.branch_project);
        TextView tvArea = view.findViewById(R.id.area_project);
        TextView tvBln = view.findViewById(R.id.bulan_project);

        tvId.setText(arrayListProject.get(position).getId());
        tvProject.setText(arrayListProject.get(position).getNama());
        tvBranch.setText(arrayListProject.get(position).getB_name());
        tvArea.setText(arrayListProject.get(position).getArea());
        tvBln.setText(arrayListProject.get(position).getBulan());

        return view;
    }
}
