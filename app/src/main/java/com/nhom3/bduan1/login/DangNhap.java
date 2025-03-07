package com.nhom3.bduan1.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.appcompatactivity.AdminActivity;

import com.nhom3.bduan1.appcompatactivity.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DangNhap extends AppCompatActivity {
    final String TAG = "zzzzzzzzz";
    private EditText edtEmail, edtPass;
    private FirebaseAuth mAuth;
    public FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.edtemail_dn);
        edtPass = findViewById(R.id.edtPass_dn);
        TextView btnDangKy = findViewById(R.id.btnDangKy);
        Button btnDangNhap = findViewById(R.id.btnDangNhap);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        Drawable eyeOpen = getResources().getDrawable(R.drawable.matmo); // Icon mắt mở
        Drawable eyeClosed = getResources().getDrawable(R.drawable.mat); // Icon mắt đóng

        edtPass.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int drawableEndIndex = edtPass.getRight() - edtPass.getCompoundDrawables()[2].getBounds().width();
                if (event.getRawX() >= drawableEndIndex) {
                    if (edtPass.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edtPass.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeClosed, null);
                    } else {
                        edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edtPass.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeOpen, null);
                    }
                    edtPass.setSelection(edtPass.getText().length()); // Đặt con trỏ ở cuối
                    return true;
                }
            }
            return false;
        });
        // Sự kiện đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(DangNhap.this, "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(DangNhap.this, "Bạn chưa nhập pass", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userID = mAuth.getCurrentUser().getUid();
                            firestore.collection("users").document(userID).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                if (documentSnapshot.exists()) {
                                                    String role = documentSnapshot.getString("role");
                                                    Log.d(TAG, "Role: " + role);
                                                    if (role != null) {
                                                        switch (role) {
                                                            case "admin":
                                                                Toast.makeText(DangNhap.this, "Admin dang nhap", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(DangNhap.this, AdminActivity.class));
                                                                break;
                                                            case "user":
                                                                Intent intent=new Intent(DangNhap.this, UserActivity.class);
                                                                intent.putExtra("emailUser",email);
                                                                intent.putExtra("userId",userID);
                                                                Toast.makeText(DangNhap.this, "User dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                                                                startActivity(intent);
                                                                break;
                                                            default:
                                                                Toast.makeText(DangNhap.this, "Role không hợp lệ", Toast.LENGTH_SHORT).show();
                                                                break;
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(DangNhap.this, "Không thấy dữ liệu người dùng", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(DangNhap.this, "Lỗi khi lấy dữ liệu người dùng", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Sự kiện đăng ký
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhap.this, DangKy.class));
            }
        });
    }
}