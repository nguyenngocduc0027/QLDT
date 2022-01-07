package com.example.qldt.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.qldt.AdminQLSinhVienEditActivity;
import com.example.qldt.R;
import com.example.qldt.model.SinhVien;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SinhVienAdapter extends ArrayAdapter<SinhVien> {

    private Activity activity;
    private int resource;
    private List<SinhVien> objects;

    public SinhVienAdapter(@NonNull Activity activity, int resource, @NonNull List<SinhVien> objects) {
        super(activity, resource, objects);
        this.activity =activity;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // mỗi một đới tượng sinh viên sẽ trả về 1 view trong list view
        LayoutInflater inflater = this.activity.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(this.resource,null);


        TextView mssv = view.findViewById(R.id.item_list_sv_mssv);
        TextView name = view.findViewById(R.id.item_list_sv_name);
        TextView gt =view.findViewById(R.id.item_list_sv_gt);
        TextView dob = view.findViewById(R.id.item_list_sv_dob);
        TextView email = view.findViewById(R.id.item_list_sv_email);
        TextView phone = view.findViewById(R.id.item_list_sv_phone);
        TextView address = view.findViewById(R.id.item_list_sv_address);
        CircleImageView image = view.findViewById(R.id.item_list_sv_image);

        // lấy dữ liệu của đối tượng sinh viên đưa lên các textview thành phần
        SinhVien sinhVien = this.objects.get(position);
        mssv.setText(sinhVien.getMssv());
        name.setText(sinhVien.getName());
        gt.setText(sinhVien.getGt());
        dob.setText(sinhVien.getDob());
        email.setText(sinhVien.getEmail());
        phone.setText(sinhVien.getPhone());
        address.setText(sinhVien.getAddress());
        Glide.with(view.getContext()).load(sinhVien.getImage()).override(100,100).fitCenter().into(image);

        ImageView btn_popup_menu = view.findViewById(R.id.btn_popup_menu_QLSV_item);
        btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ViewHolder")
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity,v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_menu_edit_sv){
                            Intent go_to_layout_edit_sv = new Intent(activity, AdminQLSinhVienEditActivity.class);
                            go_to_layout_edit_sv.putExtra("SINHVIEN",sinhVien);
                            activity.startActivity(go_to_layout_edit_sv);
                        }
                        if (item.getItemId() == R.id.popup_menu_delete_sv){
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference();
                            databaseReference.child("SinhVien").child(sinhVien.getMssv()).removeValue();
                        }
                        return false;
                    }
                });

                // gọi popup_menu.xml vào để show
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                // hiển thị icon
                popupMenu.show();
            }
        });

        return view;
    }
}
