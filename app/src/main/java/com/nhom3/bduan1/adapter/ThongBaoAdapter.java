package com.nhom3.bduan1.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.models.ThongBaoModels;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ThongBaoAdapter extends RecyclerView.Adapter{
    private final Context context;
    private final List<ThongBaoModels> list;

    public ThongBaoAdapter(Context context, List<ThongBaoModels> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_thong_bao_admin, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ThongBaoModels thongbao = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvtieude.setText(thongbao.getTieuDe());
        viewHolder.tvchitiet.setText(thongbao.getChiTiet());
        viewHolder.tvngaydang.setText(thongbao.getNgay());
        viewHolder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy ID của thông báo để xóa
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xoá thông báo không?");
                builder.setPositiveButton("Có", (dialog, which) -> {
                    //
                    String id = thongbao.getId();

                    // Xóa từ Firebase Firestore (giả sử bạn lưu theo "id")
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("ThongBao")
                            .document(String.valueOf(id)) // Sử dụng ID để xác định tài liệu
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                // Xóa khỏi danh sách và cập nhật RecyclerView
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Xóa không thành công: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });


        viewHolder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate layout sửa thông báo
                View dialogView = LayoutInflater.from(context).inflate(R.layout.item_update_thong_bao, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogView);

                // Lấy các view từ layout
                EditText etTieuDe = dialogView.findViewById(R.id.etTieuDe_ud);
                EditText etChiTiet = dialogView.findViewById(R.id.etChiTiet_ud);
                Button btnUpdate = dialogView.findViewById(R.id.btnud_ud);

                // Đặt dữ liệu cũ
                etTieuDe.setText(thongbao.getTieuDe());
                etChiTiet.setText(thongbao.getChiTiet());

                // Hiển thị dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                // Xử lý khi nhấn nút Update
                btnUpdate.setOnClickListener(v -> {
                    // Lấy thông tin mới từ người dùng
                    String newTieuDe = etTieuDe.getText().toString().trim();
                    String newChiTiet = etChiTiet.getText().toString().trim();

                    if (newTieuDe.isEmpty() || newChiTiet.isEmpty()) {
                        Toast.makeText(context, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Cập nhật thông báo
                    thongbao.setTieuDe(newTieuDe);
                    thongbao.setChiTiet(newChiTiet);

                    // Ghi dữ liệu mới vào Firestore
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("ThongBao")
                            .document(String.valueOf(thongbao.getId())) // ID của tài liệu
                            .set(thongbao)
                            .addOnSuccessListener(aVoid -> {
                                // Cập nhật RecyclerView
                                notifyItemChanged(position);
                                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss(); // Đóng dialog sau khi thành công
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvtieude;
        final TextView tvchitiet;
        final TextView tvngaydang;
        final ImageView btnxoa;
        final ImageView btnsua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtieude = itemView.findViewById(R.id.tvtieude_tb);
            tvchitiet = itemView.findViewById(R.id.tvChiTiet_tb);
            tvngaydang = itemView.findViewById(R.id.tvNgayDang);
            btnxoa = itemView.findViewById(R.id.btnxoa_thongbao);
            btnsua = itemView.findViewById(R.id.btncapnhat_thongbao);

        }
    }
}
