package com.nhom3.bduan1.appcompatactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.adapter.HoaDonChiTietTungPhongAdapter;
import com.nhom3.bduan1.models.HoaDonChiTietModels;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HoaDonChiTietTungPhongActivity extends AppCompatActivity {

    private ImageView imgBackHoaDonChiTietTungPhong;
    private TextView tvTenTungPhong;
    private RecyclerView rcvHoaDonChiTietTungPhong;
    private HoaDonChiTietTungPhongAdapter adapter;
    private List<HoaDonChiTietModels> hoaDonChiTietList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hoa_don_chi_tiet_tung_phong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUi();
        hoaDonChiTietList=new ArrayList<>();
        imgBackHoaDonChiTietTungPhong.setOnClickListener(v->finish());
        Intent intent=getIntent();
        String tenPhong=intent.getStringExtra("tenPhong");
        String idPhong=intent.getStringExtra("maPhong");
        tvTenTungPhong.setText("Quản lí hoá đơn phòng: "+tenPhong);
        getHoaDonChiTietTungPhong(idPhong);

    }
    private void getHoaDonChiTietTungPhong(String idPhong){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HoaDonChiTiet").whereEqualTo("idPhong",idPhong)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                        HoaDonChiTietModels hoaDonChiTiet=documentSnapshot.toObject(HoaDonChiTietModels.class);
                        hoaDonChiTietList.add(hoaDonChiTiet);
                    }
                    Collections.reverse(hoaDonChiTietList);
                    adapter=new HoaDonChiTietTungPhongAdapter(hoaDonChiTietList,this);
                    adapter.notifyDataSetChanged();
                    rcvHoaDonChiTietTungPhong.setLayoutManager(new LinearLayoutManager(this));
                    rcvHoaDonChiTietTungPhong.setAdapter(adapter);
                }).addOnFailureListener(e -> {
                });
    }
    private void initUi(){
        imgBackHoaDonChiTietTungPhong=findViewById(R.id.imgbackHoaDonChiTietTungPhong);
        tvTenTungPhong=findViewById(R.id.tvTenTungPhong);
        rcvHoaDonChiTietTungPhong=findViewById(R.id.rcvHoaDonChiTietTungPhong);
    }
}