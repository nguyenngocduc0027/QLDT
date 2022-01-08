package com.example.qldt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qldt.model.HocPhan;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminQLHocPhanAddActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextInputEditText admin_add_hp_mahp, admin_add_hp_name, admin_add_hp_sotc, admin_add_hp_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlhoc_phan_add);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("HocPhan");

        admin_add_hp_mahp = findViewById(R.id.admin_add_hp_mahp);
        admin_add_hp_name = findViewById(R.id.admin_add_hp_name);
        admin_add_hp_sotc = findViewById(R.id.admin_add_hp_sotc);
        admin_add_hp_intro = findViewById(R.id.admin_add_hp_intro);

        Button admin_add_hp_btn = findViewById(R.id.admin_add_btnaddhp);
        admin_add_hp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!admin_add_hp_mahp.getEditableText().toString().isEmpty()){
                    if (!admin_add_hp_name.getEditableText().toString().isEmpty()){
                        if (!admin_add_hp_sotc.getEditableText().toString().isEmpty()){
                            String mahp = admin_add_hp_mahp.getEditableText().toString();
                            String name = admin_add_hp_name.getEditableText().toString();
                            String sotc = admin_add_hp_sotc.getEditableText().toString();
                            String intro = admin_add_hp_intro.getEditableText().toString();

                            HocPhan hocPhan = new HocPhan(mahp,name,sotc,intro);
                            databaseReference.child(hocPhan.getMahp()).setValue(hocPhan);
                            backfunc();
                        } else {
                            Toast.makeText(getApplicationContext(),"Chưa có số tín chỉ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Trống tên học phần", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Trống mã học phần", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView back = findViewById(R.id.admin_add_hp_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void backfunc() {
        Intent intent = new Intent(getApplicationContext(),AdminQLHocPhanActivity.class);
        Toast.makeText(getApplicationContext(),"Không Thành Công cũng Thành Thụ",Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

}