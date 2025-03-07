package com.nhom3.bduan1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.models.QuanLiHopDongModels;
import com.nhom3.bduan1.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HopDongUserAdapter extends RecyclerView.Adapter<HopDongUserAdapter.HopDongUserViewHolder> {

    private final List<QuanLiHopDongModels> listHopDong;

    public HopDongUserAdapter(List<QuanLiHopDongModels> listHopDong, Context context) {
        this.listHopDong = listHopDong;
    }

    @NonNull
    @Override
    public HopDongUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HopDongUserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hopdong_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HopDongUserViewHolder holder, int position) {
        QuanLiHopDongModels quanLiHopDongModel = listHopDong.get(position);
        if (quanLiHopDongModel == null) {
            return;
        }
        Log.d("zzz","hihi"+ quanLiHopDongModel.getTienCoc());
        holder.ngayBatDauUser.setText("Ngày bắt đầu: "+quanLiHopDongModel.getNgayBatDau());
        holder.ngayKetThucUser.setText("Ngày kết thúc: "+quanLiHopDongModel.getNgayKetThuc());
        holder.tienCocUser.setText(quanLiHopDongModel.getTienCoc()+"đ");
        holder.tvEmaiUser.setText(quanLiHopDongModel.getEmailKhachHang());
        holder.tvTenUser.setText(quanLiHopDongModel.getTenKhachHang());
        holder.tvSoNguoiThue.setText(quanLiHopDongModel.getSoNguoi()+" người");
        getPhong(quanLiHopDongModel.getIdPhong(),holder);
        getDichVu(holder);
    }

    @Override
    public int getItemCount() {
        return listHopDong.size();
    }

    public class HopDongUserViewHolder extends RecyclerView.ViewHolder {
        private final TextView tenPhongUser;
        private final TextView ngayBatDauUser;
        private final TextView ngayKetThucUser;
        private final TextView tienPhongUser;
        private final TextView tienCocUser;
        private final TextView tvSoNguoiThue;
        private final TextView tienDienUser;
        private final TextView tienNuocUser;
        private final TextView tienMangUser;
        private final TextView tienThangMayUser;
        private final TextView tienRacUser;
        private final TextView tvEmaiUser;
        private final TextView tvTenUser;
        public HopDongUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tenPhongUser = itemView.findViewById(R.id.tvTenPhongUser);
            ngayBatDauUser = itemView.findViewById(R.id.tvNgayBatDauUser);
            ngayKetThucUser = itemView.findViewById(R.id.tvNgayKetThucUser);
            tienPhongUser = itemView.findViewById(R.id.tvTienPhongUser);
            tienCocUser = itemView.findViewById(R.id.tvTienCocUser);
            tienDienUser = itemView.findViewById(R.id.tvTienDienUser);
            tienNuocUser = itemView.findViewById(R.id.tvTienNuocUser);
            tienMangUser = itemView.findViewById(R.id.tvTienMangUser);
            tienThangMayUser = itemView.findViewById(R.id.tvTienThangMayUser);
            tienRacUser = itemView.findViewById(R.id.tvTienRacUser);
            tvEmaiUser = itemView.findViewById(R.id.tvEmailUser);
            tvTenUser = itemView.findViewById(R.id.tvTenUser);
            tvSoNguoiThue = itemView.findViewById(R.id.tvSoNguoiThueUser);
        }
    }
    private void getDichVu(HopDongUserViewHolder holder){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DichVu")
                .document("vdYaZn0fm5owoxA1IvoA")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            holder.tienDienUser.setText(document.getString("tienDien")+"đ/kwh");
                            holder.tienNuocUser.setText(document.getString("tienNuoc")+"đ/khối");
                            holder.tienMangUser.setText(document.getString("tienMang")+"đ/phòng/tháng");
                            holder.tienThangMayUser.setText(document.getString("tienThangMay")+"đ/người/tháng");
                            holder.tienRacUser.setText(document.getString("tienRac")+"đ/người/tháng");


                        }
                    }
                });
    }
    private void getPhong(String idPhong,HopDongUserViewHolder holder){
        Log.d("idPhong",idPhong);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PhongTro")
                .document(idPhong)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            holder.tienPhongUser.setText(document.getDouble("tienPhong")+"đ/tháng");
                            holder.tenPhongUser.setText("Tên phòng: "+document.getString("tenPhong"));
                            Log.d("tenPhong",document.getString("tenPhong"));
                        }
                    }
                });
    }
};
