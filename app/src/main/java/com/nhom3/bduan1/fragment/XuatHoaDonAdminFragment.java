    package com.nhom3.bduan1.fragment;

    import android.os.Bundle;
    import android.util.Log;
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
    import com.nhom3.bduan1.adapter.HoaDonAdapterAdmin;
    import com.nhom3.bduan1.models.QuanLyPhongTroModels;
    import com.google.firebase.FirebaseApp;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.util.ArrayList;
    import java.util.List;

    public class XuatHoaDonAdminFragment extends Fragment {
        FirebaseFirestore db;
        List<QuanLyPhongTroModels> list;
        RecyclerView recyclerView;
        HoaDonAdapterAdmin adapter;
        ImageView imgback;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(com.nhom3.bduan1.R.layout.fragment_xuat_hoa_don_admin, container, false);
            FirebaseApp.initializeApp(requireContext());

            db = FirebaseFirestore.getInstance();
            recyclerView = view.findViewById(R.id.rvHoaDon);
            imgback = view.findViewById(R.id.imgBackXuatHoaDon);
            imgback.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });
            list = new ArrayList<>();
            adapter = new HoaDonAdapterAdmin(requireContext(), list);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(adapter);
            OpenDanhSach();

            return view;
        }


        public void OpenDanhSach() {
           db.collection("PhongTro")
                   .whereEqualTo("trangThaiPhong", "Đã thuê")
                   .get()
                   .addOnCompleteListener(task -> {
                       if (task.isSuccessful()) {
                           for (DocumentSnapshot document : task.getResult()) {
                               QuanLyPhongTroModels model = document.toObject(QuanLyPhongTroModels.class);
                               model.setId(document.getId());
                               list.add(model);
                               adapter.notifyDataSetChanged();
                               Log.d("phong",model.getId());
                           }
                       }
                   });

        }
    }
