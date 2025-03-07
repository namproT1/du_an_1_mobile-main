package com.nhom3.bduan1.login;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom3.bduan1.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoiMatKhau extends AppCompatActivity {

    private EditText edMatKhauCu, edMatKhauMoi, edMatKhauNhapLai;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        // Khởi tạo các View
        edMatKhauCu = findViewById(R.id.edMatKhauCu);
        edMatKhauMoi = findViewById(R.id.edMatKhauMoi);
        edMatKhauNhapLai = findViewById(R.id.edMatKhauNhapLai);
        Button btnLuu = findViewById(R.id.btnLuu);
        Button btnHuy = findViewById(R.id.btnHuy);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        btnLuu.setOnClickListener(v -> doiMatKhau());
        btnHuy.setOnClickListener(v -> finish()); // Đóng activity khi nhấn hủy

        // Drawable cho mắt mở và mắt đóng
        Drawable eyeOpen = getResources().getDrawable(R.drawable.matmo); // Icon mắt mở
        Drawable eyeClosed = getResources().getDrawable(R.drawable.mat); // Icon mắt đóng

        // Thêm OnTouchListener cho EditText để thay đổi mật khẩu
        View.OnTouchListener togglePasswordVisibility = (v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                EditText editText = (EditText) v;
                int drawableEndIndex = editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width();
                if (event.getRawX() >= drawableEndIndex) {
                    if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeClosed, null);
                    } else {
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeOpen, null);
                    }
                    editText.setSelection(editText.getText().length()); // Đặt con trỏ ở cuối
                    return true;
                }
            }
            return false;
        };

        // Gán OnTouchListener cho từng trường mật khẩu
        edMatKhauCu.setOnTouchListener(togglePasswordVisibility);
        edMatKhauMoi.setOnTouchListener(togglePasswordVisibility);
        edMatKhauNhapLai.setOnTouchListener(togglePasswordVisibility);
    }

    private void doiMatKhau() {
        String matKhauCu = edMatKhauCu.getText().toString().trim();
        String matKhauMoi = edMatKhauMoi.getText().toString().trim();
        String matKhauNhapLai = edMatKhauNhapLai.getText().toString().trim();

        // Kiểm tra các trường nhập liệu
        if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || matKhauNhapLai.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới có khớp không
        if (!matKhauMoi.equals(matKhauNhapLai)) {
            Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy người dùng hiện tại
        FirebaseUser user = mAuth.getCurrentUser();

        // Kiểm tra nếu người dùng đã đăng nhập
        if (user != null) {
            // Đăng nhập lại với mật khẩu cũ
            reauthenticateUser(matKhauCu, matKhauMoi);
        }
    }

    private void reauthenticateUser(String matKhauCu, String matKhauMoi) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), matKhauCu);

            // Tiến hành xác thực lại người dùng với mật khẩu cũ
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Sau khi xác thực thành công, tiến hành thay đổi mật khẩu
                    user.updatePassword(matKhauMoi).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(DoiMatKhau.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            finish(); // Đóng activity sau khi đổi mật khẩu thành công
                        } else {
                            Toast.makeText(DoiMatKhau.this, "Lỗi khi đổi mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(DoiMatKhau.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}