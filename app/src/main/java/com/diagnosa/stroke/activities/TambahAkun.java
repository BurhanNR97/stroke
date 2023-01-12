package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBgejala;
import com.diagnosa.stroke.db.DBuser;

public class TambahAkun extends AppCompatActivity {
    DBuser db;
    EditText txID, txNIK, txNama, txUsername, txPassword;
    RadioButton rbAdmin, rbUser, rbDokter;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_akun);

        Toolbar toolbar = findViewById(R.id.toolbartambahakun);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tambah Data");

        db = new DBuser(this);

        id = getIntent().getLongExtra(DBuser.row_id, 0);

        txID = (EditText)findViewById(R.id.txID_Addakun);
        txNIK = (EditText)findViewById(R.id.txNik_AddAkun);
        txUsername = (EditText)findViewById(R.id.txUsername_AddAkun);
        txPassword = (EditText)findViewById(R.id.txPass_AddAkun);
        txNama = (EditText)findViewById(R.id.txNama_AddAkun);
        rbAdmin = (RadioButton)findViewById(R.id.RB_adminAdd);
        rbUser = (RadioButton)findViewById(R.id.RB_userAdd);
        rbDokter = (RadioButton)findViewById(R.id.RB_dokterAdd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu_akun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_add_akun:
                String nik = txNIK.getText().toString().trim();
                String nama = txNama.getText().toString().trim();
                String pass = txPassword.getText().toString().trim();
                String user = txUsername.getText().toString().trim();
                String level = "";

                //Create Condition if Title and Detail is empty
                if (nik.length()==0){
                    txNIK.requestFocus();
                    txNIK.setError("Masukkan No Identitas");
                } else
                if (nama.length()==0){
                    txNama.requestFocus();
                    txNama.setError("Masukkan Nama Akun");
                } else
                if (user.length()==0){
                    txUsername.requestFocus();
                    txUsername.setError("Masukkan Username");
                } else
                if (pass.length()==0){
                    txPassword.requestFocus();
                    txPassword.setError("Masukkan Password");
                } else{
                    RadioGroup rb = (RadioGroup)findViewById(R.id.RB_level);
                    int selID = rb.getCheckedRadioButtonId();
                    if (selID == rbAdmin.getId()) {
                        level = "admin";
                    } else
                    if (selID == rbUser.getId()) {
                        level = "user";
                    } else
                    if (selID == rbDokter.getId()) {
                        level = "dokter";
                    }

                    if (level.equals("")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Silahkan pilih level", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        ContentValues values = new ContentValues();
                        values.put(DBuser.row_nik, nik);
                        values.put(DBuser.row_nama, nama);
                        values.put(DBuser.row_email, user);
                        values.put(DBuser.row_password, pass);
                        values.put(DBuser.row_level, level);
                        db.insertData(values);
                        Toast.makeText(TambahAkun.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(TambahAkun.this, AkunPengguna.class));
        finish();
    }
}