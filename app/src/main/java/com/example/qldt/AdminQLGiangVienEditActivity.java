package com.example.qldt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import com.example.qldt.model.GiangVien;
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

public class AdminQLGiangVienEditActivity extends AppCompatActivity {

    FirebaseStorage storage ;
    FirebaseDatabase firebaseDatabase ;
    FirebaseAuth firebaseAuth ;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    CircleImageView imageView;

    Uri imageUri;
    String myUri = "";
    RadioGroup admin_edit_gtgv;
    DatePickerDialog editpicker;
    TextInputEditText admin_edit_msnvgv ,admin_edit_namegv, admin_edit_emailgv, admin_edit_dobgv, admin_edit_phonegv, admin_edit_addressgv, admin_edit_passwordgv ;
    RadioButton admin_edit_gtgv_btn,admin_edit_gtgv_nam,admin_edit_gtgv_nu;
    int selectedId;

    GiangVien giangVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlgiang_vien_edit);

        storage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("GiangVien");
        storageReference = FirebaseStorage.getInstance().getReference().child("GiangVien");




        imageView = findViewById(R.id.admin_edit_imggv_show);
        Button admin_edit_gv_btn = findViewById(R.id.admin_edit_btneditgv);
        TextView choose_imggv = findViewById(R.id.admin_edit_imggv);
        ImageView back = findViewById(R.id.admin_edit_gv_back);
        admin_edit_msnvgv = findViewById(R.id.admin_edit_msnvgv);
        admin_edit_gtgv = findViewById(R.id.admin_edit_gtgv);
        admin_edit_namegv = findViewById(R.id.admin_edit_namegv);
//        admin_edit_emailgv = findViewById(R.id.admin_edit_emailgv);
        admin_edit_dobgv = findViewById(R.id.admin_edit_dobgv);
        admin_edit_phonegv = findViewById(R.id.admin_edit_phonegv);
        admin_edit_addressgv = findViewById(R.id.admin_edit_addressgv);
        admin_edit_passwordgv = findViewById(R.id.admin_edit_passwordgv);


        admin_edit_dobgv.setInputType(InputType.TYPE_NULL);
        admin_edit_dobgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                editpicker = new DatePickerDialog(AdminQLGiangVienEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                admin_edit_dobgv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                editpicker.show();
            }
        });

        // lấy gói data từ màn hình hiển thị sinh viên
        Intent getdata = getIntent();
        // truyền vào khóa của gói để nhận dữ liệu đúng
        giangVien = (GiangVien) getdata.getSerializableExtra("GIANGVIEN");
        if (giangVien != null) {
            // đưa thông tin từ gói nhận đc hiển thị lên hinttext của edit text
            Glide.with(getApplicationContext()).load(giangVien.getImage()).override(120,120).fitCenter().into(imageView);
            admin_edit_msnvgv.setText(giangVien.getMsnv());
            admin_edit_namegv.setText(giangVien.getName());
//            admin_edit_emailgv.setText(giangVien.getEmail());
            admin_edit_dobgv.setText(giangVien.getDob());
            admin_edit_phonegv.setText(giangVien.getPhone());
            admin_edit_addressgv.setText(giangVien.getAddress());
            admin_edit_passwordgv.setText(giangVien.getPassword());
        } else {
            Toast.makeText(getApplicationContext(),"Error Load Data Sinh Vien", Toast.LENGTH_LONG).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        choose_imggv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(AdminQLGiangVienEditActivity.this);
            }
        });

        admin_edit_gv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage();
            }
        });
    }

    private void uploadProfileImage() {
        if (imageUri != null){
            final  StorageReference fileRef = storageReference.child(admin_edit_msnvgv.getText().toString()+".jpg");
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
                        if (admin_edit_gtgv.getCheckedRadioButtonId() != -1) {
                            selectedId = admin_edit_gtgv.getCheckedRadioButtonId();
                            admin_edit_gtgv_btn = findViewById(selectedId);
                            String image = downloadUrl.toString();
                            String gt = admin_edit_gtgv_btn.getText().toString();
                            String msnv = admin_edit_msnvgv.getText().toString();
                            String name = admin_edit_namegv.getText().toString();
                            String email = admin_edit_msnvgv.getText().toString()+"@gmail.com";
                            String dob = admin_edit_dobgv.getText().toString();
                            String phone = admin_edit_phonegv.getText().toString();
                            String address = admin_edit_addressgv.getText().toString();
                            String password = admin_edit_passwordgv.getText().toString();
                            giangVien = new GiangVien(image,msnv,email,name,gt,dob,phone,address,password);
                            databaseReference.child(giangVien.getMsnv()).setValue(giangVien);
                            backfunc();
                        } else {
                            Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }
        else {
            if (admin_edit_gtgv.getCheckedRadioButtonId() != -1) {
                selectedId = admin_edit_gtgv.getCheckedRadioButtonId();
                admin_edit_gtgv_btn = findViewById(selectedId);
                String image = giangVien.getImage();
                String gt = admin_edit_gtgv_btn.getText().toString();
                String msnv = admin_edit_msnvgv.getText().toString();
                String name = admin_edit_namegv.getText().toString();
                String email = admin_edit_msnvgv.getText().toString()+"@gmail.com";
                String dob = admin_edit_dobgv.getText().toString();
                String phone = admin_edit_phonegv.getText().toString();
                String address = admin_edit_addressgv.getText().toString();
                String password = admin_edit_passwordgv.getText().toString();
                giangVien = new GiangVien(image,msnv,email,name,gt,dob,phone,address,password);
                databaseReference.child(giangVien.getMsnv()).setValue(giangVien);
                backfunc();
            } else {
                Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
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