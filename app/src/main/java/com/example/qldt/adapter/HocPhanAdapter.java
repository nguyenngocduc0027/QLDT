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

import com.example.qldt.AdminQLGiangVienEditActivity;
import com.example.qldt.AdminQLHocPhanEditActivity;
import com.example.qldt.R;
import com.example.qldt.model.HocPhan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HocPhanAdapter  extends ArrayAdapter<HocPhan> {

    private Activity activity;
    private int resource;
    private List<HocPhan> objects;
    public HocPhanAdapter(@NonNull Activity activity, int resource, @NonNull List<HocPhan> objects) {
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


        TextView mahp = view.findViewById(R.id.item_list_hp_mahp);
        TextView name = view.findViewById(R.id.item_list_hp_name);
        TextView sotc = view.findViewById(R.id.item_list_hp_sotc);
        TextView intro =view.findViewById(R.id.item_list_hp_intro);

        // lấy dữ liệu của đối tượng sinh viên đưa lên các textview thành phần
        HocPhan hocPhan  = this.objects.get(position);
        mahp.setText(hocPhan.getMahp());
        name.setText(hocPhan.getName());
        sotc.setText(hocPhan.getSotc());
        intro.setText(hocPhan.getIntro());

        ImageView btn_popup_menu = view.findViewById(R.id.btn_popup_menu_QLHP_item);
        btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ViewHolder")
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity,v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_menu_edit_hp){
                            Intent go_to_layout_edit_hp = new Intent(activity, AdminQLHocPhanEditActivity.class);
                            go_to_layout_edit_hp.putExtra("HOCPHAN",hocPhan);
                            activity.startActivity(go_to_layout_edit_hp);
//                            Toast.makeText(v.getContext(),"edit gv",Toast.LENGTH_LONG).show();
                        }
                        if (item.getItemId() == R.id.popup_menu_delete_hp){
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference();
                            databaseReference.child("HocPhan").child(hocPhan.getMahp()).removeValue();
//                            Toast.makeText(v.getContext(),"delete gv",Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                });

                // gọi popup_menu.xml vào để show
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_hp,popupMenu.getMenu());
                // hiển thị icon
                popupMenu.show();
            }
        });

        return view;
    }
}
