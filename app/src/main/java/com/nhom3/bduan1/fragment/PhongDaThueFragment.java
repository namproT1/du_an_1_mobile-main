package com.nhom3.bduan1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.adapter.PhongDaThueAdapter;
import com.nhom3.bduan1.models.QuanLyPhongTroModels;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PhongDaThueFragment extends Fragment {
    FirebaseFirestore db;
    List<QuanLyPhongTroModels> list;
    RecyclerView recyclerView;
    PhongDaThueAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_phong_da_thue, container, false);
        recyclerView = view.findViewById(R.id.rvphongdathue);
        list = new ArrayList<>();
        adapter = new PhongDaThueAdapter(requireContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        FirebaseApp.initializeApp(requireContext());
        db = FirebaseFirestore.getInstance();

        // Gọi hàm để lấy dữ liệu
        fetchRentedRooms();

        return view;
    }

    private void fetchRentedRooms() {
        db.collection("PhongTro")
                .whereEqualTo("trangThaiPhong", "Đã thuê")  // Lọc chỉ phòng có trạng thái "Đã cho thuê"
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }
                    if (value != null) {
                        list.clear();  // Xóa danh sách hiện tại để tránh trùng lặp
                        for (QueryDocumentSnapshot doc : value) {
                            QuanLyPhongTroModels phongTro = doc.toObject(QuanLyPhongTroModels.class);
                            phongTro.setId(doc.getId());  // Lấy ID tài liệu nếu cần thiết
                            list.add(phongTro);
                        }
                        adapter.notifyDataSetChanged();  // Cập nhật RecyclerView sau khi thêm dữ liệu
                    }
                });
    }
}
