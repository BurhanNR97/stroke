package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.adapter.AdapterAkun;
import com.diagnosa.stroke.adapter.AdapterGejala;
import com.diagnosa.stroke.db.DBgejala;
import com.diagnosa.stroke.db.DBuser;

public class AkunPengguna extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DBuser db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_pengguna);

        Toolbar toolbar = findViewById(R.id.toolbarakun);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Akun Pengguna");


        db = new DBuser(this);
        listView = (ListView)findViewById(R.id.list_akun);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_akun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_akun) {
            startActivity(new Intent(getApplicationContext(), TambahAkun.class));
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
        Intent intent = new Intent(getApplicationContext(), BerandaAdmin.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }

    public void setListView(){
        Cursor cursor = db.allData();
        AdapterAkun customCursorAdapter = new AdapterAkun(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDakunrow);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();

        Intent intent = new Intent(AkunPengguna.this, EditAkun.class);
        intent.putExtra(DBgejala.row_id, id);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}