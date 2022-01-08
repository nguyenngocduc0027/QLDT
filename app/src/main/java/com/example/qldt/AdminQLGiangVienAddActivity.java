package com.example.qldt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qldt.model.GiangVien;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminQLGiangVienAddActivity extends AppCompatActivity {

    FirebaseStorage storage ;
    FirebaseDatabase firebaseDatabase ;
    FirebaseAuth firebaseAuth ;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    CircleImageView imageView;

    Uri imageUri;
    String myUri = "";
    RadioGroup admin_add_gtgv;
    DatePickerDialog editpicker;
    TextInputEditText admin_add_msnvgv, admin_add_namegv, admin_add_emailgv, admin_add_dobgv, admin_add_phonegv, admin_add_addressgv ;
    RadioButton admin_add_gtgv_btn,admin_add_gtgv_nam,admin_add_gtgv_nu;
    int selectedId;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlgiang_vien_add);

        storage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("GiangVien");
        storageReference = FirebaseStorage.getInstance().getReference().child("GiangVien");


        imageView = findViewById(R.id.admin_add_imggv_show);
        Button admin_add_gv_btn = findViewById(R.id.admin_add_btnaddgv);
        TextView choose_imggv = findViewById(R.id.admin_add_imggv);
        ImageView back = findViewById(R.id.admin_add_gv_back);
        admin_add_msnvgv = findViewById(R.id.admin_add_msnvgv);
        admin_add_gtgv = findViewById(R.id.admin_add_gtgv);
        admin_add_namegv = findViewById(R.id.admin_add_namegv);
//        admin_add_emailgv = findViewById(R.id.admin_add_emailgv);
        admin_add_dobgv = findViewById(R.id.admin_add_dobgv);
        admin_add_phonegv = findViewById(R.id.admin_add_phonegv);
        admin_add_addressgv = findViewById(R.id.admin_add_addressgv);


        admin_add_dobgv.setInputType(InputType.TYPE_NULL);
        admin_add_dobgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                editpicker = new DatePickerDialog(AdminQLGiangVienAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                admin_add_dobgv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                editpicker.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        choose_imggv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(AdminQLGiangVienAddActivity.this);
            }
        });

        admin_add_gv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_gv_by_admin();
            }
        });
    }

    private void add_gv_by_admin() {
        if (imageUri != null){
            final  StorageReference fileRef = storageReference.child(admin_add_msnvgv.getText().toString()+".jpg");
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        myUri = downloadUrl.toString();

//                        HashMap<String, Object> userMap = new HashMap<>();
//                        userMap.put("image",myUri);
                        if (!admin_add_msnvgv.getEditableText().toString().isEmpty()){
//                            if (!admin_add_emailgv.getEditableText().toString().isEmpty() && admin_add_emailgv.getEditableText().toString().trim().matches(emailPattern)) {
                                if (admin_add_gtgv.getCheckedRadioButtonId() != -1) {
                                    if (!admin_add_namegv.getEditableText().toString().isEmpty() ){
                                        if (!admin_add_dobgv.getEditableText().toString().isEmpty()){
                                            if (!admin_add_phonegv.getEditableText().toString().isEmpty()){
                                                if (!admin_add_addressgv.getEditableText().toString().isEmpty()){
                                                    selectedId = admin_add_gtgv.getCheckedRadioButtonId();
                                                    admin_add_gtgv_btn = findViewById(selectedId);
                                                    String msnv = admin_add_msnvgv.getText().toString();
                                                    String image = downloadUrl.toString();
                                                    String gt = admin_add_gtgv_btn.getText().toString();
                                                    String name = admin_add_namegv.getText().toString();
                                                    String email = admin_add_msnvgv.getText().toString()+"@gmail.com";
                                                    String dob = admin_add_dobgv.getText().toString();
                                                    String phone = admin_add_phonegv.getText().toString();
                                                    String address = admin_add_addressgv.getText().toString();
                                                    String password = "12345678";

                                                    GiangVien giangVien = new GiangVien(image,msnv,email,name,gt,dob,phone,address,password);
                                                    databaseReference.child(msnv).setValue(giangVien);
                                                    backfunc();
                                                } else {
                                                    Toast.makeText(getApplicationContext(),"Địa chỉ trống", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Chưa Có SĐT", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Chưa nhập ngày sinh", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), " Họ tên trống ",Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
                                }
//                            } else {
//                                Toast.makeText(getApplicationContext(), "Email lỗi", Toast.LENGTH_LONG).show();
//                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Mã giảng viên trống", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }
        else {
            if (!admin_add_msnvgv.getEditableText().toString().isEmpty()){
//                if (!admin_add_emailgv.getEditableText().toString().isEmpty() && admin_add_emailgv.getEditableText().toString().trim().matches(emailPattern)) {
                    if (admin_add_gtgv.getCheckedRadioButtonId() != -1) {
                        if (!admin_add_namegv.getEditableText().toString().isEmpty() ){
                            if (!admin_add_dobgv.getEditableText().toString().isEmpty()){
                                if (!admin_add_phonegv.getEditableText().toString().isEmpty()){
                                    if (!admin_add_addressgv.getEditableText().toString().isEmpty()){
                                        selectedId = admin_add_gtgv.getCheckedRadioButtonId();
                                        admin_add_gtgv_btn = findViewById(selectedId);
                                        String msnv = admin_add_msnvgv.getText().toString();
                                        String image = myUri;
                                        String gt = admin_add_gtgv_btn.getText().toString();
                                        String name = admin_add_namegv.getText().toString();
                                        String email = admin_add_msnvgv.getText().toString()+"@gmail.com";
                                        String dob = admin_add_dobgv.getText().toString();
                                        String phone = admin_add_phonegv.getText().toString();
                                        String address = admin_add_addressgv.getText().toString();
                                        String password = "12345678";

                                        GiangVien giangVien = new GiangVien(image,msnv,email,name,gt,dob,phone,address,password);
                                        databaseReference.child(msnv).setValue(giangVien);
                                        backfunc();
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Địa chỉ trống", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Chưa Có SĐT", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Chưa nhập ngày sinh", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), " Họ tên trống ",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Email lỗi", Toast.LENGTH_LONG).show();
//                }
            } else {
                Toast.makeText(getApplicationContext(), "Mã giảng viên trống", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void backfunc() {
        Intent intent = new Intent(getApplicationContext(),AdminQLGiangVienActivity.class);
        Toast.makeText(getApplicationContext(),"Không Thành Công cũng Thành Thụ",Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            imageView.setImageURI(imageUri);
        } else {
            Toast.makeText(this,"Lỗi, hãy thử lại", Toast.LENGTH_SHORT).show();
        }
    }
}