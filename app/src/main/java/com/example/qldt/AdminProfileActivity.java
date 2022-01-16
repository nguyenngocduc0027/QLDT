package com.example.qldt;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminProfileActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);



        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));

            }
        });
        ImageButton edit_profile = findViewById(R.id.edit_button);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  go_edit =  new Intent(v.getContext(), AdminEditProfileActivity.class);
                startActivity(go_edit);
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        CircleImageView show_avatar = findViewById(R.id.admin_avatar_profile);
        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).child("image")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String link = snapshot.getValue(String.class);
                        Glide.with(AdminProfileActivity.this).load(link).fitCenter().into(show_avatar);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AdminProfileActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
                    }
                });

        TextView admin_name = findViewById(R.id.admin_name_profile);
        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).child("name")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name_admin = snapshot.getValue(String.class);
                        admin_name.setText(name_admin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


        TextView admin_email = findViewById(R.id.admin_email_profile);
        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).child("email")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String email_admin = snapshot.getValue(String.class);
                        admin_email.setText(email_admin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
        TextView admin_dob = findViewById(R.id.admin_dob_profile);
        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).child("dob")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String dob_admin = snapshot.getValue(String.class);
                        admin_dob.setText(dob_admin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

        TextView admin_phone = findViewById(R.id.admin_phone_profile);
        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).child("phone")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String phone_admin = snapshot.getValue(String.class);
                        admin_phone.setText(phone_admin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

        TextView admin_address = findViewById(R.id.admin_address_profile);
        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).child("address")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String address_admin = snapshot.getValue(String.class);
                        admin_address.setText(address_admin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

        TextView admin_gt = findViewById(R.id.admin_gt_profile);
        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).child("gt")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String gt_admin = snapshot.getValue(String.class);
                        admin_gt.setText(gt_admin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
    }
}