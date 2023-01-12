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
import com.diagnosa.stroke.db.DBgejala;

public class TambahGejala extends AppCompatActivity {

    DBgejala db;
    EditText txKode, txNama, txBobot;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_gejala);

        Toolbar toolbar = findViewById(R.id.toolbartambahgejala);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tambah Data");

        db = new DBgejala(this);

        id = getIntent().getLongExtra(DBgejala.row_id, 0);

        txKode = (EditText)findViewById(R.id.txKode_Addgejala);
        txNama = (EditText)findViewById(R.id.txNama_Addgejala);
        txBobot = (EditText)findViewById(R.id.txBobot_Addgejala);
        txNama.requestFocus();

        Cursor kode = db.kode();
        if (kode.moveToNext()) {
            String a = kode.getString(1).substring(1);
            int b = Integer.parseInt(a);
            int c = b + 1;
            String gg = "";
            if (c == 1) {
                gg = "G0";
            } else
            if (c > 1) {
                gg = "G";
            }
            txKode.setText(gg+c);
        } else
        {
            txKode.setText("G01");
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu_gejala, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.save_add_gejala:
                String kode = txKode.getText().toString().trim();
                String nama = txNama.getText().toString().trim();
                String bobot = txBobot.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBgejala.row_kdGejala, kode);
                values.put(DBgejala.row_nmGejala, nama);
                values.put(DBgejala.row_bobot, bobot);

                //Create Condition if Title and Detail is empty
                if (nama.length()==0){
                    txNama.setError("Masukkan gejala penyakit");
                } else
                if (bobot.length()==0){
                    txBobot.setError("Masukkan bobot gejala");
                } else{
                    db.insertData(values);
                    Toast.makeText(TambahGejala.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
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