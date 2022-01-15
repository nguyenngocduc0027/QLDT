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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qldt.AdminQLHocPhanEditActivity;
import com.example.qldt.AdminQLLopHocDSSVActivity;
import com.example.qldt.AdminQLLopHocEditActivity;
import com.example.qldt.R;
import com.example.qldt.model.LopHoc;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LopHocAdapter extends ArrayAdapter<LopHoc> {

    private Activity activity;
    private int resource;
    private List<LopHoc> objects;

    public LopHocAdapter(@NonNull Activity activity, int resource, @NonNull List<LopHoc> objects) {
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

        TextView malh = view.findViewById(R.id.item_list_lh_malh);
        TextView tenlop = view.findViewById(R.id.item_list_lh_name);
        TextView phonghoc = view.findViewById(R.id.item_list_lh_phonghoc);
        TextView giangvienlh =view.findViewById(R.id.item_list_lh_giangvien);
        TextView slsinhvien = view.findViewById(R.id.item_list_lh_slsv);
        TextView thu = view.findViewById(R.id.item_list_lh_thu);
        TextView tgbatdau = view.findViewById(R.id.item_list_lh_tgbatdau);
        TextView tgketthuc = view.findViewById(R.id.item_list_lh_tgkethuc);
        TextView dslop = view.findViewById(R.id.item_list_lh_dslop);

        // lấy dữ liệu của đối tượng sinh viên đưa lên các textview thành phần
        LopHoc lopHoc = this.objects.get(position);
        malh.setText(lopHoc.getMalh());
        tenlop.setText(lopHoc.getTenlop());
        phonghoc.setText(lopHoc.getPhonghoc());
        giangvienlh.setText(lopHoc.getGiangvienlh());
        slsinhvien.setText(String.valueOf(lopHoc.getSlsinhvien()));
        thu.setText(lopHoc.getThu());
        tgbatdau.setText(lopHoc.getTgbatdau());
        tgketthuc.setText(lopHoc.getTgketthuc());

        ImageView btn_popup_menu = view.findViewById(R.id.btn_popup_menu_QLLH_item);
        btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ViewHolder")
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity,v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_menu_edit_lh){
//                            Intent go_to_layout_edit_hp = new Intent(activity, AdminQLLopHocEditActivity.class);
//                            go_to_layout_edit_hp.putExtra("LOPHOC",lopHoc);
                            activity.startActivity(new Intent(activity, AdminQLLopHocEditActivity.class).putExtra("LOPHOC",lopHoc));
//                            Toast.makeText(v.getContext(),"edit gv",Toast.LENGTH_LONG).show();
                        }
                        if (item.getItemId() == R.id.popup_menu_delete_lh){
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference();
                            databaseReference.child("LopHoc").child(lopHoc.getMalh()).removeValue();
//                            Toast.makeText(v.getContext(),"delete gv",Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                });

                // gọi popup_menu.xml vào để show
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_lh,popupMenu.getMenu());
                // hiển thị icon
                popupMenu.show();
            }
        });

        dslop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, AdminQLLopHocDSSVActivity.class));
            }
        });


        return view;
    }
}
