package com.nhom3.bduan1.appcompatactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.fragment.QuanLyHopDongFragment;
import com.nhom3.bduan1.fragment.QuanLyPhongTroFragment;
import com.nhom3.bduan1.models.DichVuModels;
import com.nhom3.bduan1.models.QuanLiHopDongModels;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class QuanLiHopDongChiTietActivity extends AppCompatActivity {

    private EditText edtTenKhachHangChiTiet, edtEmail_KhachHangChiTiet,edtTienCocChiTiet,
            edtCCCDChiTiet, edtSoNguoiChiTiet, edtTenPhongChiTiet, edtNgayBatDauChiTiet,edtGiaPhong,
            edtNgayKetThucChiTiet, edt_tienDien_hoadon, edtTienNuoc, edtTienMang, edtTienThangMay, edtTienRac;
    private Button btnCapNhatHopDong;
    private ImageView imgBackHopDongChiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_li_hop_dong_chi_tiet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hopdongchitiet), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUi();
        imgBackHopDongChiTiet.setOnClickListener(v -> finish());
        Intent intent=getIntent();
        String idHopDong=intent.getStringExtra("id");
        edtTenKhachHangChiTiet.setText(intent.getStringExtra("tenKhachHang"));
        edtEmail_KhachHangChiTiet.setText(intent.getStringExtra("emailKhachHang"));
        edtCCCDChiTiet.setText(intent.getStringExtra("cccd"));
        edtSoNguoiChiTiet.setText(intent.getStringExtra("soNguoi"));
        edtTenPhongChiTiet.setText(intent.getStringExtra("tenPhong"));
        edtNgayBatDauChiTiet.setText(intent.getStringExtra("ngayBatDau"));
        edtNgayKetThucChiTiet.setText(intent.getStringExtra("ngayKetThuc"));
        edtTienCocChiTiet.setText(intent.getStringExtra("tienCoc"));
        String idPhong=intent.getStringExtra("idPhong");
        getPhongTro(idPhong);
        edtNgayBatDauChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuanLyPhongTroFragment.showDatePickerDialog(edtNgayBatDauChiTiet);
            }
        });
        edtNgayKetThucChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuanLyPhongTroFragment.showDatePickerDialog(edtNgayKetThucChiTiet);
            }
         });
        getDichVu(edt_tienDien_hoadon,edtTienNuoc,edtTienMang,edtTienThangMay,edtTienRac);
        btnCapNhatHopDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenKhachHang=edtTenKhachHangChiTiet.getText().toString();
                String emailKhachHang=edtEmail_KhachHangChiTiet.getText().toString();
                String cccd=edtCCCDChiTiet.getText().toString();
                String soNguoi=edtSoNguoiChiTiet.getText().toString();
                String tienCoc=edtTienCocChiTiet.getText().toString();
                String ngayBatDauf=edtNgayBatDauChiTiet.getText().toString();
                String ngayKetThuc=edtNgayKetThucChiTiet.getText().toString();
                String tienDien=edt_tienDien_hoadon.getText().toString();
                String tienNuoc=edtTienNuoc.getText().toString();
                String tienMang=edtTienMang.getText().toString();
                String tienThangMay=edtTienThangMay.getText().toString();
                String tienRac=edtTienRac.getText().toString();
                if (tenKhachHang.isEmpty()||emailKhachHang.isEmpty()||cccd.isEmpty()||soNguoi.isEmpty()
                        ||ngayBatDauf.isEmpty()||ngayKetThuc.isEmpty()||tienDien.isEmpty()||tienNuoc.isEmpty()
                        ||tienMang.isEmpty()||tienThangMay.isEmpty()||tienRac.isEmpty()){
                    Toast.makeText(QuanLiHopDongChiTietActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if (QuanLyHopDongFragment.isEndDateAfterStartDate(edtNgayBatDauChiTiet.getText().toString(),
                            edtNgayKetThucChiTiet.getText().toString())){
                        Toast.makeText(QuanLiHopDongChiTietActivity.this, "Ngày kết thúc phải sau ngày bắt đầu", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    updateHopDong(idHopDong,tenKhachHang,emailKhachHang,cccd,soNguoi,ngayBatDauf,ngayKetThuc,tienCoc);
                    finish();
                }
            }
        });
    }
    private void updateHopDong(String idHopDong,String tenKhachHang,String emailKhachHang,String cccd,String soNguoi,String ngayBatDau,String ngayKetThuc,String tienCoc){
        QuanLiHopDongModels quanLiHopDongModel=new QuanLiHopDongModels(tenKhachHang,emailKhachHang,cccd,soNguoi,ngayBatDau,ngayKetThuc,tienCoc);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HopDong")
                .document(idHopDong)
                .set(quanLiHopDongModel)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(QuanLiHopDongChiTietActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(QuanLiHopDongChiTietActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                });
    }
    private void getPhongTro(String idPhong) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PhongTro").document(idPhong).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    Double giaPhong = task.getResult().getDouble("tienPhong");
                    edtGiaPhong.setText(String.valueOf(giaPhong));
                    Log.d("giaPhong",giaPhong+"đâsđsa");
                }
            }
        });
    }
    private void getDichVu(EditText edtTienDien, EditText edtTienNuoc, EditText edtTienMang, EditText edtTienThangMay, EditText edtTienRac){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("DichVu").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DichVuModels dichVu = null;
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    dichVu = documentSnapshot.toObject(DichVuModels.class);
                }
                edtTienDien.setText(dichVu.getTienDien());
                edtTienNuoc.setText(dichVu.getTienNuoc());
                edtTienMang.setText(dichVu.getTienMang());
                edtTienThangMay.setText(dichVu.getTienThangMay());
                edtTienRac.setText(dichVu.getTienRac());

        }
    });
    }
    private void initUi(){
        edtTenKhachHangChiTiet = findViewById(R.id.edtTenKhachHangChiTiet);
        edtEmail_KhachHangChiTiet = findViewById(R.id.edtEmail_KhachHangChiTiet);
        edtCCCDChiTiet = findViewById(R.id.edtCCCDChiTiet);
        edtSoNguoiChiTiet = findViewById(R.id.edtSoNguoiChiTiet);
        edtTenPhongChiTiet = findViewById(R.id.edtTenPhongChiTiet);
        edtNgayBatDauChiTiet = findViewById(R.id.edtNgayBatDauChiTiet);
        edtNgayKetThucChiTiet = findViewById(R.id.edtNgayKetThucChiTiet);
        edt_tienDien_hoadon = findViewById(R.id.edt_tienDien_hoadon);
        edtTienThangMay = findViewById(R.id.edtTienThangMay);
        edtTienRac = findViewById(R.id.edtTienRac);
        edtTienNuoc = findViewById(R.id.edtTienNuoc);
        edtTienMang = findViewById(R.id.edtTienMang);
        btnCapNhatHopDong=findViewById(R.id.btnCapNhatHopDong);
        imgBackHopDongChiTiet=findViewById(R.id.imgBackHopDongChiTiet);
        edtGiaPhong=findViewById(R.id.edtGiaPhongChiTiet);
        edtTienCocChiTiet=findViewById(R.id.edtTienCocChiTiet);
    }
}