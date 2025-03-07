package com.nhom3.bduan1.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HoatDongFragmentStateAdapter extends FragmentStateAdapter {
    public HoatDongFragmentStateAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PhongDaThueFragment();
            case 1:
                return new PhongTrongFragment();
            default:
                return new PhongDaThueFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
