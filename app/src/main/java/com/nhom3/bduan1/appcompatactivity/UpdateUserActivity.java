package com.nhom3.bduan1.appcompatactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nhom3.bduan1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

public class UpdateUserActivity extends AppCompatActivity {

    private Button btnUpdate;
    private TextInputLayout textInputName, textInputPhoneNumber, textInputBirth;
    private TextInputEditText edtName, edtPhoneNumber, edtBirthDate;
    private ImageView imgBackUpdateUser;
    private RadioButton rdMale;
    private FirebaseFirestore db; // Firestore instance
    private FirebaseAuth mAuth; // Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUi();

        // Initialize Firestore and Firebase Auth
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        rdMale.setChecked(true);

        btnUpdate.setOnClickListener(v -> {
            if (validateInputs()) {
                // Nếu dữ liệu hợp lệ, tiến hành cập nhật
                updateUserData();
            }
        });

        imgBackUpdateUser.setOnClickListener(v -> finish());
    }

    private void initUi() {
        btnUpdate = findViewById(R.id.btnUpdate_user);
        textInputName = findViewById(R.id.textInputName);
        textInputPhoneNumber = findViewById(R.id.textInputPhoneNumber);
        textInputBirth = findViewById(R.id.textInputBirth);
        edtName = findViewById(R.id.edtName_update);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber_update);
        edtBirthDate = findViewById(R.id.edtBirth_update);
        imgBackUpdateUser = findViewById(R.id.imgBackUpdateUser);
        rdMale = findViewById(R.id.radioMale);
        RadioButton rdFemale = findViewById(R.id.radioFemale);
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (TextUtils.isEmpty(edtName.getText())) {
            textInputName.setError("Vui lòng nhập tên");
            isValid = false;
        } else {
            textInputName.setError(null); // Xóa lỗi nếu có
        }

        if (TextUtils.isEmpty(edtPhoneNumber.getText())) {
            textInputPhoneNumber.setError("Vui lòng nhập số điện thoại");
            isValid = false;
        } else {
            textInputPhoneNumber.setError(null);
        }

        if (TextUtils.isEmpty(edtBirthDate.getText())) {
            textInputBirth.setError("Vui lòng nhập ngày sinh");
            isValid = false;
        } else {
            textInputBirth.setError(null);
        }

        return isValid;
    }

    private void updateUserData() {
        // Lấy thông tin từ các trường nhập liệu
        String name = edtName.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String birthDate = edtBirthDate.getText().toString().trim();
        String gender = rdMale.isChecked() ? "Nam" : "Nữ";

        // Lấy ID người dùng hiện tại từ Firebase Auth
        String userId = mAuth.getCurrentUser().getUid();

        // Cập nhật dữ liệu vào Firestore
        db.collection("users")
                .document(userId)
                .update(
                        "name", name,
                        "phoneNumber", phoneNumber,
                        "birth", birthDate,
                        "gender", gender
                )
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng Activity sau khi cập nhật thành công
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
