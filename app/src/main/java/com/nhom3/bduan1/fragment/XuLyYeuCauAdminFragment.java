package com.nhom3.bduan1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.adapter.XuLyYeuCauAdapter;
import com.nhom3.bduan1.models.QuanLyPhongTroModels;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class XuLyYeuCauAdminFragment extends Fragment {
    FirebaseFirestore db;
    List<QuanLyPhongTroModels> list;
    RecyclerView recyclerView;
    XuLyYeuCauAdapter adapter;
    ImageView imgBackXuLyYeuCau;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xu_ly_yeu_cau_admin, container, false);
        recyclerView = view.findViewById(R.id.rvYeuCau);
        imgBackXuLyYeuCau = view.findViewById(R.id.imgBackXuLiYeuCau);
        imgBackXuLyYeuCau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        list = new ArrayList<>();
        adapter = new XuLyYeuCauAdapter(requireContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        FirebaseApp.initializeApp(requireContext());
        db = FirebaseFirestore.getInstance();
        fetchDataFromFirestore();
        return view;
    }


    private void fetchDataFromFirestore() {
        db.collection("PhongTro").addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {

                for (DocumentChange doc : value.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        QuanLyPhongTroModels phongTro = doc.getDocument().toObject(QuanLyPhongTroModels.class);
                        phongTro.setId(doc.getDocument().getId()); // Set document ID
                        list.add(phongTro);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
