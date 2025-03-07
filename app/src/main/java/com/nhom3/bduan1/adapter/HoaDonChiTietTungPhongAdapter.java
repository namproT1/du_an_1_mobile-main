package com.nhom3.bduan1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.models.QuanLiHopDongModels;
import com.nhom3.bduan1.R;
import com.nhom3.bduan1.models.QuanLyPhongTroModels;
import com.nhom3.bduan1.models.HoaDonModels;
import com.nhom3.bduan1.models.HoaDonChiTietModels;
import com.nhom3.bduan1.models.HoaDonDichVuModels;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietTungPhongAdapter extends RecyclerView.Adapter<HoaDonChiTietTungPhongAdapter.HoaDonChiTietTungPhongViewHolder> {
    private final List<HoaDonChiTietModels> hoaDonChiTietList;
    private final Context context;
    public HoaDonChiTietTungPhongAdapter(List<HoaDonChiTietModels> hoaDonChiTietList, Context context) {
        this.hoaDonChiTietList = hoaDonChiTietList;
        this.context = context;
    }

    @NonNull
    @Override
    public HoaDonChiTietTungPhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HoaDonChiTietTungPhongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_hoa_don, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonChiTietTungPhongViewHolder holder, int position) {
        HoaDonChiTietModels hoaDonChiTiet=hoaDonChiTietList.get(position);
        holder.tvTongTien.setText(hoaDonChiTiet.getSoTienThanhToan()+" VND");
        getTienDichVu(hoaDonChiTiet.getIdHoaDonDichVu(),holder.tvTienDien,holder.tvTienNuoc,holder.tvTienThangMay,holder.tvTienMang,holder.tvTienRac);
        getHopDong(hoaDonChiTiet.getIdPhong(),holder.tvSoNguoi );
        getPhongTro(hoaDonChiTiet.getIdPhong(),holder.tvPhong);

        List<String> trangThaiHoaDon=new ArrayList<>();
        trangThaiHoaDon.add("Chưa thanh toán");
        trangThaiHoaDon.add("Đã thanh toán");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,trangThaiHoaDon);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spnTrangThaiHoaDon.setAdapter(adapter);
        final boolean[] isSpinnerFirstLoad = {true};

        getNgayXuatHoaDon(hoaDonChiTiet.getIdHoaDon(),holder.tvNgayXuatHoaDon,holder.spnTrangThaiHoaDon,isSpinnerFirstLoad);

        holder.spnTrangThaiHoaDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (isSpinnerFirstLoad[0]) {
                    isSpinnerFirstLoad[0] = false;
                } else {
                    String trangThai = (String) parent.getItemAtPosition(i);
                    updateTrangThaiHoaDon(hoaDonChiTiet.getIdHoaDon(), trangThai.equals("Đã thanh toán"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return hoaDonChiTietList!=null?hoaDonChiTietList.size():0;
    }

    public class HoaDonChiTietTungPhongViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNgayXuatHoaDon;
        final TextView tvSoNguoi;
        final TextView tvPhong;
        final TextView tvTienDien;
        final TextView tvTienNuoc;
        final TextView tvTienThangMay;
        final TextView tvTienMang;
        final TextView tvTienRac;
        final TextView tvTongTien;
        final Spinner spnTrangThaiHoaDon;
        public HoaDonChiTietTungPhongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNgayXuatHoaDon = itemView.findViewById(R.id.tv_ngayXuatHoaDon_cthd);
            tvSoNguoi = itemView.findViewById(R.id.tv_soNguoi_cthd);
            tvPhong = itemView.findViewById(R.id.tv_phong_cthd);
            tvTienDien = itemView.findViewById(R.id.tv_tienDien_cthd);
            tvTienNuoc = itemView.findViewById(R.id.tv_tienNuoc_cthd);
            tvTienThangMay = itemView.findViewById(R.id.tv_tienThangMay_cthd);
            tvTienMang = itemView.findViewById(R.id.tv_TienMang_cthd);
            tvTongTien = itemView.findViewById(R.id.tv_tongTien_cthd);
            tvTienRac = itemView.findViewById(R.id.tv_tienRac_cthd);
            spnTrangThaiHoaDon=itemView.findViewById(R.id.spnTrangThaiHoaDon);

        }
    }
    private void updateTrangThaiHoaDon(String idHoaDon,boolean trangThai){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HoaDon")
                .document(idHoaDon)
                .update("trangThaiHoaDon",trangThai)
                .addOnSuccessListener(aVoid -> {
                    Log.d("updateTrangThaiHoaDon", "Cập nhật trạng thái hóa đơn thành công");
                })
                .addOnFailureListener(e -> {
                    Log.e("updateTrangThaiHoaDon", "Lỗi khi cập nhật trạng thái hóa đơn: " + e.getMessage());
                });
    }
    private void getNgayXuatHoaDon(String idHoaDon,TextView tvNgayXuatHoaDon,Spinner spnTrangThaiHoaDon,boolean[] isSpinnerFirstLoad){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HoaDon")
                .document(idHoaDon)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        HoaDonModels hoaDon = documentSnapshot.toObject(HoaDonModels.class);
                        tvNgayXuatHoaDon.setText(hoaDon.getNgayTao());
                        if (hoaDon.isTrangThaiHoaDon()){
                            spnTrangThaiHoaDon.setSelection(1);
                        }else {
                            spnTrangThaiHoaDon.setSelection(0);
                        }
                        isSpinnerFirstLoad[0] = false;
                }
                });
    }
    private void getHopDong(String idPhong,TextView tvSoNguoi){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HopDong")
                .whereEqualTo("idPhong",idPhong)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            QuanLiHopDongModels quanLiHopDongModel=documentSnapshot.toObject(QuanLiHopDongModels.class);
                            if (quanLiHopDongModel.getIdPhong().equals(idPhong)){
                                tvSoNguoi.setText(quanLiHopDongModel.getSoNguoi()+" người");
                                return;
                            }
                    }
                });
    }
    private void getPhongTro(String idPhong,TextView tvTenPhong){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("PhongTro")
                .document(idPhong)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        QuanLyPhongTroModels phongTroModel = documentSnapshot.toObject(QuanLyPhongTroModels.class);
                        tvTenPhong.setText(phongTroModel.getTenPhong());
                    }
                });
    }
    private void getTienDichVu(String idHoaDonDichVu,TextView tvTienDien,TextView tvTienNuoc,
                               TextView tvTienThangMay,TextView tvTienMang,TextView tvTienRac){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HoaDonDichVu")
                .document(idHoaDonDichVu)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        HoaDonDichVuModels hoaDonDichVu = documentSnapshot.toObject(HoaDonDichVuModels.class);
                        tvTienDien.setText(hoaDonDichVu.getTienDien() + " VND"+" /"+hoaDonDichVu.getSoDien()+" kWh");
                        tvTienNuoc.setText(hoaDonDichVu.getTienNuoc() + " VND"+" /"+hoaDonDichVu.getSoNuoc()+" m3");
                        tvTienThangMay.setText(hoaDonDichVu.getTienThangMay() + " VND");
                        tvTienMang.setText(hoaDonDichVu.getTienMang() + " VND");
                        tvTienRac.setText(hoaDonDichVu.getTienRac() + " VND");
                    }
                });
    }
}
