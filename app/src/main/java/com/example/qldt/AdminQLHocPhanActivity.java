package com.example.qldt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qldt.adapter.HocPhanAdapter;
import com.example.qldt.model.HocPhan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminQLHocPhanActivity extends AppCompatActivity {

    ListView list_hp;
    ArrayList<HocPhan> hocPhanArrayList;
    private HocPhanAdapter adapter;

    ImageButton QLHP_add_hp,QLHP_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlhoc_phan);

        list_hp = findViewById(R.id.list_hp);
        QLHP_add_hp = findViewById(R.id.QLHP_add_hp);
        QLHP_back_button = findViewById(R.id.QLHP_back_button);


        hocPhanArrayList = new ArrayList<>();
//        hocPhanArrayList.add(new HocPhan("ET1111","Lap trinh","2","bla bla ba be sa sd ced asfdgv h ds wef hgdf bs"));

        GetData();

        adapter = new HocPhanAdapter(AdminQLHocPhanActivity.this,R.layout.listview_hp,hocPhanArrayList);
        list_hp.setAdapter(adapter);

        QLHP_add_hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminQLHocPhanAddActivity.class);
                startActivity(intent);
            }
        });

        QLHP_back_button.setOnClickListener(new View.OnClickListener() {
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
        DatabaseReference databaseReference = database.getReference().child("HocPhan");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // cập nhật lại dữ liệu mới lên list_sv và xóa dữ liệu cũ
                adapter.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // chuyển đổi dữ liệu qua class SinhVien
                    HocPhan hocPhan = dataSnapshot.getValue(HocPhan.class);
                    // thêm sinh viên vào list_sv
                    assert hocPhan != null;
                    hocPhan.setMahp(dataSnapshot.getKey());
                    adapter.add(hocPhan);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail" + error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}