package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBbiodata;
import com.diagnosa.stroke.db.DBuser;

public class LoginActivity extends AppCompatActivity {
    EditText txtUser, txtPass;
    DBuser dBuser;
    DBbiodata dBbiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dBuser = new DBuser(this);
        dBbiodata = new DBbiodata(this);
        txtUser = findViewById(R.id.loginUsername);
        txtPass = findViewById(R.id.loginPassword);
    }

    public void regis(View view){
        startActivity(new Intent(this, RegisActivity.class));
        finish();
    }

    @SuppressLint("Range")
    public void btnMasuk(View view){
        String email = txtUser.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        ContentValues values = new ContentValues();
        Boolean res = dBuser.checkUser(email, password);

        if(res == true){
            Cursor hak = dBuser.checkLevel(email);
            finish();
            if (hak.moveToNext()) {
                String l = hak.getString(hak.getColumnIndex(DBuser.row_id));
                if (hak.getString(3).equals("admin")) {
                    Intent intent = new Intent(getApplicationContext(), BerandaAdmin.class);
                    intent.putExtra("id", l);
                    finish();
                    startActivity(intent);
                } else
                if (hak.getString(hak.getColumnIndex(DBuser.row_level)).equals("user")) {
                    Intent intent = new Intent(getApplicationContext(), BerandaPasien.class);
                    intent.putExtra("id", l);
                    finish();
                    startActivity(intent);
                } else
                if (hak.getString(3).equals("dokter")) {
                    Intent intent = new Intent(getApplicationContext(), BerandaDokter.class);
                    intent.putExtra("id", l);
                    finish();
                    startActivity(intent);
                }
            }
        } else {
            Toast.makeText(LoginActivity.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
        }
    }
}