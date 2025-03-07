package com.nhom3.bduan1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.models.HoaDonModels;
import com.nhom3.bduan1.appcompatactivity.UserActivity;
import com.nhom3.bduan1.adapter.BillDetailUserAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class BillFragment extends Fragment {

    private BillDetailUserAdapter adpater;
    private List<HoaDonModels> hoaDonList;
    public BillFragment() {
        // Required empty public constructor
    }


    public static BillFragment newInstance() {
        return new BillFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bill, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcvBill);
        hoaDonList=new ArrayList<>();
        adpater=new BillDetailUserAdapter(hoaDonList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adpater);
        getHoaDonByUserID(UserActivity.userId);

        return view;
    }
    private void getHoaDonByUserID(String idKhachHang){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HoaDon")
                .whereEqualTo("idKhachHang",idKhachHang)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        HoaDonModels hoaDon=documentSnapshot.toObject(HoaDonModels.class);
                        hoaDon.setIdHoaDon(documentSnapshot.getId());
                        hoaDonList.add(hoaDon);
                    }
                    adpater.notifyDataSetChanged();
                });
    }
}