package com.nhom3.bduan1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nhom3.bduan1.R;

public class HomeQuanLyHoaDonAdminFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_hoa_don_admin, container, false);

        LinearLayout llxuathoadon = view.findViewById(R.id.ll_xuat_hoa_don);
        LinearLayout llQuanLiHoaDon=view.findViewById(R.id.ll_quan_li_hoa_don);
        ImageView imgbackQuanLiHoaDonAdmin=view.findViewById(R.id.imgbackQuanLiHoaDonAdmin);
        imgbackQuanLiHoaDonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        llQuanLiHoaDon.setOnClickListener(v->{
            addFragment(new QuanLiHoaDonAdminFragment());
        });
        llxuathoadon.setOnClickListener(v -> {

            addFragment(new XuatHoaDonAdminFragment());
        });
       return view;
    }
    private void addFragment(Fragment fragment){
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
