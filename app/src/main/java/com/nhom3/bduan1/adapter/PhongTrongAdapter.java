package com.nhom3.bduan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.R;import com.nhom3.bduan1.models.QuanLyPhongTroModels;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PhongTrongAdapter extends RecyclerView.Adapter<PhongTrongAdapter.phongtrongHolder> {
    private final Context context;
    private final List<QuanLyPhongTroModels> list;

    public PhongTrongAdapter(Context context, List<QuanLyPhongTroModels> list) {
        this.context = context;
        this.list = list;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public phongtrongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quan_ly_danh_sach_trong, parent, false);
        return new phongtrongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull phongtrongHolder holder, int position) {
        QuanLyPhongTroModels model = list.get(position);
        holder.TvTenPhong.setText(model.getTenPhong());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class phongtrongHolder extends RecyclerView.ViewHolder {
        private final TextView TvTenPhong;
        public phongtrongHolder(@NonNull View itemView) {
            super(itemView);
            TvTenPhong = itemView.findViewById(R.id.tvtenphong_dspct);
        }
    }
}
