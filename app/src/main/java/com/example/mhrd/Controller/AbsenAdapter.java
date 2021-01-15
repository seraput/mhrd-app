package com.example.mhrd.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.mhrd.Models.AbsenData;
import com.example.mhrd.R;
import java.util.List;

public class AbsenAdapter extends ArrayAdapter<AbsenData> {

    Context context;
    List<AbsenData> arrayListAbsent;

    public AbsenAdapter(@NonNull Context context, List<AbsenData> arrayListAbsent) {
        super(context, R.layout.list_absen, arrayListAbsent);

        this.context = context;
        this.arrayListAbsent = arrayListAbsent;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_absen, null, true);

        TextView tvId = view.findViewById(R.id.c_absen_id);
        TextView tvNama = view.findViewById(R.id.c_absen_nama);
        TextView tvJam = view.findViewById(R.id.c_absen_jam);
        TextView tvTanggal = view.findViewById(R.id.c_absen_tanggal);
        TextView tvKet = view.findViewById(R.id.c_absen_ket);

        tvId.setText(arrayListAbsent.get(position).getId());
        tvNama.setText(arrayListAbsent.get(position).getU_nama());
        tvJam.setText(arrayListAbsent.get(position).getJam());
        tvTanggal.setText(arrayListAbsent.get(position).getTanggal());
        tvKet.setText(arrayListAbsent.get(position).getKeterangan());

        return view;
    }
}
