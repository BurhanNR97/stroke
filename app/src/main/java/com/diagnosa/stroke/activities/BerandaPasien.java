package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBuser;

public class BerandaPasien extends AppCompatActivity {
    String nik = "";
    String iidd = "";
    private int countFragment;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_pasien);

        String id = getIntent().getStringExtra("id");
        DBuser db = new DBuser(this);
        TextView nama = findViewById(R.id.userPasien);
        Cursor cek = db.cekID(id);
        if (cek.moveToFirst()){
            nik = cek.getString(cek.getColumnIndex(DBuser.row_nik));
            iidd = id;
            nama.setText(cek.getString(cek.getColumnIndex(DBuser.row_nama)));
        }
    }

    public void diagnosa(View view){
        Intent intent = new Intent(getApplicationContext(), DiagnosaActivity.class);
        intent.putExtra("nik", nik);
        intent.putExtra("id", iidd);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public int getCountFragment() {
        return countFragment;
    }

    public void keluar(View view) {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage("Anda yakin ingin logout?")
                .setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(BerandaPasien.this, LoginActivity.class));
                        finish();
                    }
                })
                .show();
    }

    public void tentang(View view){
        Intent intent = new Intent(getApplicationContext(), Tentang.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }
}