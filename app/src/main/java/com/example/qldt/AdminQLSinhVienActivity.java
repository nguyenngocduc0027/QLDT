package com.example.qldt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AdminQLSinhVienActivity extends AppCompatActivity {

    ImageButton QLSV_add_sv,QLSV_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlsinh_vien);

        QLSV_add_sv = findViewById(R.id.QLSV_add_sv);
        QLSV_back_button = findViewById(R.id.QLSV_back_button);




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
                finish();
            }
        });
    }
}