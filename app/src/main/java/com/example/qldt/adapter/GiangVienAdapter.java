package com.example.qldt.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.qldt.AdminQLGiangVienEditActivity;
import com.example.qldt.R;
import com.example.qldt.model.GiangVien;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GiangVienAdapter extends ArrayAdapter<GiangVien> {
    private Activity activity;
    private int resource;
    private List<GiangVien> objects;


    public GiangVienAdapter(@NonNull Activity activity, int resource, @NonNull List<GiangVien> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // mỗi một đới tượng sinh viên sẽ trả về 1 view trong list view
        LayoutInflater inflater = this.activity.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(this.resource,null);


        TextView msnv = view.findViewById(R.id.item_list_gv_msnv);
        TextView name = view.findViewById(R.id.item_list_gv_name);
        TextView email = view.findViewById(R.id.item_list_gv_email);
        TextView gt =view.findViewById(R.id.item_list_gv_gt);
        TextView dob = view.findViewById(R.id.item_list_gv_dob);
        TextView phone = view.findViewById(R.id.item_list_gv_phone);
        TextView address = view.findViewById(R.id.item_list_gv_address);
        CircleImageView image = view.findViewById(R.id.item_list_gv_image);

        // lấy dữ liệu của đối tượng sinh viên đưa lên các textview thành phần
        GiangVien giangVien = this.objects.get(position);
        msnv.setText(giangVien.getMsnv());
        name.setText(giangVien.getName());
        email.setText(giangVien.getEmail());
        gt.setText(giangVien.getGt());
        dob.setText(giangVien.getDob());
        phone.setText(giangVien.getPhone());
        address.setText(giangVien.getAddress());
        Glide.with(view.getContext()).load(giangVien.getImage()).override(100,100).fitCenter().into(image);

        Log.d(TAG, "getView: "+giangVien.getEmail());

        ImageView btn_popup_menu = view.findViewById(R.id.btn_popup_menu_QLGV_item);
        btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ViewHolder")
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity,v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_menu_edit_gv){
                            Intent go_to_layout_edit_gv = new Intent(activity, AdminQLGiangVienEditActivity.class);
                            go_to_layout_edit_gv.putExtra("GIANGVIEN",giangVien);
                            activity.startActivity(go_to_layout_edit_gv);
//                            Toast.makeText(v.getContext(),"edit gv",Toast.LENGTH_LONG).show();
                        }
                        if (item.getItemId() == R.id.popup_menu_delete_gv){
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference();
                            databaseReference.child("GiangVien").child(giangVien.getMsnv()).removeValue();
//                            Toast.makeText(v.getContext(),"delete gv",Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                });

                // gọi popup_menu.xml vào để show
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_gv,popupMenu.getMenu());
                // hiển thị icon
                popupMenu.show();
            }
        });

        return view;
    }
}
