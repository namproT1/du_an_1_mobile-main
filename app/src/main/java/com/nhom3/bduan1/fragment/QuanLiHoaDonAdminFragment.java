package com.nhom3.bduan1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.adapter.QuanLiHoaDonAdminAdapter;
import com.nhom3.bduan1.models.QuanLyPhongTroModels;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class QuanLiHoaDonAdminFragment extends Fragment {

    private RecyclerView rcvQuanliHoaDon;
    private QuanLiHoaDonAdminAdapter adapter;
    private List<QuanLyPhongTroModels> list;
    private FirebaseFirestore db;
    public QuanLiHoaDonAdminFragment() {
        // Required empty public constructor
    }


    public static QuanLiHoaDonAdminFragment newInstance(String param1, String param2) {
        return new QuanLiHoaDonAdminFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_quan_li_hoa_don_admin, container, false);
        rcvQuanliHoaDon=view.findViewById(R.id.rcvQuanLiHoaDon);
        ImageView imgback = view.findViewById(R.id.imgBackQuanLiHoaDon);
        list=new ArrayList<>();
        db=FirebaseFirestore.getInstance();

        imgback.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        getPhongTro();
        return view;

    }
    private void getPhongTro(){
        db.collection("PhongTro")
                .whereEqualTo("trangThaiPhong","Đã thuê")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot:task.getResult()){
                            QuanLyPhongTroModels quanLyPhongTroModel=documentSnapshot.toObject(QuanLyPhongTroModels.class);
                            quanLyPhongTroModel.setId(documentSnapshot.getId());
                            list.add(quanLyPhongTroModel);
                            adapter=new QuanLiHoaDonAdminAdapter(list,getContext());
                            adapter.notifyDataSetChanged();
                            rcvQuanliHoaDon.setLayoutManager(new LinearLayoutManager(getContext()));
                            rcvQuanliHoaDon.setAdapter(adapter);
                        }
                    }
                });
    }
}













