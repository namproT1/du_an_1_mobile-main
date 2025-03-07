package com.nhom3.bduan1.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.models.DichVuModels;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DichVu_AdminFragment extends Fragment {

    private TextView tvTienDien,tvTienNuoc,tvTienMang,tvTienThangMay,tvTienRac;
    private EditText edtTienDien,edtTienNuoc,edtTienMang,edtTienThangMay,edtTienRac;
    private ImageView imgQuanLiDichVuAdmin;
    public DichVu_AdminFragment() {
        // Required empty public constructor
    }


    public static DichVu_AdminFragment newInstance() {
        return new DichVu_AdminFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dich_vu__admin, container, false);
        imgQuanLiDichVuAdmin=view.findViewById(R.id.imgQuanLiDichVuAdmin);
        tvTienDien=view.findViewById(R.id.tvTienDien);
        tvTienNuoc=view.findViewById(R.id.tvTienNuoc);
        tvTienMang=view.findViewById(R.id.tvTienMang);
        tvTienThangMay=view.findViewById(R.id.tvTienThangMay);
        tvTienRac=view.findViewById(R.id.tvTienRac);
        edtTienDien=view.findViewById(R.id.edt_tienDien_hoadon);
        edtTienNuoc=view.findViewById(R.id.edtTienNuoc);
        edtTienMang=view.findViewById(R.id.edtTienMang);
        edtTienThangMay=view.findViewById(R.id.edtTienThangMay);
        Button btnCapNhatGiaDichVu = view.findViewById(R.id.btnCapNhatGiaDichVu);
        edtTienRac=view.findViewById(R.id.edtTienRac);
        getDichVu(edtTienDien,edtTienNuoc,edtTienMang,edtTienThangMay,edtTienRac);
        imgQuanLiDichVuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        btnCapNhatGiaDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String tienDien=edtTienDien.getText().toString();
                    String tienNuoc=edtTienNuoc.getText().toString();
                    String tienMang=edtTienMang.getText().toString();
                    String tienThangMay=edtTienThangMay.getText().toString();
                    String tienRac=edtTienRac.getText().toString();
                    if(tienDien.isEmpty()||tienNuoc.isEmpty()||tienMang.isEmpty()||tienThangMay.isEmpty()||tienRac.isEmpty()){
                        Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else{
                        updateGiaDichVu(tienDien,tienNuoc,tienMang,tienThangMay,tienRac);
                        getDichVu(edtTienDien,edtTienNuoc,edtTienMang,edtTienThangMay,edtTienRac);
                    }
            }
        });
        return view;
    }
    private void getDichVu(EditText edtTienDien, EditText edtTienNuoc, EditText edtTienMang, EditText edtTienThangMay, EditText edtTienRac){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("DichVu").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
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

                    tvTienDien.setText("Tiền điện("+dichVu.getTienDien()+"/kwh)");
                    tvTienNuoc.setText("Tiền nước("+dichVu.getTienNuoc()+"/khối)");
                    tvTienMang.setText("Tiền mạng("+dichVu.getTienMang()+"/phòng/tháng)");
                    tvTienThangMay.setText("Tiền thang máy("+dichVu.getTienThangMay()+"/người/tháng)");
                    tvTienRac.setText("Tiền rác("+dichVu.getTienRac()+"/người/tháng)");

                }
            }
        });
    }
    private void updateGiaDichVu(String tienDien, String tienNuoc, String tienMang, String tienThangMay, String tienRac) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DichVuModels dichVu=new DichVuModels();
        dichVu.setTienDien(tienDien);
        dichVu.setTienNuoc(tienNuoc);
        dichVu.setTienMang(tienMang);
        dichVu.setTienThangMay(tienThangMay);
        dichVu.setTienRac(tienRac);
        dichVu.setIdDichVu("vdYaZn0fm5owoxA1IvoA");
        db.collection("DichVu")
                .document(dichVu.getIdDichVu())
                .set(dichVu)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Cập nhật giá dịch vụ thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Cập nhật giá dịch vụ thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}














