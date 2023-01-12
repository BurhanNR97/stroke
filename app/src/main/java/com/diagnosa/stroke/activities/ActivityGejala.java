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
import com.diagnosa.stroke.adapter.AdapterGejala;
import com.diagnosa.stroke.db.DBgejala;

public class ActivityGejala extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DBgejala db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gejala);

        Toolbar toolbar = findViewById(R.id.toolbargejala);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Gejala Penyakit");


        db = new DBgejala(this);
        listView = (ListView)findViewById(R.id.list_gejala);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_gejala, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_gejala) {
            startActivity(new Intent(getApplicationContext(), TambahGejala.class));
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
        Intent intent = new Intent(getApplicationContext(), BerandaDokter.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }

    public void setListView(){
        Cursor cursor = db.allData();
        AdapterGejala customCursorAdapter = new AdapterGejala(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDgejalarow);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();

        Intent idjenis = new Intent(ActivityGejala.this, EditGejala.class);
        idjenis.putExtra(DBgejala.row_id, id);
        startActivity(idjenis);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}