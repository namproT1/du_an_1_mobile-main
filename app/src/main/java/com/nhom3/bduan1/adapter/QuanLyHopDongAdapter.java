package com.nhom3.bduan1.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.appcompatactivity.QuanLiHopDongChiTietActivity;
import com.nhom3.bduan1.models.QuanLiHopDongModels;
import com.nhom3.bduan1.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class QuanLyHopDongAdapter extends RecyclerView.Adapter<QuanLyHopDongAdapter.ViewHolder> {
    final Context context;
    final List<QuanLiHopDongModels> list;

    public QuanLyHopDongAdapter(Context context, List<QuanLiHopDongModels> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hop_dong_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        QuanLiHopDongModels model = list.get(position);
        getTenPhong(model.getIdPhong(), holder);
        holder.tvNgayThue.setText(model.getNgayBatDau());
        holder.tvNgayHetHan.setText(model.getNgayKetThuc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuanLiHopDongChiTietActivity.class);
                intent.putExtra("id", model.getIdHopDong());
                intent.putExtra("idPhong", model.getIdPhong());
                intent.putExtra("tenKhachHang", model.getTenKhachHang());
                intent.putExtra("tenPhong", holder.tvTenPhong.getText().toString());
                intent.putExtra("ngayBatDau", holder.tvNgayThue.getText().toString());
                intent.putExtra("ngayKetThuc", holder.tvNgayHetHan.getText().toString());
                intent.putExtra("emailKhachHang", model.getEmailKhachHang());
                intent.putExtra("cccd", model.getCccd());
                intent.putExtra("soNguoi", model.getSoNguoi());
                intent.putExtra("tienCoc",model.getTienCoc());
                Log.d("idPhong",model.getTienCoc());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setPositiveButton("Có", (dialogInterface, i) -> {
                    deleteHopDong(model.getIdHopDong());
                    xoaHopDongChiTiet(model.getIdHopDong());
                    updateStatusPhongTro(model.getIdPhong());
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,list.size());

                });
                builder.setNegativeButton("Không", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog dialog=builder.create();
                dialog.show();
                return true;
            }
        });
    }
    private void deleteHopDong(String idHopDong){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HopDong")
                .document(idHopDong)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                });
    }
    private void xoaHopDongChiTiet(String idHopDong){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HopDongChiTiet")
                .get()
                .addOnSuccessListener(documentSnapshots -> {
                   for (QueryDocumentSnapshot documentSnapshot:documentSnapshots){
                        if (documentSnapshot.getString("idHopDong").equals(idHopDong)){
                            String idHopDongChiTiet=documentSnapshot.getId();
                            db.collection("HopDongChiTiet")
                                    .document(idHopDongChiTiet)
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        //Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    }).addOnFailureListener(e -> {
                                       // Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                    });
                       }
                   }
                });
    }
    private void updateStatusPhongTro(String idPhong){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("PhongTro")
                .document(idPhong)
                .update("trangThaiPhong","Còn trống")
                .addOnSuccessListener(aVoid -> {
//                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    //Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                });
    }
    private void getTenPhong(String idPhong, ViewHolder holder){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PhongTro")
                .document(idPhong)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String tenPhong = documentSnapshot.getString("tenPhong");
                        holder.tvTenPhong.setText(tenPhong);
                    }
                });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTenPhong;
        final TextView tvNgayThue;
        final TextView tvNgayHetHan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvtenphong_HopDong);
            tvNgayThue = itemView.findViewById(R.id.tvNgayThue);
            tvNgayHetHan = itemView.findViewById(R.id.tvNgayHetHan);
        }
    }
}
