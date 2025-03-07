package com.nhom3.bduan1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.login.DangNhap;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        LinearLayout llQuanLyPhongTro = view.findViewById(R.id.llQuanLyPhongTro);
        LinearLayout llDanhSachPhong = view.findViewById(R.id.llDanhSachPhong);
        LinearLayout llThongBao = view.findViewById(R.id.llThongBao);
        LinearLayout llQuanLyHoaDon = view.findViewById(R.id.llQuanLyHoaDon);
        LinearLayout llXuLyYeuCau = view.findViewById(R.id.llXuLyYeuCauHoTro);
        LinearLayout llHopDong = view.findViewById(R.id.llQuanLyHopDong);
        LinearLayout llDichVu = view.findViewById(R.id.llQuanLiDichVu);
        LinearLayout llDangXuat = view.findViewById(R.id.llDangXuat);

        llHopDong.setOnClickListener(view1 -> {
            FragmentTransaction HopDongTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            HopDongTransaction.replace(R.id.fragmentContainer, new QuanLyHopDongFragment());
            HopDongTransaction.addToBackStack(null);
            HopDongTransaction.commit();

        });

        llXuLyYeuCau.setOnClickListener(view1 -> {
            FragmentTransaction xuLyYeuCauTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            xuLyYeuCauTransaction.replace(R.id.fragmentContainer, new XuLyYeuCauAdminFragment());
            xuLyYeuCauTransaction.addToBackStack(null);
            xuLyYeuCauTransaction.commit();

        });

        llQuanLyHoaDon.setOnClickListener(view1 -> {
            FragmentTransaction hoaDonTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            hoaDonTransaction.replace(R.id.fragmentContainer, new HomeQuanLyHoaDonAdminFragment());
            hoaDonTransaction.addToBackStack(null);
            hoaDonTransaction.commit();
        });

        llThongBao.setOnClickListener(view1 -> {
            FragmentTransaction thongbaoTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            thongbaoTransaction.replace(R.id.fragmentContainer, new ThongBaoAdminFragment());
            thongbaoTransaction.addToBackStack(null);
            thongbaoTransaction.commit();
        });


        llDanhSachPhong.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, new DanhSachPhongFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });


        llQuanLyPhongTro.setOnClickListener(v -> {

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, new QuanLyPhongTroFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        llDichVu.setOnClickListener(view1 -> {
            FragmentTransaction dichVuTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            dichVuTransaction.replace(R.id.fragmentContainer, new DichVu_AdminFragment());
            dichVuTransaction.addToBackStack(null);
            dichVuTransaction.commit();

        });
        llDangXuat.setOnClickListener(view1 -> {
            mAuth.signOut();
            Intent intent = new Intent(getContext(), DangNhap.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            requireActivity().finish();
        });
        return view;
    }
}
