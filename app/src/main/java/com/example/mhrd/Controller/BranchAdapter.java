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
import com.example.mhrd.R;
import java.util.List;

public class BranchAdapter extends ArrayAdapter<BranchData> {
    Context context;
    List<BranchData> arrayListBranch;

    public BranchAdapter(@NonNull Context context, List<BranchData> arrayListBranch) {
        super(context, R.layout.list_branch, arrayListBranch);

        this.context = context;
        this.arrayListBranch = arrayListBranch;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_branch, null, true);

        TextView tvId = view.findViewById(R.id.c_branch_code);
        TextView tvName = view.findViewById(R.id.c_branch_name);
        TextView tvAlamat = view.findViewById(R.id.c_branch_address);

        tvId.setText(arrayListBranch.get(position).getId());
        tvName.setText(arrayListBranch.get(position).getName());
        tvAlamat.setText(arrayListBranch.get(position).getAlamat());

        return view;
    }
}
