package com.example.qldt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.qldt.model.HocPhan;
import com.example.qldt.model.LopHoc;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminQLLopHocAddActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    TextInputEditText malhinput,slsinhvieninput, phonghocinput;
    ArrayAdapter<String> tgbatdauAdapter,thuAdapter, tgketthucAdapter;
    AppCompatSpinner giangvienSpinner, hocphanSpinner, thuSpinner;
    Spinner tgbatdauSpiner, tgketthucSpinner;
    String[] list_thu = { "Thứ 2", "Thứ 3", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ Nhật"};
    String[] list_tgbd = { "6h45", "7h30", "8h25", "9h20", "10h15", "11h00", "12h30", "13h15","14h10","15h05", "16h00", "16h45", "17h45", "18h30"};
    String[] list_tgkt = { "7h30", "8h15", "9h10", "10h05", "11h00", "11h45", "13h15","14h00","14h55", "15h50", "16h45", "17h30", "18h30","19h15"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qllop_hoc_add);

        hocphanSpinner = (AppCompatSpinner) findViewById(R.id.admin_add_lh_hocphan);
        giangvienSpinner =(AppCompatSpinner) findViewById(R.id.admin_add_lh_giangvien);
        thuSpinner = (AppCompatSpinner) findViewById(R.id.admin_add_lh_thu);
        tgbatdauSpiner = findViewById(R.id.admin_add_lh_tgbatdau);
        tgketthucSpinner = findViewById(R.id.admin_add_lh_tgketthuc);
        malhinput = findViewById(R.id.admin_add_lh_malh);
        slsinhvieninput = findViewById(R.id.admin_add_lh_slsinhvien);
        phonghocinput = findViewById(R.id.admin_add_lh_phonghoc);



        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("HocPhan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> mahocphan = new ArrayList<String>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String namehp = dataSnapshot.child("mahp").getValue(String.class)+" - "+dataSnapshot.child("name").getValue(String.class);
                    mahocphan.add(namehp);
                }
                ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mahocphan);
                nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                hocphanSpinner.setAdapter(nameAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("GiangVien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> giangvien = new ArrayList<String>();
                for (DataSnapshot gvSnapshot:snapshot.getChildren()){
                    String namegv = gvSnapshot.child("msnv").getValue(String.class)+" - "+ gvSnapshot.child("name").getValue(String.class);
                    giangvien.add(namegv);
                }
                ArrayAdapter<String> namegvAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, giangvien);
                namegvAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                giangvienSpinner.setAdapter(namegvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        thuAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list_thu);
        thuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thuSpinner.setAdapter(thuAdapter);
        tgbatdauAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list_tgbd);
        tgbatdauAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tgbatdauSpiner.setAdapter(tgbatdauAdapter);
        tgketthucAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list_tgkt);
        tgketthucAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tgketthucSpinner.setAdapter(tgketthucAdapter);


        Button admin_add_btnaddlh = findViewById(R.id.admin_add_btnaddlh);
        admin_add_btnaddlh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!malhinput.getEditableText().toString().isEmpty()){
                    if (!slsinhvieninput.getEditableText().toString().isEmpty()){
                        String malh = malhinput.getEditableText().toString();
                        String tenlop = hocphanSpinner.getSelectedItem().toString();
                        String phonghoc = phonghocinput.getEditableText().toString();
                        String giangvienlh = giangvienSpinner.getSelectedItem().toString();
                        int slsinhvien = Integer.parseInt(slsinhvieninput.getEditableText().toString());
                        String thu = thuSpinner.getSelectedItem().toString();
                        String tgbatdau = tgbatdauSpiner.getSelectedItem().toString();
                        String tgketthuc = tgketthucSpinner.getSelectedItem().toString();

                        LopHoc lopHoc = new LopHoc(malh,tenlop,phonghoc,giangvienlh,thu,tgbatdau,tgketthuc,slsinhvien);
                        databaseReference.child("LopHoc").child(lopHoc.getMalh()).setValue(lopHoc);
                        backfunc();

                    } else {
                        Toast.makeText(getApplicationContext(), "So luong vien trong", Toast.LENGTH_SHORT).show();
                    }
                } else  {
                    Toast.makeText(getApplicationContext(), "Ma lop hoc trong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView back = findViewById(R.id.admin_add_lh_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void backfunc() {
        Toast.makeText(getApplicationContext(),"Không Thành Công cũng Thành Thụ",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),AdminQLLopHocActivity.class));
        finish();
    }
}