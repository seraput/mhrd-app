package com.example.mhrd.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.mhrd.Models.JobsData;
import com.example.mhrd.R;
import java.util.List;

public class JobsAdapter extends ArrayAdapter<JobsData> {
    Context context;
    List<JobsData> arrayListJobs;

    public JobsAdapter(@NonNull Context context, List<JobsData> arrayListJobs) {
        super(context, R.layout.list_jobs, arrayListJobs);

        this.context = context;
        this.arrayListJobs = arrayListJobs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jobs, null, true);

        TextView tvId = view.findViewById(R.id.c_jobs_id);
        TextView tvBranch = view.findViewById(R.id.c_jobs_branch);
        TextView tvProject = view.findViewById(R.id.c_jobs_project);
        TextView tvOutlet = view.findViewById(R.id.c_jobs_outlet);
        TextView tvUser = view.findViewById(R.id.c_jobs_user);

        tvId.setText(arrayListJobs.get(position).getId());
        tvBranch.setText(arrayListJobs.get(position).getP_name());
        tvProject.setText(arrayListJobs.get(position).getUser_nama());
        tvOutlet.setText(arrayListJobs.get(position).getOutlet_name());
        tvUser.setText(arrayListJobs.get(position).getStart());

        return view;
    }
}
