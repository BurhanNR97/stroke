package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
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

public class EditRules extends AppCompatActivity {

    DBgejala dbGejala;
    DBpenyakit dbPenyakit;
    DBrules db;
    EditText TxKode, TxNilai;
    Spinner spA, spK;
    long id;
    String alt, kri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rules);

        Toolbar toolbar = findViewById(R.id.toolbareditrules);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Ubah Data");

        dbGejala = new DBgejala(this);
        dbPenyakit = new DBpenyakit(this);
        db = new DBrules(this);

        id = getIntent().getLongExtra(DBrules.row_id, 0);
        alt = String.valueOf(getIntent().getLongExtra(DBpenyakit.row_kdPenyakit, 0));
        kri = String.valueOf(getIntent().getLongExtra(DBgejala.row_kdGejala, 0));

        TxKode = (EditText)findViewById(R.id.txKode_EditRules);
        TxNilai = (EditText)findViewById(R.id.txNilai_editRules);
        spA = (Spinner)findViewById(R.id.sp_alternatif);
        spK = (Spinner)findViewById(R.id.sp_kriteria);

        loadSpinner();
        getData();
    }

    @SuppressLint("Range")
    private void getData() {
        Cursor cursor = db.oneData(id);
        if(cursor.moveToFirst()){
            String kode = cursor.getString(cursor.getColumnIndex(DBrules.row_kdRules));
            String bobot = cursor.getString(cursor.getColumnIndex(DBrules.row_nilai));

            TxKode.setText(kode);
            TxNilai.setText(bobot);

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

        spA.setSelection(Integer.parseInt(alt));
        spK.setSelection(Integer.parseInt(kri));

        /*Cursor cur = positionDB.oneData(txKode.getText().toString().trim());
        if(cur.moveToFirst()){
            sPenyakit.setSelection(Integer.parseInt(cur.getString(cur.getColumnIndex(PositionDB.row_posAlt))));
            sGejala.setSelection(Integer.parseInt(cur.getString(cur.getColumnIndex(PositionDB.row_posKri))));
        }*/
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu_rules, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_edit_rules:
                String kode = TxKode.getText().toString().trim();
                String nilai = TxNilai.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBrules.row_kdRules, kode);
                values.put(DBrules.row_alternatif, spA.getSelectedItem().toString().trim().substring(0,2));
                values.put(DBrules.row_kriteria, spK.getSelectedItem().toString().trim().substring(0,3));
                values.put(DBgejala.row_bobot, nilai);

                if (nilai.length()==0){
                    TxNilai.setError("Masukkan Nilai Rules");
                } else {
                    db.updateData(values, id);
                    Toast.makeText(EditRules.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit_gejala:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditRules.this);
                builder.setMessage("Ingin menghapus data ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteData(id);
                        Toast.makeText(EditRules.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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