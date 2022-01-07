package com.example.qldt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qldt.adapter.SinhVienAdapter;
import com.example.qldt.model.SinhVien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminQLSinhVienActivity extends AppCompatActivity {

    ListView list_sv;
    ArrayList<SinhVien> sinhVienArrayList;
    private SinhVienAdapter adapter;

    ImageButton QLSV_add_sv,QLSV_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlsinh_vien);

        QLSV_add_sv = findViewById(R.id.QLSV_add_sv);
        QLSV_back_button = findViewById(R.id.QLSV_back_button);

        list_sv = findViewById(R.id.list_sv);
        // tạo danh sách sinh viên

//        sinhVienArrayList.add(new SinhVien("","Mã số sinh viên","Họ tên","Nam/Nữ","Email sinh viên","dd/mm/yyyy","039xxxx","wwwwww",""));
//        sinhVienArrayList.add(new SinhVien("","Mã số sinh viên","Họ tên","Nam/Nữ","Email sinh viên","dd/mm/yyyy","039xxxx","wwwwww",""));
//        sinhVienArrayList.add(new SinhVien("","Mã số sinh viên","Họ tên","Nam/Nữ","Email sinh viên","dd/mm/yyyy","039xxxx","wwwwww",""));
            sinhVienArrayList = new ArrayList<>();
            GetData();
        // gọi custom adapter để gán cho listview
        //this là layout này, custom_listview_item là layout của từng sinh viên
        // sinhvien_array_list là list data sinh viên được trả về từ firebase
        adapter = new SinhVienAdapter(AdminQLSinhVienActivity.this,R.layout.listview_sv,sinhVienArrayList);
        // tạo adapter cho listview
        list_sv.setAdapter(adapter);





        QLSV_add_sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminQLSinhVienAddActivity.class);
                startActivity(intent);
            }
        });

        QLSV_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),AdminHomeActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    private void GetData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("SinhVien");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // cập nhật lại dữ liệu mới lên list_sv và xóa dữ liệu cũ
                adapter.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // chuyển đổi dữ liệu qua class SinhVien
                    SinhVien sinhVien = dataSnapshot.getValue(SinhVien.class);
                    // thêm sinh viên vào list_sv
                    assert sinhVien != null;
                    sinhVien.setMssv(dataSnapshot.getKey());
                    adapter.add(sinhVien);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail" + error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}