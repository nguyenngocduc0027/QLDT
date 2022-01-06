package com.example.qldt;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.qldt.model.UsersAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdminLoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        TextView btn_forgot_password_ad = findViewById(R.id.btn_forgot_pass_admin);
        TextView btn_login_admin = findViewById(R.id.btn_login_admin);
        TextInputEditText log_email = findViewById(R.id.email_admin_input);
        TextInputEditText log_password = findViewById(R.id.password_admin_input);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_forgot_password_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(AdminLoginActivity.this,AdminForgotPasswordActivity.class);
                startActivity(forgot);
            }
        });

        if (firebaseAuth.getCurrentUser() != null) {
            Intent auto_log = new Intent(AdminLoginActivity.this, AdminHomeActivity.class );
            startActivity(auto_log);
            finish();
        } else {
            btn_login_admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent log_ad = new Intent(AdminLoginActivity.this, AdminHomeActivity.class );
                    if (log_email.getText().toString().trim().isEmpty()){
                        Toast.makeText(AdminLoginActivity.this,"Email trống",Toast.LENGTH_LONG).show();
                        return;
                    }if (log_email.getText().toString().trim().equals(emailPattern)){
                        Toast.makeText(AdminLoginActivity.this,"Email sai định dạng",Toast.LENGTH_LONG).show();
                        return;
                    }else if (log_password.getText().toString().isEmpty()) {
                        Toast.makeText(AdminLoginActivity.this,"Password trống",Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        firebaseAuth.signInWithEmailAndPassword(log_email.getText().toString(),log_password.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            String email = log_email.getText().toString();
                                            String password = log_password.getText().toString();
                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin").child(firebaseAuth.getUid());
                                            databaseReference.child("email").setValue(email);
                                            databaseReference.child("password").setValue(password);
                                            Toast.makeText(AdminLoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                            startActivity(log_ad);
                                        } else {
                                            Toast.makeText(AdminLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                    }
                                });
                    }
                }
            });

        }

    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}