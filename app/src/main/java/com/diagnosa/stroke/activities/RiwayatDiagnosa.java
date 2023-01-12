package com.diagnosa.stroke.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.adapter.AdapterGejala;
import com.diagnosa.stroke.adapter.AdapterRiwayat;
import com.diagnosa.stroke.db.DBcetak;
import com.diagnosa.stroke.db.DBdiagnosa;
import com.diagnosa.stroke.db.DBgejala;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class RiwayatDiagnosa extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    DBdiagnosa db;
    DBcetak dBcetak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_diagnosa);

        Toolbar toolbar = findViewById(R.id.toolbarriwayat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Riwayat Diagnosa");

        db = new DBdiagnosa(this);
        dBcetak = new DBcetak(this);
        listView = (ListView)findViewById(R.id.list_riwayat);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), BerandaAdmin.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }

    public void setListView(){
        Cursor cursor = db.allData();
        AdapterRiwayat customCursorAdapter = new AdapterRiwayat(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }

    @SuppressLint("Range")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDriwayatrow);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();
        AlertDialog.Builder builder = new AlertDialog.Builder(RiwayatDiagnosa.this);
        builder.setCancelable(true);
        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteData(id);
                Toast.makeText(RiwayatDiagnosa.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("Cetak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        setListView();
    }
}