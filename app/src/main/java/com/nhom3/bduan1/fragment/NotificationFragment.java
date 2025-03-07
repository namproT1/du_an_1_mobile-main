package com.nhom3.bduan1.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.models.ThongBaoModels;
import com.nhom3.bduan1.adapter.NotificationAdapter_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NotificationFragment extends Fragment {

    private NotificationAdapter_user adapter;
    private List<ThongBaoModels> listNotifi;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance(String param1, String param2) {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView=view.findViewById(R.id.rcvNotification_user);
        db=FirebaseFirestore.getInstance();
        listNotifi=new ArrayList<>();
        getNotification();
        return view;
    }
    private void getNotification(){
        db.collection("ThongBao").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document:task.getResult()){
                        ThongBaoModels notifi=document.toObject(ThongBaoModels.class);
                        listNotifi.add(notifi);
                        Log.d("notifi",listNotifi.size()+"");
                    }
                    adapter=new NotificationAdapter_user(listNotifi,getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    Log.d("notifi","fail"+task.getException());
                }
            }
        });
    }
}