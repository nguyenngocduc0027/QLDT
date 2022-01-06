package com.example.qldt;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qldt.model.UsersAdmin;
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

public class AdminEditProfileActivity extends AppCompatActivity {

    FirebaseStorage storage ;
    FirebaseDatabase firebaseDatabase ;
    FirebaseAuth firebaseAuth ;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    CircleImageView imageView;

    Uri imageUri;
    String myUri = "";
    RadioGroup btn_gender_group;
    DatePickerDialog editpicker;
    TextInputEditText email_admin_edit, name_admin_edit, dob_admin_edit, phone_admin_edit, address_admin_edit ;
    RadioButton btn_gender,btn_gender_nam,btn_gender_nu;
    int selectedId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_profile);
        storage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin").child(firebaseAuth.getCurrentUser().getUid());
        storageReference = FirebaseStorage.getInstance().getReference().child("Admin");


        imageView = findViewById(R.id.choose_image);
        Button update = findViewById(R.id.btn_update_profile);
        TextView chang_img = findViewById(R.id.btn_change_img_profile);
        ImageView edit_back = findViewById(R.id.edit_back);
        btn_gender_group = findViewById(R.id.btn_gender);
        name_admin_edit = findViewById(R.id.name_admin_edit);
        email_admin_edit = findViewById(R.id.email_admin_edit);
        dob_admin_edit = findViewById(R.id.dob_admin_edit);
        phone_admin_edit = findViewById(R.id.phone_admin_edit);
        address_admin_edit = findViewById(R.id.address_admin_edit);





        dob_admin_edit.setInputType(InputType.TYPE_NULL);
        dob_admin_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                editpicker = new DatePickerDialog(AdminEditProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob_admin_edit.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                editpicker.show();
            }
        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UsersAdmin usersAdmin = snapshot.getValue(UsersAdmin.class);
                assert usersAdmin != null;
                name_admin_edit.setText(usersAdmin.getName());
                email_admin_edit.setText(usersAdmin.getEmail());
                dob_admin_edit.setText(usersAdmin.getDob());
                phone_admin_edit.setText(usersAdmin.getPhone());
                address_admin_edit.setText(usersAdmin.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminEditProfileActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String link = snapshot.getValue(String.class);
                        Glide.with(AdminEditProfileActivity.this).load(link).fitCenter().into(imageView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AdminEditProfileActivity.this, "Lỗi dữ liệu ảnh", Toast.LENGTH_SHORT).show();
                    }
                });

        edit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage();
            }
        });

        chang_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(AdminEditProfileActivity.this);
            }
        });


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

    private void uploadProfileImage() {
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Thay đổi thông tin");
//        progressDialog.setMessage("Vui lòng đợi .... ahihi ");
//        progressDialog.show();

        if (imageUri != null){
            final  StorageReference fileRef = storageReference.child(firebaseAuth.getCurrentUser().getUid()+".jpg");
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

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image",myUri);


                        if (btn_gender_group.getCheckedRadioButtonId() != -1){
                            dataupdate();
                            databaseReference.updateChildren(userMap);
                            backfunc();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
                        }

//                        progressDialog.dismiss();

                    }
                }
            });

        }
        else {
//            progressDialog.dismiss();
            if (btn_gender_group.getCheckedRadioButtonId() != -1){
                dataupdate();
                backfunc();
            }
            else {
                Toast.makeText(getApplicationContext(),"Vui lòng chọn giới tính", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void backfunc() {
        Intent intent = new Intent(getApplicationContext(),AdminProfileActivity.class);
        Toast.makeText(getApplicationContext(),"Không Thành Công cũng Thành Thụ",Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

    private void dataupdate() {
            selectedId = btn_gender_group.getCheckedRadioButtonId();
            btn_gender = findViewById(selectedId);
            String admin_gt_edit = btn_gender.getText().toString();
            String name = name_admin_edit.getText().toString();
            String email = email_admin_edit.getText().toString();
            String dob = dob_admin_edit.getText().toString();
            String phone = phone_admin_edit.getText().toString();
            String address = address_admin_edit.getText().toString();
            databaseReference.child("gt").setValue(admin_gt_edit);
            databaseReference.child("name").setValue(name);
            databaseReference.child("email").setValue(email);
            databaseReference.child("dob").setValue(dob);
            databaseReference.child("phone").setValue(phone);
            databaseReference.child("address").setValue(address);
    }
}