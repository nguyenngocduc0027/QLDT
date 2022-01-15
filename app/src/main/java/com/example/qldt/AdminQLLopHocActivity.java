package com.example.qldt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qldt.adapter.LopHocAdapter;
import com.example.qldt.model.LopHoc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminQLLopHocActivity extends AppCompatActivity {

    ListView list_lh;
    ArrayList<LopHoc> lopHocArrayList;
    LopHocAdapter lopHocAdapter;

    ImageButton QLLH_add_lh,QLLH_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qllop_hoc);

        list_lh = findViewById(R.id.list_lh);
        lopHocArrayList = new ArrayList<>();
        getdulieu();

        lopHocAdapter = new LopHocAdapter(AdminQLLopHocActivity.this,R.layout.listview_lh,lopHocArrayList);
        list_lh.setAdapter(lopHocAdapter);


        QLLH_add_lh = findViewById(R.id.QLLH_add_lh);
        QLLH_add_lh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminQLLopHocAddActivity.class));
            }
        });
        QLLH_back_button = findViewById(R.id.QLLH_back_button);
        QLLH_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
                finish();
            }
        });
    }

    private void getdulieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("LopHoc");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // cập nhật lại dữ liệu mới lên list_sv và xóa dữ liệu cũ
                lopHocAdapter.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // chuyển đổi dữ liệu qua class SinhVien
                    LopHoc lopHoc = dataSnapshot.getValue(LopHoc.class);
                    // thêm sinh viên vào list_sv
                    assert lopHoc != null;
                    lopHoc.setMalh(dataSnapshot.getKey());
                    lopHocAdapter.add(lopHoc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail" + error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}