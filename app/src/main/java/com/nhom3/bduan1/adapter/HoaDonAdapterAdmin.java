package com.nhom3.bduan1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.models.QuanLiHopDongModels;
import com.nhom3.bduan1.R;
import com.nhom3.bduan1.models.QuanLyPhongTroModels;
import com.nhom3.bduan1.models.HoaDonModels;
import com.nhom3.bduan1.models.HoaDonChiTietModels;
import com.nhom3.bduan1.models.HoaDonDichVuModels;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HoaDonAdapterAdmin extends RecyclerView.Adapter<HoaDonAdapterAdmin.ViewHolder> {
    final Context context;
    final List<QuanLyPhongTroModels> list;
    private int soNguoiThue=0;
    private FirebaseFirestore db;
    private String tienDienDichVu=null;
    private String tienNuocDichVu=null;
    private String emailKhachHang=null;
    private String giaPhong=null;
    private String idHdDichVu=null;

    public HoaDonAdapterAdmin(Context context, List<QuanLyPhongTroModels> list) {
        this.context = context;
        this.list = list;
        db = FirebaseFirestore.getInstance(); // Khởi tạo Firestore
    }

    @NonNull
    @Override
    public HoaDonAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xuat_hoa_don, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapterAdmin.ViewHolder holder, int position) {
        QuanLyPhongTroModels quanLyPhongTroModel = list.get(position);
        holder.tvTenPhong.setText(quanLyPhongTroModel.getTenPhong());
        holder.itemView.setOnClickListener(v -> {
            // Kiểm tra xem hóa đơn có tồn tại hay không, nếu có thì mở màn hình cập nhật
           hienthidailog(quanLyPhongTroModel.getId());
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTenPhong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvtenphong_hoadon);
        }
    }
    private void getInforPhong(String idPhong,EditText edtTenPhong,EditText edtGiaPhong){
        db=FirebaseFirestore.getInstance();
        db.collection("PhongTro")
                .document(idPhong)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot snapshot=task.getResult();
                    if(snapshot.exists()){
                        QuanLyPhongTroModels quanLyPhongTroModel=snapshot.toObject(QuanLyPhongTroModels.class);
                        edtTenPhong.setText(quanLyPhongTroModel.getTenPhong());
                        edtGiaPhong.setText(quanLyPhongTroModel.getTienPhong()+"đ");
                        giaPhong=String.valueOf(quanLyPhongTroModel.getTienPhong());
                        Log.d("hihi",giaPhong);
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Lỗi lấy thông tin phòng", Toast.LENGTH_SHORT).show();
                });
    }
    private void getHopDong(String idPhong,EditText edtSoNguoi,EditText edtTienMang,
                            EditText edtTienThangmay,EditText edtTienRac,
                            TextView tvThangMay,TextView tvTienRac){
        db=FirebaseFirestore.getInstance();
        db.collection("HopDong")
                .whereEqualTo("idPhong",idPhong)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            QuanLiHopDongModels quanLiHopDongModel = snapshot.toObject(QuanLiHopDongModels.class);
                            if (quanLiHopDongModel.getIdPhong().equals(idPhong)){
                                soNguoiThue=Integer.parseInt(quanLiHopDongModel.getSoNguoi());
                                edtSoNguoi.setText(quanLiHopDongModel.getSoNguoi()+" người");
                                tvThangMay.setText("Tiền thang máy "+"("+quanLiHopDongModel.getSoNguoi()+" người)");
                                tvTienRac.setText("Tiền rác "+"("+quanLiHopDongModel.getSoNguoi()+" người)");
                                getDichVu(edtTienMang,edtTienThangmay,edtTienRac,soNguoiThue);
                                emailKhachHang= quanLiHopDongModel.getEmailKhachHang();

                                return;
                            }
                        }
                    }
                });

    }
    private void getDichVu(EditText edtTienMang,EditText edtTienThangMay,
                           EditText edtTienRac,int soNguoiThue){
        db=FirebaseFirestore.getInstance();
        db.collection("DichVu")
                .document("vdYaZn0fm5owoxA1IvoA")
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot snapshot=task.getResult();
                    if(snapshot.exists()){
                        String strtienDien=snapshot.getString("tienDien");
                        String strtienNuoc=snapshot.getString("tienNuoc");
                        String strtienMang=snapshot.getString("tienMang");
                        String strtienThangMay=snapshot.getString("tienThangMay");
                        String strtienRac=snapshot.getString("tienRac");
                        edtTienMang.setText(strtienMang+"đ");
                        int tienThangMay=Integer.parseInt(strtienThangMay)*soNguoiThue;
                        int tienRac=Integer.parseInt(strtienRac)*soNguoiThue;
                        edtTienThangMay.setText(String.valueOf(tienThangMay)+"đ");
                        edtTienRac.setText(String.valueOf(tienRac)+"đ");
                        tienDienDichVu=strtienDien;
                        tienNuocDichVu=strtienNuoc;
                        Log.d(("zzzz"),tienDienDichVu+tienNuocDichVu);
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Lỗi lấy thông tin phòng", Toast.LENGTH_SHORT).show();
                });

    }
    private void hienthidailog(String idPhong) {
        // Tạo LayoutInflater để lấy view từ XML
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.item_xuat_hoa_don_them, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Tìm các EditText trong dialog
        EditText edtTenPhong = dialogView.findViewById(R.id.edt_phongHoaDon);
        EditText edtSoDien = dialogView.findViewById(R.id.edt_soDien_hoadon);
        EditText edtGiaPhong = dialogView.findViewById(R.id.edt_giaPhong_hoadon);

        EditText edtTienNuoc = dialogView.findViewById(R.id.edt_tienNuoc_hoadon);
        EditText edtSoNguoi = dialogView.findViewById(R.id.edt_soNguoi_hoadon);
        EditText edtTienMang= dialogView.findViewById(R.id.edtTienMang_hoaDon);
        EditText edtTienThangMay= dialogView.findViewById(R.id.edtTienThangMay_hoaDon);
        EditText edtTienRac= dialogView.findViewById(R.id.edtTienRac_hoaDon);
        TextView tvThangMay= dialogView.findViewById(R.id.tvThangMay);
        TextView tvTienRac= dialogView.findViewById(R.id.tvTienRacz);
        Button btnXuatHoaDon = dialogView.findViewById(R.id.btnXuatHoaDon);
        getInforPhong(idPhong,edtTenPhong,edtGiaPhong);
        getHopDong(idPhong,edtSoNguoi,edtTienMang,edtTienThangMay,edtTienRac,tvThangMay,tvTienRac);

        btnXuatHoaDon.setOnClickListener(view -> {
            String strSoDien=edtSoDien.getText().toString();
            String strSoNuoc=edtTienNuoc.getText().toString();

            if (strSoDien.isEmpty()||strSoNuoc.isEmpty()){
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }else{
                    String strTienThangMay=edtTienThangMay.getText().toString();
                    String strTienRac=edtTienRac.getText().toString();
                    String strTienMang=edtTienMang.getText().toString();

                    String editTienThangMay=strTienThangMay.substring(0,strTienThangMay.length()-1);
                    String editTienRac=strTienRac.substring(0,strTienRac.length()-1);
                    String editTienMang=strTienMang.substring(0,strTienMang.length()-1);

                    int tienMayThangInteger=Integer.parseInt(editTienThangMay);
                    int tienRacInteger=Integer.parseInt(editTienRac);
                    int tienMangInteger=Integer.parseInt(editTienMang);
                    int tienDienInteger=Integer.parseInt(tienDienDichVu)*Integer.parseInt(edtSoDien.getText().toString());
                    int tienNuocInteger=Integer.parseInt(tienNuocDichVu)*Integer.parseInt(edtTienNuoc.getText().toString());
                    int tongTien=tienMayThangInteger+tienRacInteger+tienMangInteger+tienDienInteger+tienNuocInteger;
                    String giaPhongThue=giaPhong.substring(0,giaPhong.length()-2);
                    int TongTienHoaDon=tongTien+Integer.parseInt(giaPhongThue);

                    themHoaDonDichVu(String.valueOf(tienDienInteger),String.valueOf(tienNuocInteger),editTienThangMay,editTienMang,editTienRac,edtSoDien.getText().toString(),edtTienNuoc.getText().toString(),
                            String.valueOf(tongTien),idPhong,String.valueOf(TongTienHoaDon));

                    Toast.makeText(context, "Thêm hoá đơn thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
            }
        });
        // Tạo và hiển thị AlertDialog
        // Tạo Dialog
        dialog.show();

    }
    private void getIdUser(String emailKhachHang,String idPhong,String idHoaDonDichVu,String soTienThanhToan){
        db=FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email",emailKhachHang)
                .get()
                .addOnSuccessListener(runnable -> {
                    for (DocumentSnapshot snapshot: runnable){
                        if (snapshot.getString("email").equals(emailKhachHang)){
                            themHoaDon(snapshot.getId(),idPhong,idHoaDonDichVu,soTienThanhToan);
                            return;
                        }
                    }
                });
    }
    private void themHoaDon(String idKhachHang,String idPhong,String idHoaDonDichVu,String soTienThanhToan){
        Calendar calendar=Calendar.getInstance();
        Date date=calendar.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat(("dd/MM/yyyy"));
        String ngayTao=sdf.format(date);
        HoaDonModels hoaDon=new HoaDonModels(idKhachHang,false,ngayTao);
        db=FirebaseFirestore.getInstance();
        db.collection("HoaDon")
                .add(hoaDon)
                .addOnSuccessListener(documentReference -> {
                    themHoaDonChiTiet(documentReference.getId(),idPhong,idHoaDonDichVu,soTienThanhToan);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Thêm hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                });
    }
    private void themHoaDonChiTiet(String idHoaDon,String idPhong,String idHoaDonDichVu,String soTienThanhToan){
        HoaDonChiTietModels hoaDonChiTiet=new HoaDonChiTietModels(idHoaDon,idPhong,idHoaDonDichVu,soTienThanhToan);
        db=FirebaseFirestore.getInstance();
        db.collection("HoaDonChiTiet")
                .add(hoaDonChiTiet)
                .addOnSuccessListener(documentReference -> {
                    Log.d("zzzz","hoadonchitiet thành công");
                }).addOnFailureListener(runnable -> {
                    Log.d("zzzz","hoadonchitiet thất bại");
                });
    }
    private void themHoaDonDichVu(String tienDien,String tienNuoc,String tienThangMay,String tienMang,String tienRac,String soDien,String soNuoc,
                                  String tongTienDichVu,String idPhong,String dichVuTOngTien){
        HoaDonDichVuModels hoaDonDichVu=new HoaDonDichVuModels("vdYaZn0fm5owoxA1IvoA",tienDien,tienNuoc,tienThangMay,tienMang,tienRac,soDien,soNuoc,tongTienDichVu);
        db=FirebaseFirestore.getInstance();
        db.collection("HoaDonDichVu")
                .add(hoaDonDichVu)
                .addOnSuccessListener(documentReference -> {
                    hoaDonDichVu.setIdHoaDonDichVu(documentReference.getId());
                    Log.d("zzzz","hoadonDichVu thành công"+hoaDonDichVu.getIdHoaDonDichVu());
                    idHdDichVu=hoaDonDichVu.getIdHoaDonDichVu();
                    getIdUser(emailKhachHang,idPhong,idHdDichVu,dichVuTOngTien);
                }).addOnFailureListener(runnable -> {
                    Log.d("zzzz","hoadonDichVu thất bại");
                });
    }
}
