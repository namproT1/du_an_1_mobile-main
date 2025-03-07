package com.nhom3.bduan1.appcompatactivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.fragment.ThongBaoAdminFragment;

import com.nhom3.bduan1.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);  // Đảm bảo bạn có layout activity_admin.xml

        // Chỉnh sửa padding của BottomNavigationView để tránh bị che bởi thanh trạng thái
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottom_Admin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);  // Chỉ thêm padding cho phần bottom
            return insets;
        });

        initUi();

        // Lấy dữ liệu từ Intent, có thể là thông tin của quản trị viên
        Intent intent = getIntent();
        String emailAdmin = intent.getStringExtra("emailAdmin");
        String adminId = intent.getStringExtra("adminId");

        // Thêm fragment mặc định (DashboardFragment)
        addFragment(new HomeFragment());

        // Xử lý sự kiện nhấn vào BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item1) {
                    Drawable icon1 = getResources().getDrawable(R.drawable.icon_home_2, null);
                    icon1.setBounds(100, 100, 100, 100);  // Tạo kích thước mới cho icon (width, height)
                    item.setIcon(icon1);;
                    addFragment(new HomeFragment());
                } else if (id == R.id.item2) {
                    Drawable icon2 = getResources().getDrawable(R.drawable.icon_notification, null);
                    icon2.setBounds(100, 100, 100, 100);  // Tạo kích thước mới cho icon (width, height)
                    item.setIcon(icon2);;
                    addFragment(new ThongBaoAdminFragment());
                } else {
                    Drawable icon1 = getResources().getDrawable(R.drawable.icon_home_2, null);
                    icon1.setBounds(100, 100, 100, 100);  // Tạo kích thước mới cho icon (width, height)
                    item.setIcon(icon1);;
                    addFragment(new HomeFragment());
                }
                return true;
            }
        });
    }

    // Khởi tạo các thành phần UI
    private void initUi() {
        bottomNavigationView = findViewById(R.id.bottom_Admin);
        findViewById(R.id.fragmentContainer);
    }

    // Thêm Fragment vào FrameLayout
    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
