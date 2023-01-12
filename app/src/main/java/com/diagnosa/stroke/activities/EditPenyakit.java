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
import android.widget.EditText;
import android.widget.Toast;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBpenyakit;

public class EditPenyakit extends AppCompatActivity {

    DBpenyakit db;
    EditText TxKode, TxNama;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_penyakit);

        Toolbar toolbar = findViewById(R.id.toolbareditjenis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Ubah Data");

        db = new DBpenyakit(this);

        id = getIntent().getLongExtra(DBpenyakit.row_id, 0);

        TxKode = (EditText)findViewById(R.id.txKode_EditJenis);
        TxNama = (EditText)findViewById(R.id.txNama_EditJenis);

        getData();
    }

    @SuppressLint("Range")
    private void getData() {
        Cursor cursor = db.oneData(id);
        if(cursor.moveToFirst()){
            String kode = cursor.getString(cursor.getColumnIndex(DBpenyakit.row_kdPenyakit));
            String nama = cursor.getString(cursor.getColumnIndex(DBpenyakit.row_nmPenyakit));

            TxKode.setText(kode);
            TxNama.setText(nama);
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu_jenis, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_edit_jenis:
                String kode = TxKode.getText().toString().trim();
                String nama = TxNama.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBpenyakit.row_kdPenyakit, kode);
                values.put(DBpenyakit.row_nmPenyakit, nama);

                if (nama.length()==0){
                    TxNama.setError("Masukkan nama penyakit");
                }else {
                    db.updateData(values, id);
                    Toast.makeText(EditPenyakit.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit_jenis:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPenyakit.this);
                builder.setMessage("Ingin menghapus data ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteData(id);
                        Toast.makeText(EditPenyakit.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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