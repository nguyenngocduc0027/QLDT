package com.example.qldt;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qldt.model.SinhVien;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminQLSinhVienAddActivity extends AppCompatActivity {

    FirebaseStorage storage ;
    FirebaseDatabase firebaseDatabase ;
    FirebaseAuth firebaseAuth ;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    CircleImageView imageView;

    Uri imageUri;
    String myUri = "";
    RadioGroup admin_add_gtsv;
    DatePickerDialog editpicker;
    TextInputEditText admin_add_idsv ,admin_add_namesv, admin_add_emailsv, admin_add_dobsv, admin_add_phonesv, admin_add_addresssv ;
    RadioButton admin_add_gtsv_btn,admin_add_gtsv_nam,admin_add_gtsv_nu;
    int selectedId;

    String mssc_check = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlsinh_vien_add);

        storage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("SinhVien");
        storageReference = FirebaseStorage.getInstance().getReference().child("SinhVien");




        imageView = findViewById(R.id.admin_add_imgsv_show);
        Button admin_add_sv_btn = findViewById(R.id.admin_add_btnaddsv);
        TextView choose_imgsv = findViewById(R.id.admin_add_imgsv);
        ImageView back = findViewById(R.id.admin_add_sv_back);
        admin_add_idsv = findViewById(R.id.admin_add_idsv);
        admin_add_gtsv = findViewById(R.id.admin_add_gtsv);
        admin_add_namesv = findViewById(R.id.admin_add_namesv);
        admin_add_emailsv = findViewById(R.id.admin_add_emailsv);
        admin_add_dobsv = findViewById(R.id.admin_add_dobsv);
        admin_add_phonesv = findViewById(R.id.admin_add_phonesv);
        admin_add_addresssv = findViewById(R.id.admin_add_addresssv);


        admin_add_dobsv.setInputType(InputType.TYPE_NULL);
        admin_add_dobsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                editpicker = new DatePickerDialog(AdminQLSinhVienAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                admin_add_dobsv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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

        choose_imgsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(AdminQLSinhVienAddActivity.this);
            }
        });

        admin_add_sv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_sv_by_admin();
            }
        });

    }

    private void add_sv_by_admin() {
        uploadProfileImage();
    }

    private void uploadProfileImage() {
        if (imageUri != null){
            final  StorageReference fileRef = storageReference.child(admin_add_idsv.getText().toString()+".jpg");
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
                        if (!admin_add_idsv.getEditableText().toString().isEmpty()){
                            if (!admin_add_namesv.getEditableText().toString().isEmpty()) {
                                if (admin_add_gtsv.getCheckedRadioButtonId() != -1) {
                                    if (!admin_add_emailsv.getEditableText().toString().isEmpty() && admin_add_emailsv.getEditableText().toString().trim().matches(emailPattern)){
                                        if (!admin_add_dobsv.getEditableText().toString().isEmpty()){
                                            if (!admin_add_phonesv.getEditableText().toString().isEmpty()){
                                                if (!admin_add_addresssv.getEditableText().toString().isEmpty()){
                                                    selectedId = admin_add_gtsv.getCheckedRadioButtonId();
                                                    admin_add_gtsv_btn = findViewById(selectedId);
                                                    String image = downloadUrl.toString();
                                                    String gt = admin_add_gtsv_btn.getText().toString();
                                                    String mssv = admin_add_idsv.getText().toString();
                                                    String name = admin_add_namesv.getText().toString();
                                                    String email = admin_add_emailsv.getText().toString();
                                                    String dob = admin_add_dobsv.getText().toString();
                                                    String phone = admin_add_phonesv.getText().toString();
                                                    String address = admin_add_addresssv.getText().toString();
                                                    String password = admin_add_idsv.getText().toString();

                                                    SinhVien sinhVien = new SinhVien(image, mssv,name, gt, email, dob, phone, address, password);
                                                    databaseReference.child(sinhVien.getMssv()).setValue(sinhVien);
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
                                        Toast.makeText(getApplicationContext(), " Email Lỗi",Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Họ Tên Trống", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"MSSV Trống", Toast.LENGTH_LONG).show();
                        }


                    }
                }
            });

        }
        else {
            if (!admin_add_idsv.getEditableText().toString().isEmpty()){
                if (!admin_add_namesv.getEditableText().toString().isEmpty()) {
                    if (admin_add_gtsv.getCheckedRadioButtonId() != -1) {
                        if (!admin_add_emailsv.getEditableText().toString().isEmpty() && admin_add_emailsv.getEditableText().toString().trim().matches(emailPattern)){
                            if (!admin_add_dobsv.getEditableText().toString().isEmpty()){
                                if (!admin_add_phonesv.getEditableText().toString().isEmpty()){
                                    if (!admin_add_addresssv.getEditableText().toString().isEmpty()){
                                        selectedId = admin_add_gtsv.getCheckedRadioButtonId();
                                        admin_add_gtsv_btn = findViewById(selectedId);
                                        String image = myUri;
                                        String gt = admin_add_gtsv_btn.getText().toString();
                                        String mssv = admin_add_idsv.getText().toString();
                                        String name = admin_add_namesv.getText().toString();
                                        String email = admin_add_emailsv.getText().toString();
                                        String dob = admin_add_dobsv.getText().toString();
                                        String phone = admin_add_phonesv.getText().toString();
                                        String address = admin_add_addresssv.getText().toString();
                                        String password = admin_add_idsv.getText().toString();

                                        SinhVien sinhVien = new SinhVien(image,mssv,name, gt, email, dob, phone, address, password);
                                        databaseReference.child(sinhVien.getMssv()).setValue(sinhVien);
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
                            Toast.makeText(getApplicationContext(), " Email Lỗi",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Họ Tên Trống", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),"MSSV Trống", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void backfunc() {
        Intent intent = new Intent(getApplicationContext(),AdminQLSinhVienActivity.class);
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