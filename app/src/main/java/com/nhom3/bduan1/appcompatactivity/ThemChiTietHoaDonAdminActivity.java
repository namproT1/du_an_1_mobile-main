package com.nhom3.bduan1.appcompatactivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nhom3.bduan1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ThemChiTietHoaDonAdminActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView tvTenPhong, tvSoDien, tvGiaPhong, tvTienDien, tvTienNuoc, tvTienDichVu, tvSoNguoi, tvTongTien;
    private Button btnGuiHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_chi_tiet_hoa_don);

        db = FirebaseFirestore.getInstance();
        tvTenPhong = findViewById(R.id.tv_phong_cthd);
        tvTienDien = findViewById(R.id.tv_tienDien_cthd);
        tvTienNuoc = findViewById(R.id.tv_tienNuoc_cthd);
        tvTongTien = findViewById(R.id.tv_tongTien_cthd);
        tvGiaPhong = findViewById(R.id.tv_tienPhong_cthd);
        tvSoNguoi = findViewById(R.id.tv_soNguoi_cthd);
        TextView tvNgayXuatHoaDon = findViewById(R.id.tv_ngayXuatHoaDon_cthd);
        TextView tvTinhTuNgay = findViewById(R.id.tv_tinhTuNgay_cthd);
        TextView tvDenNgay = findViewById(R.id.tv_denNgay_cthd);


        SimpleDateFormat time = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date = time.format(new Date());

        tvNgayXuatHoaDon.setText(date);
        tvTinhTuNgay.setText(date);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Cộng thêm 30 ngày
        Date datePlus30 = calendar.getTime();
        String date30DaysLater = time.format(datePlus30);

        // Gán ngày kết thúc (cộng 30 ngày) vào TextView
        tvDenNgay.setText(date30DaysLater);

        // Nhận dữ liệu từ Intent
        Bundle extras = getIntent().getExtras();
        boolean isUpdate = extras != null && extras.getBoolean("isUpdate", false);

        if (isUpdate) {
            // Cập nhật thông tin hóa đơn
            String tenPhong = extras.getString("tenPhong");
            String soDien = extras.getString("soDien");
            String giaPhong = extras.getString("giaPhong");
            String tienDien = extras.getString("tienDien");
            String tienNuoc = extras.getString("tienNuoc");
            String tienDichVu = extras.getString("tienDichVu");
            String soNguoi = extras.getString("soNguoi");
            double tongTien = extras.getDouble("tongTien");

            // Hiển thị thông tin hóa đơn
            tvTenPhong.setText(tenPhong);
            tvSoDien.setText(soDien);
            tvGiaPhong.setText(giaPhong);
            tvTienDien.setText(tienDien);
            tvTienNuoc.setText(tienNuoc);
            tvTienDichVu.setText(tienDichVu);
            tvSoNguoi.setText(soNguoi);
            tvTongTien.setText(String.valueOf(tongTien));
        } else {
            // Hiển thị thông tin hóa đơn mới từ Intent
            String tenPhong = extras.getString("tenPhong");
            String soDien = extras.getString("soDien");
            String giaPhong = extras.getString("giaPhong");
            String tienDien = extras.getString("tienDien");
            String tienNuoc = extras.getString("tienNuoc");
            String tienDichVu = extras.getString("tienDichVu");
            String soNguoi = extras.getString("soNguoi");
            double tongTien = extras.getDouble("tongTien");

            // Hiển thị thông tin hóa đơn
            tvTenPhong.setText(tenPhong);
            tvSoDien.setText(soDien);
            tvGiaPhong.setText(giaPhong);
            tvTienDien.setText(tienDien);
            tvTienNuoc.setText(tienNuoc);
            tvTienDichVu.setText(tienDichVu);
            tvSoNguoi.setText(soNguoi);
            tvTongTien.setText(String.valueOf(tongTien));
        }

        btnGuiHoaDon.setOnClickListener(v -> {
            // Gửi hóa đơn vào Firestore
            sendInvoice();
        });
    }


    private void sendInvoice() {
        // Lấy thông tin hóa đơn từ các TextView
        String tenPhong = tvTenPhong.getText().toString();
        String soDien = tvSoDien.getText().toString();
        String giaPhong = tvGiaPhong.getText().toString();
        String tienDien = tvTienDien.getText().toString();
        String tienNuoc = tvTienNuoc.getText().toString();
        String tienDichVu = tvTienDichVu.getText().toString();
        String soNguoi = tvSoNguoi.getText().toString();
        double tongTien = Double.parseDouble(tvTongTien.getText().toString());

        // Tạo một DocumentReference cho Firestore
        DocumentReference invoiceRef = db.collection("hoadon").document();  // Mỗi hóa đơn sẽ có một document riêng biệt

        // Tạo một Map để lưu trữ dữ liệu hóa đơn
        Map<String, Object> invoiceData = new HashMap<>();
        invoiceData.put("tenPhong", tenPhong);
        invoiceData.put("soDien", soDien);
        invoiceData.put("giaPhong", giaPhong);
        invoiceData.put("tienDien", tienDien);
        invoiceData.put("tienNuoc", tienNuoc);
        invoiceData.put("tienDichVu", tienDichVu);
        invoiceData.put("soNguoi", soNguoi);
        invoiceData.put("tongTien", tongTien);

        // Gửi dữ liệu vào Firestore
        invoiceRef.set(invoiceData)
                .addOnSuccessListener(aVoid -> {
                    // Hiển thị thông báo thành công
                    Toast.makeText(ThemChiTietHoaDonAdminActivity.this, "Hóa đơn đã được gửi thành công", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Hiển thị thông báo lỗi
                    Toast.makeText(ThemChiTietHoaDonAdminActivity.this, "Lỗi khi gửi hóa đơn: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

}
