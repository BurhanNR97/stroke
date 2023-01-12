package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBgejala;
import com.diagnosa.stroke.db.DBpenyakit;
import com.diagnosa.stroke.db.DBrules;

import java.util.List;

public class TambahRules extends AppCompatActivity {

    DBgejala dbGejala;
    DBpenyakit dbPenyakit;
    DBrules db;
    EditText TxKode, TxNilai;
    Spinner spA, spK;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rules);

        Toolbar toolbar = findViewById(R.id.toolbarAddrules);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tambah Data");

        dbGejala = new DBgejala(this);
        dbPenyakit = new DBpenyakit(this);
        db = new DBrules(this);

        id = getIntent().getLongExtra(DBpenyakit.row_id, 0);

        TxKode = (EditText)findViewById(R.id.txKode_AddRules);
        TxNilai = (EditText)findViewById(R.id.txNilai_addRules);
        spA = (Spinner)findViewById(R.id.sp_alternatifAdd);
        spK = (Spinner)findViewById(R.id.sp_kriteriaAdd );

        loadSpinner();

        Cursor kode = db.kode();
        if (kode.moveToNext()) {
            String a = kode.getString(1).substring(1);
            int b = Integer.parseInt(a);
            int c = b + 1;
            String d = String.valueOf(c);
            if (d.length() == 2) {
                TxKode.setText("R0"+c);
            } else
            if (d.length() == 3) {
                TxKode.setText("R"+c);
            }
        } else
        {
            TxKode.setText("R01");
        }
    }

    private void loadSpinner() {
        List<String> gejala = dbGejala.ambilData();
        List<String> penyakit = dbPenyakit.ambilData();

        // Creating adapter for spinner
        ArrayAdapter<String> dataGejala = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gejala);
        ArrayAdapter<String> dataPenyakit = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, penyakit);

        // Drop down layout style - list view with radio button
        dataGejala.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataPenyakit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spK.setAdapter(dataGejala);
        spA.setAdapter(dataPenyakit);
        /*Cursor cur = positionDB.oneData(txKode.getText().toString().trim());
        if(cur.moveToFirst()){
            sPenyakit.setSelection(Integer.parseInt(cur.getString(cur.getColumnIndex(PositionDB.row_posAlt))));
            sGejala.setSelection(Integer.parseInt(cur.getString(cur.getColumnIndex(PositionDB.row_posKri))));
        }*/
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu_rules, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_add_rules:
                String kode = TxKode.getText().toString().trim();
                String nilai = TxNilai.getText().toString().trim();
                String alt = spA.getSelectedItem().toString().trim().substring(0,2);
                String kri = spK.getSelectedItem().toString().trim().substring(0,3);

                Boolean cek = db.cekData(alt, kri);

                ContentValues values = new ContentValues();
                values.put(DBrules.row_kdRules, kode);
                values.put(DBrules.row_alternatif, alt);
                values.put(DBrules.row_kriteria, kri);
                values.put(DBgejala.row_bobot, nilai);

                if (nilai.length()==0){
                    TxNilai.setError("Masukkan Nilai Rules");
                } else {
                    if (cek==true) {
                        Toast.makeText(TambahRules.this, "Data sudah ada", Toast.LENGTH_SHORT).show();
                    } else {
                        db.insertData(values);
                        Toast.makeText(TambahRules.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
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