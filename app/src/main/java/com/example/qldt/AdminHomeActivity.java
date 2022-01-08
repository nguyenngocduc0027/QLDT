package com.example.qldt;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminHomeActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        CircleImageView show_avatar = findViewById(R.id.admin_avatar_show);
        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).child("image")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String link = snapshot.getValue(String.class);
                        Glide.with(AdminHomeActivity.this).load(link).override(50,50).fitCenter().into(show_avatar);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AdminHomeActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
                    }
                });


        ImageView logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent log_out =  new Intent(v.getContext(),AdminLoginActivity.class);
                startActivity(log_out);
            }
        });

        Button btn_hocphan = findViewById(R.id.btn_hocphan);
        btn_hocphan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hp = new Intent(AdminHomeActivity.this, AdminQLHocPhanActivity.class);
                startActivity(hp);
            }
        });

        Button btn_giangvien = findViewById(R.id.btn_giangvien);
        btn_giangvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gv = new Intent(AdminHomeActivity.this,AdminQLGiangVienActivity.class);
                startActivity(gv);
            }
        });

        Button btn_sinhvien = findViewById(R.id.btn_sinhvien);
        btn_sinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminQLSinhVienActivity.class);
                startActivity(intent);
            }
        });

        Button btn_profile = findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminProfileActivity.class);
                startActivity(intent);
            }
        });



        TextView admin_name_show = findViewById(R.id.admin_name_show);
        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).child("name")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name_admin = snapshot.getValue(String.class);
                        admin_name_show.setText(name_admin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


    }
}