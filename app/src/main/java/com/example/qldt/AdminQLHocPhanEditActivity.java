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

public class AdminQLHocPhanEditActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    TextInputEditText admin_edit_hp_mahp, admin_edit_hp_name, admin_edit_hp_sotc, admin_edit_hp_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlhoc_phan_edit);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("HocPhan");

        admin_edit_hp_mahp = findViewById(R.id.admin_edit_hp_mahp);
        admin_edit_hp_name = findViewById(R.id.admin_edit_hp_name);
        admin_edit_hp_sotc = findViewById(R.id.admin_edit_hp_sotc);
        admin_edit_hp_intro = findViewById(R.id.admin_edit_hp_intro);

        // lấy gói data từ màn hình hiển thị sinh viên
        Intent getdata = getIntent();
        // truyền vào khóa của gói để nhận dữ liệu đúng
        HocPhan hocPhan = (HocPhan) getdata.getSerializableExtra("HOCPHAN");
        if (hocPhan != null){
            admin_edit_hp_mahp.setText(hocPhan.getMahp());
            admin_edit_hp_name.setText(hocPhan.getName());
            admin_edit_hp_sotc.setText(hocPhan.getSotc());
            admin_edit_hp_intro.setText(hocPhan.getIntro());
        } else {
            Toast.makeText(getApplicationContext(),"Lỗi load dữ liệu",Toast.LENGTH_LONG).show();
        }

        Button admin_edit_hp_btn = findViewById(R.id.admin_edit_btnedithp);
        admin_edit_hp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahp = admin_edit_hp_mahp.getEditableText().toString();
                String name = admin_edit_hp_name.getEditableText().toString();
                String sotc = admin_edit_hp_sotc.getEditableText().toString();
                String intro = admin_edit_hp_intro.getEditableText().toString();

                HocPhan hocPhan = new HocPhan(mahp,name,sotc,intro);
                databaseReference.child(hocPhan.getMahp()).setValue(hocPhan);
                backfunc();
            }
        });

        ImageView back = findViewById(R.id.admin_edit_hp_back);
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