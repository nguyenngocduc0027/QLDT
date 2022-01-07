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

import com.bumptech.glide.Glide;
import com.example.qldt.model.SinhVien;
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

public class AdminQLSinhVienEditActivity extends AppCompatActivity {

    FirebaseStorage storage ;
    FirebaseDatabase firebaseDatabase ;
    FirebaseAuth firebaseAuth ;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    CircleImageView imageView;

    Uri imageUri;
    String myUri = "";
    RadioGroup admin_edit_gtsv;
    DatePickerDialog editpicker;
    TextInputEditText admin_edit_idsv ,admin_edit_namesv, admin_edit_emailsv, admin_edit_dobsv, admin_edit_phonesv, admin_edit_addresssv, admin_edit_passwordsv ;
    RadioButton admin_edit_gtsv_btn,admin_edit_gtsv_nam,admin_edit_gtsv_nu;
    int selectedId;

    SinhVien sinhVien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlsinh_vien_edit);

        storage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("SinhVien");
        storageReference = FirebaseStorage.getInstance().getReference().child("SinhVien");




        imageView = findViewById(R.id.admin_edit_imgsv_show);
        Button admin_edit_sv_btn = findViewById(R.id.admin_edit_btneditsv);
        TextView choose_imgsv = findViewById(R.id.admin_edit_imgsv);
        ImageView back = findViewById(R.id.admin_edit_sv_back);
        admin_edit_idsv = findViewById(R.id.admin_edit_idsv);
        admin_edit_gtsv = findViewById(R.id.admin_edit_gtsv);
        admin_edit_namesv = findViewById(R.id.admin_edit_namesv);
        admin_edit_emailsv = findViewById(R.id.admin_edit_emailsv);
        admin_edit_dobsv = findViewById(R.id.admin_edit_dobsv);
        admin_edit_phonesv = findViewById(R.id.admin_edit_phonesv);
        admin_edit_addresssv = findViewById(R.id.admin_edit_addresssv);
        admin_edit_passwordsv = findViewById(R.id.admin_edit_passwordsv);


        admin_edit_dobsv.setInputType(InputType.TYPE_NULL);
        admin_edit_dobsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                editpicker = new DatePickerDialog(AdminQLSinhVienEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                admin_edit_dobsv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                editpicker.show();
            }
        });

        // lấy gói data từ màn hình hiển thị sinh viên
        Intent getdata = getIntent();
        // truyền vào khóa của gói để nhận dữ liệu đúng
        sinhVien = (SinhVien) getdata.getSerializableExtra("SINHVIEN");
        if (sinhVien != null) {
            // đưa thông tin từ gói nhận đc hiển thị lên hinttext của edit text
            Glide.with(getApplicationContext()).load(sinhVien.getImage()).override(120,120).fitCenter().into(imageView);
            admin_edit_idsv.setText(sinhVien.getMssv());
            admin_edit_namesv.setText(sinhVien.getName());
            admin_edit_emailsv.setText(sinhVien.getEmail());
            admin_edit_dobsv.setText(sinhVien.getDob());
            admin_edit_phonesv.setText(sinhVien.getPhone());
            admin_edit_addresssv.setText(sinhVien.getAddress());
            admin_edit_passwordsv.setText(sinhVien.getPassword());
        } else {
            Toast.makeText(getApplicationContext(),"Error Load Data Sinh Vien", Toast.LENGTH_LONG).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        choose_imgsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(AdminQLSinhVienEditActivity.this);
            }
        });

        admin_edit_sv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage();
            }
        });
    }

    private void uploadProfileImage() {
        if (imageUri != null){
            final  StorageReference fileRef = storageReference.child(admin_edit_idsv.getText().toString()+".jpg");
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
                        if (admin_edit_gtsv.getCheckedRadioButtonId() != -1) {
                            selectedId = admin_edit_gtsv.getCheckedRadioButtonId();
                            admin_edit_gtsv_btn = findViewById(selectedId);
                            String image = downloadUrl.toString();
                            String gt = admin_edit_gtsv_btn.getText().toString();
                            String mssv = admin_edit_idsv.getText().toString();
                            String name = admin_edit_namesv.getText().toString();
                            String email = admin_edit_emailsv.getText().toString();
                            String dob = admin_edit_dobsv.getText().toString();
                            String phone = admin_edit_phonesv.getText().toString();
                            String address = admin_edit_addresssv.getText().toString();
                            String password = admin_edit_idsv.getText().toString();
                            sinhVien = new SinhVien(image, mssv,name, gt, email, dob, phone, address, password);
                            databaseReference.child(sinhVien.getMssv()).setValue(sinhVien);
                            backfunc();
                        } else {
                            Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }
        else {
            if (admin_edit_gtsv.getCheckedRadioButtonId() != -1) {
                selectedId = admin_edit_gtsv.getCheckedRadioButtonId();
                admin_edit_gtsv_btn = findViewById(selectedId);
                String image = myUri;
                String gt = admin_edit_gtsv_btn.getText().toString();
                String mssv = admin_edit_idsv.getText().toString();
                String name = admin_edit_namesv.getText().toString();
                String email = admin_edit_emailsv.getText().toString();
                String dob = admin_edit_dobsv.getText().toString();
                String phone = admin_edit_phonesv.getText().toString();
                String address = admin_edit_addresssv.getText().toString();
                String password = admin_edit_idsv.getText().toString();
                sinhVien = new SinhVien(image, mssv,name, gt, email, dob, phone, address, password);
                databaseReference.child(sinhVien.getMssv()).setValue(sinhVien);
                backfunc();
            } else {
                Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
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