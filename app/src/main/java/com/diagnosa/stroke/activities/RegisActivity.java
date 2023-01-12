package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBuser;

public class RegisActivity extends AppCompatActivity {
    EditText nik, username, password, nama;
    Button daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        nik = findViewById(R.id.regisNIK);
        nama = findViewById(R.id.regisNama);
        username = findViewById(R.id.regisUsername);
        password = findViewById(R.id.regisPassword);
        daftar = findViewById(R.id.btnDaftar);
        DBuser db = new DBuser(this);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = nik.getText().toString().trim();
                String name = nama.getText().toString().trim();
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                Boolean cek = db.checkNIK(id);
                Boolean chek = db.checkEmail(user);

                ContentValues values = new ContentValues();
                values.put(DBuser.row_nik, id);
                values.put(DBuser.row_nama, name);
                values.put(DBuser.row_email, user);
                values.put(DBuser.row_password, pass);
                values.put(DBuser.row_level, "user");

                if (id.isEmpty()){
                    nik.setError("Data tidak boleh kosong");
                    nik.requestFocus();
                } else
                if (name.isEmpty()){
                    nama.setError("Data tidak boleh kosong");
                    nama.requestFocus();
                } else
                if (user.isEmpty()){
                    username.setError("Data tidak boleh kosong");
                    username.requestFocus();
                } else
                if (pass.isEmpty()){
                    password.setError("Data tidak boleh kosong");
                    password.requestFocus();
                } else {
                    if (cek.equals(true)){
                        nik.requestFocus();
                        nik.selectAll();
                        nik.setError("No identitas sudah digunakan");
                    } else
                    if (chek.equals(true)){
                        username.requestFocus();
                        username.selectAll();
                        username.setError("Username sudah digunakan");
                    } else
                    if (cek == false && chek == false){
                        db.insertData(values);
                        Toast toast = Toast.makeText(getApplicationContext(), "Pendaftaran berhasil", Toast.LENGTH_SHORT);
                        toast.show();
                        startActivity(new Intent(RegisActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        });
    }

    public void kemenulogin(View v){
        startActivity(new Intent(RegisActivity.this, LoginActivity.class));
        finish();
    }
}