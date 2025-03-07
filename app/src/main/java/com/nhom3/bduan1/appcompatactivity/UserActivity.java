package com.nhom3.bduan1.appcompatactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nhom3.bduan1.R;
import com.nhom3.bduan1.fragment.BillFragment;
import com.nhom3.bduan1.fragment.HomeFragment_user;
import com.nhom3.bduan1.fragment.NotificationFragment;
import com.nhom3.bduan1.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static String emailUser;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottom_user), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);  // Chỉ thêm padding cho phần bottom
            return insets;

        });
        initUi();
        Intent intent=getIntent();
        emailUser=intent.getStringExtra("emailUser");
        userId=intent.getStringExtra("userId");
        addFragment(new HomeFragment_user());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.item_home){
                    addFragment(new HomeFragment_user());
                }else if(id==R.id.item_notification){
                    addFragment(new NotificationFragment());
                } else if (id == R.id.item_bill) {
                    addFragment(new BillFragment());
                }else if(id==R.id.item_profile){
                    addFragment(new ProfileFragment());
                }
                return true;
            }
        });

    }
    private void initUi(){
        bottomNavigationView = findViewById(R.id.bottom_user);
        FrameLayout frameLayout = findViewById(R.id.frame_user);


    }
    private void addFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_user, fragment);
        fragmentTransaction.commit();
    }
}