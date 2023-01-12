package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBpenyakit;

public class TambahPenyakit extends AppCompatActivity {

    DBpenyakit db;
    EditText txKode, txNama;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penyakit);

        Toolbar toolbar = findViewById(R.id.toolbartambahjenis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tambah Data");

        db = new DBpenyakit(this);

        id = getIntent().getLongExtra(DBpenyakit.row_id, 0);

        txKode = (EditText)findViewById(R.id.txKode_Addjenis);
        txNama = (EditText)findViewById(R.id.txNama_Addjenis);
        txNama.requestFocus();

        Cursor kode = db.kode();
        if (kode.moveToNext()) {
            String a = kode.getString(1).substring(1);
            int b = Integer.parseInt(a);
            int c = b + 1;
            txKode.setText("S"+c);
        } else
        {
            txKode.setText("S1");
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu_jenispenyakit, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.save_add_jenis:
                String kode = txKode.getText().toString().trim();
                String nama = txNama.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBpenyakit.row_kdPenyakit, kode);
                values.put(DBpenyakit.row_nmPenyakit, nama);

                //Create Condition if Title and Detail is empty
                if (nama.length()==0){
                    txNama.setError("Masukkan nama penyakit");
                }else{
                    db.insertData(values);
                    Toast.makeText(TambahPenyakit.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}