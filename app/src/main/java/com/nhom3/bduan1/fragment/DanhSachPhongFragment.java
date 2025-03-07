package com.nhom3.bduan1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.nhom3.bduan1.R;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DanhSachPhongFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_phong, container, false);
        requireActivity().getWindow().setStatusBarColor(
                ContextCompat.getColor(requireContext(), R.color.your_primary_dark_color)
        );


        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 pager2 = view.findViewById(R.id.view_pager);

        FragmentActivity activity = getActivity();
        if(activity != null) {
            HoatDongFragmentStateAdapter hoatDong = new HoatDongFragmentStateAdapter(activity);
            pager2.setAdapter(hoatDong);

            new TabLayoutMediator(tabLayout, pager2, (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText("Phòng đã thuê");
                        break;
                    case 1:
                        tab.setText("Phòng trống");
                        break;
                }
            }).attach();

        }


        return view;
    }
}
