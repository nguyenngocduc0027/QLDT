package com.example.qldt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AdminForgotPasswordActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_forgot_password);

        TextView btn_forgot_password_admin = findViewById(R.id.btn_reset_admin);
        TextInputEditText email_forgot = findViewById(R.id.email_admin_input_forgot);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_forgot_password_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email_forgot.getText().toString().trim().isEmpty()){
                    Toast.makeText(AdminForgotPasswordActivity.this,"Email trống",Toast.LENGTH_LONG).show();
                    return;
                }if (email_forgot.getText().toString().trim().equals(emailPattern)){
                    Toast.makeText(AdminForgotPasswordActivity.this,"Email sai định dạng",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    String email = email_forgot.getText().toString();
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminForgotPasswordActivity.this,"Kiểm tra email để đặt lại mật khẩu",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AdminForgotPasswordActivity.this,AdminLoginActivity.class);
                                startActivity(intent);
                            }   else {
                                Toast.makeText(AdminForgotPasswordActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}