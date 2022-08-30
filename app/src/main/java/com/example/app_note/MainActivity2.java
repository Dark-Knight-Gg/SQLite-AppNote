package com.example.app_note;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ImageView m2_imgBack;
    Database database;
    ListView  m2_lv1;
    Button m2_btnAdd;
    ArrayList<Job> listJob = new ArrayList<Job>();
    JobAdapter adapter;
    private void AnhXa(){
        m2_imgBack=(ImageView) findViewById(R.id.m2_imgBack);
        m2_lv1=(ListView) findViewById(R.id.m2_lv1);
        m2_btnAdd =(Button) findViewById(R.id.m2_btnAdd);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        m2_imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        adapter = new JobAdapter(MainActivity2.this,R.layout.adapter_layout,listJob);
        m2_lv1.setAdapter(adapter);
        //khởi tạo database
        database = new Database(MainActivity2.this,"Note.sql",null,1);
        //tạo 1 bảng nếu bảng đó chưa tồn tại
        database.QueryData("CREATE TABLE IF NOT EXISTS Job (ID INTEGER PRIMARY KEY AUTOINCREMENT, TEN VARCHAR(200))");
        GetData();
        m2_btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAdd();
            }
        });
    }
    private void GetData(){
        Cursor dataJob  = database.GetData("SELECT * FROM Job");
        listJob.clear();
        while(dataJob.moveToNext()){
            String name =dataJob.getString(1);
            int id = dataJob.getInt(0);
            listJob.add(new Job(name,id));
        }
        adapter.notifyDataSetChanged();
    }
    public void DialogAdd(){
        Dialog  dialog = new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add);
        //ánh xạ
        TextView dialog_add_txt = (TextView) dialog.findViewById(R.id.dialog_add_txt);
        EditText dialog_add_etxt =(EditText) dialog.findViewById(R.id.dialog_add_etxt);
        Button dialog_add_btnOk =(Button) dialog.findViewById(R.id.dialog_add_btnOk);
        Button dialoa_add_btnNo=(Button) dialog.findViewById(R.id.dialog_add_btnNo);
        //Xử lý sự kiện
        dialoa_add_btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_add_btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = dialog_add_etxt.getText().toString();
                if(text.equals("")){
                    Toast.makeText(MainActivity2.this, "Vui lòng nhập tên công việc!", Toast.LENGTH_SHORT).show();
                }else{
                    database.QueryData("INSERT INTO Job VALUES(null,'"+text+"')");
                    Toast.makeText(MainActivity2.this, "Đã thêm!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetData();
                }
            }
        });
        dialog.show();
    }
    public void DialogEdit(String ten, int id){
        Dialog dialog = new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit);
        //ánh xạ
        TextView dialog_edit_txt = (TextView) dialog.findViewById(R.id.dialog_edit_txt);
        EditText dialog_edit_etxt =(EditText) dialog.findViewById(R.id.dialog_edit_etxt);
        Button dialog_edit_btnOk =(Button) dialog.findViewById(R.id.dialog_edit_btnOk);
        Button dialog_edit_btnNo=(Button) dialog.findViewById(R.id.dialog_edit_btnNo);
        //Xử lý sự kiệ
        dialog_edit_etxt.setText(ten);
        dialog_edit_btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_edit_btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten_new = dialog_edit_etxt.getText().toString();
                database.QueryData("UPDATE Job SET TEN='"+ten_new+"'WHERE ID='"+id+"'");
                Toast.makeText(MainActivity2.this, "Đã sửa thành công!", Toast.LENGTH_SHORT).show();
                GetData();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void DialogDelete(String ten , int id){
        Dialog dialog = new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete);
        //ánh xạ
        TextView dialog_delete_txt =(TextView) dialog.findViewById(R.id.dialog_delete_txt);
        Button dialog_delete_btnYes =(Button) dialog.findViewById(R.id.dialog_delete_btnYes);
        Button dialog_delete_btnNo =(Button) dialog.findViewById(R.id.dialog_delete_btnNo);
        //Sử lý sự kiệ
        dialog_delete_btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_delete_btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.QueryData("DELETE FROM Job WHERE ID='"+id+"'");
                Toast.makeText(MainActivity2.this, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                GetData();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}