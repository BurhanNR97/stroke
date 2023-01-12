package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBdiagnosa;
import com.diagnosa.stroke.db.DBgejala;
import com.diagnosa.stroke.db.DBuser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DiagnosaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    DBgejala dBgejala;
    DBdiagnosa dBdiagnosa;
    Button proses;
    String nik = "";

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);

        Toolbar toolbar = findViewById(R.id.toolbardiagnosa);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Mulai Diagnosa");

        listView = (ListView)findViewById(R.id.list_diagnosa);
        listView.setOnItemClickListener(this);
        dBgejala = new DBgejala(this);
        dBdiagnosa = new DBdiagnosa(this);
        proses = findViewById(R.id.btn_hasil_diagnosa);

        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listView.getCheckedItemCount()<2){
                    Toast toast = Toast.makeText(DiagnosaActivity.this, "Harap memilih 2 gejala atau lebih", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    StringBuffer terpilih = new StringBuffer();
                    String data = "";
                    for (int i=0; i<listView.getCount(); i++){
                        if (listView.isItemChecked(i)){
                            data = listView.getItemAtPosition(i).toString().substring(0,3);
                            terpilih.append(data).append('#');
                            Intent intent = new Intent(getApplicationContext(), ActivityHasil.class);
                            intent.putExtra("HASIL", terpilih.toString());
                            intent.putExtra("nik", nik);
                            intent.putExtra("id", getIntent().getStringExtra("id"));
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), BerandaPasien.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }

    public void setListView(){
        List<String> data = dBgejala.ambilData();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, data);
        listView.setAdapter(dataAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}