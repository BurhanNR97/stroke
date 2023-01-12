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
import com.diagnosa.stroke.adapter.AdapterRules;
import com.diagnosa.stroke.db.DBgejala;
import com.diagnosa.stroke.db.DBpenyakit;
import com.diagnosa.stroke.db.DBrules;

import java.util.List;

public class ActivityRules extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DBrules db;
    DBgejala dbGejala;
    DBpenyakit dbPenyakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        Toolbar toolbar = findViewById(R.id.toolbarrules);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Aturan Nilai");

        dbGejala = new DBgejala(this);
        dbPenyakit = new DBpenyakit(this);
        db = new DBrules(this);
        listView = (ListView)findViewById(R.id.list_rules);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_rules, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_rules) {
            startActivity(new Intent(getApplicationContext(), TambahRules.class));
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
        AdapterRules customCursorAdapter = new AdapterRules(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDrulesrow);
        TextView dataRules = (TextView)view.findViewById(R.id.listnamarulesrow);
        final long id = Long.parseLong(getId.getText().toString());
        final String alternatif = dataRules.getText().toString().trim().substring(1,3);
        final String kriteria = dataRules.getText().toString().trim().substring(10,13);

        List<String> g = dbGejala.kriteria();
        List<String> p = dbPenyakit.alternatif();

        final long alt = p.indexOf(alternatif);
        final long kri = g.indexOf(kriteria);

        Cursor cur = db.oneData(id);
        cur.moveToFirst();

        Intent idjenis = new Intent(ActivityRules.this, EditRules.class);
        idjenis.putExtra(DBrules.row_id, id);
        idjenis.putExtra(DBpenyakit.row_kdPenyakit, alt);
        idjenis.putExtra(DBgejala.row_kdGejala, kri);
        startActivity(idjenis);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}