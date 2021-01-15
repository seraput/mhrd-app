package com.example.mhrd.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mhrd.Models.UserData;
import com.example.mhrd.R;

import java.util.List;

public class AdapterUser extends ArrayAdapter<UserData> {

    Context context;
    List<UserData> arrayListDataUser;

    public AdapterUser(@NonNull Context context, List<UserData> arrayListDataUser) {
        super(context, R.layout.list_user_data, arrayListDataUser);

        this.context = context;
        this.arrayListDataUser = arrayListDataUser;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_data, null, true);

        TextView tvEmail = view.findViewById(R.id.txtEmail);
        TextView tvNama = view.findViewById(R.id.txtNama);
        TextView tvStatus = view.findViewById(R.id.txtLevel);

        tvEmail.setText(arrayListDataUser.get(position).getEmail());
        tvNama.setText(arrayListDataUser.get(position).getNama());
        tvStatus.setText(arrayListDataUser.get(position).getBranch());

        return view;
    }
}
