package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

public class EditAkun extends AppCompatActivity {
    DBuser db;
    EditText txID, txNIK, txNama, txUsername, txPassword;
    RadioButton rbAdmin, rbUser, rbDokter;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);

        Toolbar toolbar = findViewById(R.id.toolbareditakun);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Perbarui Data");

        db = new DBuser(this);

        id = getIntent().getLongExtra(DBuser.row_id, 0);

        txID = (EditText)findViewById(R.id.txID_Editakun);
        txNIK = (EditText)findViewById(R.id.txNik_EditAkun);
        txUsername = (EditText)findViewById(R.id.txUsername_EditAkun);
        txPassword = (EditText)findViewById(R.id.txPass_EditAkun);
        txNama = (EditText)findViewById(R.id.txNama_EditAkun);
        rbAdmin = (RadioButton)findViewById(R.id.RB_adminEdit);
        rbUser = (RadioButton)findViewById(R.id.RB_userEdit);
        rbDokter = (RadioButton)findViewById(R.id.RB_dokterEdit);

        getData();
    }

    @SuppressLint("Range")
    private void getData() {
        Cursor cursor = db.oneData(id);
        if(cursor.moveToFirst()){
            String nik = cursor.getString(cursor.getColumnIndex(DBuser.row_nik));
            String nama = cursor.getString(cursor.getColumnIndex(DBuser.row_nama));
            String user = cursor.getString(cursor.getColumnIndex(DBuser.row_email));
            String pass = cursor.getString(cursor.getColumnIndex(DBuser.row_password));
            String level = cursor.getString(cursor.getColumnIndex(DBuser.row_level));

            txNIK.setText(nik);
            txNama.setText(nama);
            txUsername.setText(user);
            txPassword.setText(pass);

            if (level.equals("admin")){
                rbAdmin.setChecked(true);
                rbUser.setChecked(false);
                rbDokter.setChecked(false);
            } else
            if (level.equals("user")){
                rbAdmin.setChecked(false);
                rbUser.setChecked(true);
                rbDokter.setChecked(false);
            } else
            if (level.equals("dokter")){
                rbDokter.setChecked(true);
                rbAdmin.setChecked(false);
                rbUser.setChecked(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menu_akun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_edit_akun:
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
                    RadioGroup rb = (RadioGroup)findViewById(R.id.RB_leveledit);
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
                        db.updateData(values, id);
                        Toast.makeText(EditAkun.this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
            case R.id.delete_edit_akun:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAkun.this);
                builder.setMessage("Ingin menghapus data ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteData(id);
                        Toast.makeText(EditAkun.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(EditAkun.this, AkunPengguna.class));
        finish();
    }
}