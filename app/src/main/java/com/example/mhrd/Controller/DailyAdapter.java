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
import com.example.mhrd.Models.DailyData;
import com.example.mhrd.R;

import java.util.List;

public class DailyAdapter extends ArrayAdapter<DailyData> {

    Context context;
    List<DailyData> arrayListDaily;

    public DailyAdapter(@NonNull Context context, List<DailyData> arrayListDaily) {
        super(context, R.layout.list_daily, arrayListDaily);

        this.context = context;
        this.arrayListDaily = arrayListDaily;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_daily, null, true);

        TextView tvId = view.findViewById(R.id.dailyName);
        TextView tvName = view.findViewById(R.id.dailyOutlet);
        TextView tvAlamat = view.findViewById(R.id.dailyTanggal);

        tvId.setText(arrayListDaily.get(position).getUser_nama());
        tvName.setText(arrayListDaily.get(position).getOutlet());
        tvAlamat.setText(arrayListDaily.get(position).getTanggal());

        return view;
    }
}
