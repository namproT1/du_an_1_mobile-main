package com.nhom3.bduan1.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom3.bduan1.appcompatactivity.SupportActivity;
import com.nhom3.bduan1.login.DangNhap;
import com.nhom3.bduan1.login.DoiMatKhau;
import com.nhom3.bduan1.R;
import com.nhom3.bduan1.appcompatactivity.UpdateUserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    TextView tvUser, tvPhoneNumber;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayout linearUpdateInfor = view.findViewById(R.id.linearUpdateInfor);
        LinearLayout linearUpdatePassword = view.findViewById(R.id.linearUpdatePassword);
        LinearLayout linearContract = view.findViewById(R.id.linearContract);
        LinearLayout linerSupport = view.findViewById(R.id.linerSupport);
        LinearLayout linearLogout = view.findViewById(R.id.linearLogout);

        tvUser = view.findViewById(R.id.tvUser);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Lấy thông tin người dùng
        loadUserData();

        linearUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext().getApplicationContext(), DoiMatKhau.class));
            }
        });

        linearUpdateInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UpdateUserActivity.class));
            }
        });
        linerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SupportActivity.class));
            }
        });

        linearLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getContext(), DangNhap.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        return view;
    }

    private void loadUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String username = document.getString("name");
                                String phoneNumber = document.getString("phoneNumber");

                                tvUser.setText(username != null ? username : "No Username");
                                tvPhoneNumber.setText(phoneNumber != null ? phoneNumber : "No Phone Number");
                            } else {
                                Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Error getting user data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
