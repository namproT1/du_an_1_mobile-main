package com.nhom3.bduan1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.appcompatactivity.HoaDonChiTietTungPhongActivity;
import com.nhom3.bduan1.R;
import com.nhom3.bduan1.models.QuanLyPhongTroModels;

import java.util.List;

public class QuanLiHoaDonAdminAdapter extends RecyclerView.Adapter<QuanLiHoaDonAdminAdapter.QuanLiHoaDonAdminViewHolder> {
    private final List<QuanLyPhongTroModels> listPhongTro;
    private final Context context;
    private FragmentManager fragmentManager;
    public QuanLiHoaDonAdminAdapter(List<QuanLyPhongTroModels> listPhongTro, Context context) {
        this.listPhongTro = listPhongTro;
        this.context = context;
    }

    @NonNull
    @Override
    public QuanLiHoaDonAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuanLiHoaDonAdminViewHolder(LayoutInflater.from(context).inflate(R.layout.item_xuat_hoa_don,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull QuanLiHoaDonAdminViewHolder holder, int position) {
        QuanLyPhongTroModels quanLyPhongTroModel=listPhongTro.get(position);
        holder.tvTenPhong.setText(quanLyPhongTroModel.getTenPhong());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HoaDonChiTietTungPhongActivity.class);
            intent.putExtra("tenPhong", quanLyPhongTroModel.getTenPhong());
            intent.putExtra("maPhong",quanLyPhongTroModel.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listPhongTro!=null? listPhongTro.size():0;
    }


    public class QuanLiHoaDonAdminViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTenPhong;
        public QuanLiHoaDonAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong=itemView.findViewById(R.id.tvtenphong_hoadon);
        }
    }
}