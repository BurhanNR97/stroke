package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBuser;

public class Tentang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        Toolbar toolbar = findViewById(R.id.toolbartentang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tentang Aplikasi");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("Range")
    @Override
    public void onBackPressed() {
        String id = "";
        DBuser db = new DBuser(this);
        Cursor cek = db.cekID(getIntent().getStringExtra("id"));
        if (cek.moveToFirst()){
            id = cek.getString(cek.getColumnIndex(DBuser.row_level));
            if (id.equals("admin")){
                Intent intent = new Intent(Tentang.this, BerandaAdmin.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                finish();
            } else
            if (id.equals("dokter")){
                Intent intent = new Intent(Tentang.this, BerandaDokter.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                finish();
            } else if (id.equals("user")){
                Intent intent = new Intent(Tentang.this, BerandaPasien.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                finish();
            }
        }
    }
}