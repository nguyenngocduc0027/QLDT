package com.example.qldt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HandleScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_screen);

        TextView btn_ql = findViewById(R.id.btn_ql);
        btn_ql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_login_ql_screen = new Intent(v.getContext(),AdminLoginActivity.class);
                startActivity(go_login_ql_screen);
            }
        });
    }
}