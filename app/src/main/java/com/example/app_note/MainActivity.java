package com.example.app_note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView m1_txtLogin,m1_txtDky;
    EditText m1_etxtUsername,m1_etxtPassword;
    CheckBox m1_checkBoxLogin;
    Button m1_btnLogin;
    Database database3;
    ArrayList<String> listUserNew = new ArrayList<String>();
    ArrayList<String> listPassNew = new ArrayList<String>();
    SharedPreferences sharedPreferences;
    //Ánh xạ
    private void AnhXa(){
        m1_txtLogin=(TextView) findViewById(R.id.m1_txtLogin);
        m1_etxtUsername=(EditText) findViewById(R.id.m1_etxtUsername);
        m1_etxtPassword=(EditText) findViewById(R.id.m1_etxtPassword);
        m1_checkBoxLogin=(CheckBox) findViewById(R.id.m1_checkBoxLogin);
        m1_btnLogin=(Button) findViewById(R.id.m1_btnLogin);
        m1_txtDky=(TextView) findViewById(R.id.m1_txtxDky);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
        //Lấy giá trị từ sharedPreferences
        m1_etxtUsername.setText(sharedPreferences.getString("Username",""));
        m1_etxtPassword.setText(sharedPreferences.getString("Password",""));
        m1_checkBoxLogin.setChecked(sharedPreferences.getBoolean("Checked",false));
        //khỏi tạo 1 database
        database3 = new Database(MainActivity.this,"Login.sql",null,1);
        m1_txtDky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,MainActivity3.class);
                startActivity(intent);
            }
        });
        m1_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = m1_etxtUsername.getText().toString();
                String password = m1_etxtPassword.getText().toString();
                if(Check()){
                    if(m1_checkBoxLogin.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Username",username);
                        editor.putString("Password",password);
                        editor.putBoolean("Checked",true);
                        editor.commit();
                    }else{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("Username");
                        editor.remove("Password");
                        editor.remove("Checked");
                        editor.commit();
                    }
                    Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(intent);
                }
                if(Check()==false){
                    Toast.makeText(MainActivity.this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean Check(){
        boolean flag = false;
        String user = m1_etxtUsername.getText().toString();
        String pass = m1_etxtPassword.getText().toString();
        Intent intent = getIntent();
        listUserNew=intent.getStringArrayListExtra("dataUser");
        listPassNew=intent.getStringArrayListExtra("dataPass");
        for(int i=0;i<listUserNew.size();i++){
            if(user.equals(listUserNew.get(i))&&pass.equals(listPassNew.get(i))){
                flag =true;
                break;
            }
        }
        return flag;
    }
}