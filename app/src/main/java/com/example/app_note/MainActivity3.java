package com.example.app_note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    TextView m3_txtDky,m3_txtLogin;
    Database database2;
    EditText m3_etxtUsername,m3_etxtPassword,m3_etxtRepeatPassword;
    Button m3_btnDky;
    ArrayList<String> listUsername = new ArrayList<String>();
    ArrayList<String> listPassword = new ArrayList<String>();
    ArrayList<DataDky> listDataDky = new ArrayList<DataDky>();
    private void AnhXa(){
        m3_txtLogin=(TextView) findViewById(R.id.m3_txtLogin);
        m3_txtDky=(TextView) findViewById(R.id.m3_txtDky);
        m3_etxtUsername=(EditText) findViewById(R.id.m3_etxtUsername);
        m3_etxtPassword=(EditText) findViewById(R.id.m3_etxtPassword);
        m3_etxtRepeatPassword=(EditText) findViewById(R.id.m3_etxtRepeatPassword);
        m3_btnDky=(Button) findViewById(R.id.m3_btnDky);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        AnhXa();

        //Khỏi tạo database
        database2 = new Database(MainActivity3.this,"Dky.sql",null,1);
        //Tạo 1 bảng nếu bảng đó chưa tồn tại
        database2.QueryData("CREATE TABLE IF NOT EXISTS DataDky(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME VARCHAR(200), PASS VARCHAR(200))");

        m3_btnDky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
                GETDATA();
                for(int i=0;i<listDataDky.size();i++){
                    String user = listDataDky.get(i).getUsername();
                    String pass = listDataDky.get(i).getPassword();
                    listUsername.add(i,user);
                    listPassword.add(i,pass);
                }
                Intent intent = new Intent(MainActivity3.this,MainActivity.class);
                intent.putExtra("dataUser",listUsername);
                intent.putExtra("dataPass",listPassword);
                startActivity(intent);
            }
        });


        m3_txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void  Check(){
        String username = m3_etxtUsername.getText().toString();
        String password = m3_etxtPassword.getText().toString();
        String repeatpassword = m3_etxtRepeatPassword.getText().toString();
        if(username.equals("")||password.equals("")||repeatpassword.equals("")){
            Toast.makeText(this, "Bận phải nhập đầy đủ các mục trên!", Toast.LENGTH_SHORT).show();
        }else{
            if(password.equals(repeatpassword)){
                database2.QueryData("INSERT INTO DataDky VALUES(null,'"+m3_etxtUsername.getText()+"','"+m3_etxtPassword.getText()+"')");
                Toast.makeText(this, "Dăng ký thành công!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Nhác lại mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void GETDATA(){
        Cursor dataDky = database2.GetData("SELECT * FROM DataDky");
        //listDataDky.clear();
        while(dataDky.moveToNext()){
            int id = dataDky.getInt(0);
            String username = dataDky.getString(1);
            String password = dataDky.getString(2);
            listDataDky.add(new DataDky(id,username,password));
        }
    }
}