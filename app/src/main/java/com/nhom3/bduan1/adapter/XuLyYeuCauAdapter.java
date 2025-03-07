package com.nhom3.bduan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.models.QuanLyPhongTroModels;

import java.util.List;

public class XuLyYeuCauAdapter extends RecyclerView.Adapter<XuLyYeuCauAdapter.ViewHolder> {
    private final Context context;
    private final List<QuanLyPhongTroModels> list;

    public XuLyYeuCauAdapter(Context context, List<QuanLyPhongTroModels> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xu_ly_yeu_cau, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuanLyPhongTroModels phongTro = list.get(position);

        // Kiểm tra và gán giá trị an toàn
        holder.tvtenphong.setText(phongTro.getTenPhong() != null ? phongTro.getTenPhong() : "Không có tên phòng");
//        holder.tvyeucau.setText(phongTro.get() != null ? phongTro.getYeuCau() : "Không có yêu cầu");
//        holder.sptrangthai.setText(
//                phongTro.getTrangThaiYeuCau() != null && !phongTro.getTrangThaiYeuCau().isEmpty()
//                        ? phongTro.getTrangThaiYeuCau()
//                        : "Chưa xử lý"
//        );

        // Khi người dùng nhấn vào item
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvtenphong;
        final TextView tvyeucau;
        final TextView sptrangthai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtenphong = itemView.findViewById(R.id.tvtenphong_xlyc);
            tvyeucau = itemView.findViewById(R.id.tvyeucau_xlyc);
            sptrangthai = itemView.findViewById(R.id.sptrangthai_xlyc);
        }
    }
}
