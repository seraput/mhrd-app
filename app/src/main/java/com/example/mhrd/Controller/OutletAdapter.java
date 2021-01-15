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
import com.example.mhrd.R;
import java.util.List;

public class OutletAdapter extends ArrayAdapter<OutletData> {

    Context context;
    List<OutletData> arrayListOutlet;

    public OutletAdapter(@NonNull Context context, List<OutletData> arrayListOutlet) {
        super(context, R.layout.custom_list_outlet, arrayListOutlet);

        this.context = context;
        this.arrayListOutlet = arrayListOutlet;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_outlet, null, true);

        TextView tvId = view.findViewById(R.id.c_out_id);
        TextView tvName = view.findViewById(R.id.c_out_nama);
        TextView tvTelp = view.findViewById(R.id.c_out_telp);
        TextView tvKota = view.findViewById(R.id.c_out_kota);
        TextView tvType = view.findViewById(R.id.c_out_type);

        tvId.setText(arrayListOutlet.get(position).getId());
        tvName.setText(arrayListOutlet.get(position).getNama());
        tvTelp.setText(arrayListOutlet.get(position).getTelp());
        tvKota.setText(arrayListOutlet.get(position).getKota());
        tvType.setText(arrayListOutlet.get(position).getType());

        return view;
    }
}
