package com.example.qldt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qldt.adapter.GiangVienAdapter;
import com.example.qldt.model.GiangVien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminQLGiangVienActivity extends AppCompatActivity {

    ListView list_gv;
    ArrayList<GiangVien> giangVienArrayList;
    private GiangVienAdapter adapter;

    ImageButton QLGV_add_gv,QLGV_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlgiang_vien);

        QLGV_add_gv = findViewById(R.id.QLGV_add_gv);
        QLGV_back_button = findViewById(R.id.QLGV_back_button);

        list_gv = findViewById(R.id.list_gv);

        // tạo danh sách sinh viên
        giangVienArrayList = new ArrayList<>();
//        giangVienArrayList.add(new GiangVien("","giangvien@gmail.com","Giang văn Viên","Nam","11/11/2021","03922223452","Ha Noi",""));
        GetData();

        // gọi custom adapter để gán cho listview
        //this là layout này, custom_listview_item là layout của từng sinh viên
        // sinhvien_array_list là list data sinh viên được trả về từ firebase
        adapter = new GiangVienAdapter(AdminQLGiangVienActivity.this,R.layout.listview_gv,giangVienArrayList);
        // tạo adapter cho listview
        list_gv.setAdapter(adapter);

        QLGV_add_gv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminQLGiangVienAddActivity.class);
                startActivity(intent);
            }
        });

        QLGV_back_button.setOnClickListener(new View.OnClickListener() {
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
        DatabaseReference databaseReference = database.getReference().child("GiangVien");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // cập nhật lại dữ liệu mới lên list_sv và xóa dữ liệu cũ
                adapter.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // chuyển đổi dữ liệu qua class SinhVien
                    GiangVien giangVien = dataSnapshot.getValue(GiangVien.class);
                    // thêm sinh viên vào list_sv
                    assert giangVien != null;
                    giangVien.setMsnv(dataSnapshot.getKey());
                    adapter.add(giangVien);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail" + error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}