package com.example.mhrd.Extends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mhrd.Controller.AdapterUser;
import com.example.mhrd.Helper.Volley.Server;
import com.example.mhrd.Models.UserData;
import com.example.mhrd.R;

import java.util.ArrayList;

public class FragmentDialogBox extends DialogFragment {

    ListView listPilih;
    AdapterUser adapterUser;
    public static ArrayList<UserData> userDataArrayList = new ArrayList<>();
    private String LoadApi = Server.URL_API + "loadUser.php";
    UserData userData;
    ListView myList;

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//
//        String [] load = getActivity().getResources().getStringArray(R.array.data);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        builder.setTitle("Data List");
//        builder.setItems(load, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                Toast.makeText(getActivity(), "Di Pilih"+load[which], Toast.LENGTH_SHORT).show();
//
//                ((AdminMainActivity)getActivity()).mtData.setText(load[which]);
//            }
//        });
//
//        return builder.create();
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_layout, null);

        myList =view.findViewById(R.id.myList);




        return view;
    }

}
