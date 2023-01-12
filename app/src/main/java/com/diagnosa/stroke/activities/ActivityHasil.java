package com.diagnosa.stroke.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBcetak;
import com.diagnosa.stroke.db.DBdiagnosa;
import com.diagnosa.stroke.db.DBgejala;
import com.diagnosa.stroke.db.DBpenyakit;
import com.diagnosa.stroke.db.DBrules;
import com.diagnosa.stroke.db.DBuser;
import com.diagnosa.stroke.kelas.cAtribut;
import com.diagnosa.stroke.kelas.cBobot;
import com.diagnosa.stroke.kelas.cJarak;
import com.diagnosa.stroke.kelas.cNormalisasi;
import com.diagnosa.stroke.kelas.cSolusiIdeal;
import com.diagnosa.stroke.kelas.cTerbobot;
import com.diagnosa.stroke.model.aAtribut;
import com.diagnosa.stroke.model.aBobot;
import com.diagnosa.stroke.model.aJarak;
import com.diagnosa.stroke.model.aNormalisasi;
import com.diagnosa.stroke.model.aSolusiIdeal;
import com.diagnosa.stroke.model.aTerbobot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActivityHasil extends AppCompatActivity {
    String[] terpilih;
    String[] alternatif = null;
    String[] kriteria = null;
    int[] bobot;
    int[][] atribut = null;
    double[] pembagi;
    double[][] normalisasi;
    double[][] terbobot;
    double[] yPlus;
    double[] yMin;
    double[] dPlus;
    Spinner spin;
    double[] dMin;
    double[] preferensi;
    String[] aRank;
    double[] nRank;
    String teks = "";
    DBgejala dBgejala;
    DBpenyakit dBpenyakit;
    DBrules dBrules;
    DBuser dBuser;
    TextView tvHasil;
    NumberFormat angka;
    Number number;
    Button maju, mundur, majuTdk, mundurTdk;
    LinearLayout lyBoot, lyAtribut, lyNormalisasi, lyTerbobot, lySolusi, lyJarak;
    TextView tBobot, tAtribut, tNormalisasi, tTerbobot, tSolusi, tJarak;
    int nilaiSpin;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        Toolbar toolbar = findViewById(R.id.toolbarhasil);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Hasil Diagnosa");

        TextView tvNama = findViewById(R.id.tv_nama_penyakit);
        TextView persentasi = findViewById(R.id.tv_persentase);
        dBgejala = new DBgejala(this);
        dBpenyakit = new DBpenyakit(this);
        dBrules = new DBrules(this);
        dBuser = new DBuser(this);
        angka = NumberFormat.getInstance(Locale.FRANCE);
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setDecimalSeparator(',');
        simbol.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(simbol);
        maju = findViewById(R.id.btn_next1);
        majuTdk = findViewById(R.id.btn_next0);
        mundur = findViewById(R.id.btn_prev1);
        mundurTdk = findViewById(R.id.btn_prev0);

        lyAtribut = findViewById(R.id.lyAtribut);
        lyBoot = findViewById(R.id.lyBobot);
        lyJarak = findViewById(R.id.lyJarak);
        lyNormalisasi = findViewById(R.id.lyNormalisasi);
        lySolusi = findViewById(R.id.lySolusi);
        lyTerbobot = findViewById(R.id.lyTerbobot);

        tAtribut = findViewById(R.id.rtAtribut);
        tBobot = findViewById(R.id.rtBobot);
        tJarak = findViewById(R.id.rtJarak);
        tNormalisasi = findViewById(R.id.rtNormalisasi);
        tSolusi = findViewById(R.id.rtSolusiIdeal);
        tTerbobot = findViewById(R.id.rtTerbobot);

        String dataGejala = getIntent().getStringExtra("HASIL");
        terpilih = new String[0];
        if (dataGejala != null) {
            terpilih = dataGejala.split("#");
        }

        //Kriteria
        kriteria = new String[terpilih.length];
        for (int i=0; i<terpilih.length; i++){
            Cursor ambilGejala = dBgejala.dGejala(terpilih[i]);
            if (ambilGejala.moveToNext()) {
                kriteria[i] = ambilGejala.getString(ambilGejala.getColumnIndex(DBgejala.row_nmGejala));
            }
        }

        StringBuffer output_gejala_terpilih = new StringBuffer();
        int no = 1;
        for (String s_gejala_terpilih : kriteria) {
            output_gejala_terpilih.append(no++)
                    .append(". ")
                    .append(s_gejala_terpilih)
                    .append("\n");
        }
        TextView tv_list_gejala_dipilih = findViewById(R.id.tv_list_gejala_dipilih);
        tv_list_gejala_dipilih.setText(output_gejala_terpilih);

        //PERHITUNGAN TOPSIS

        //Alternatif
        Cursor cr = dBpenyakit.allData();
        List<String> nn = dBpenyakit.alternatif();
        if(cr.moveToNext()){
            int jml = cr.getColumnCount();
            for (int i=0; i-1<jml-1; i++){
                alternatif = nn.toArray(new String[i]);
            }
        }

        //Nilai Bobot
        bobot = new int[kriteria.length];
        for (int i=0; i<kriteria.length; i++){
            Cursor cur = dBgejala.dGejala(terpilih[i]);
            if (cur.moveToNext()){
                bobot[i] = Integer.parseInt(cur.getString(cur.getColumnIndex(DBgejala.row_bobot)));
            }
        }

        //Nilai Atribut
        atribut = new int[kriteria.length][alternatif.length];
        for (int i=0; i<kriteria.length; i++){
            for (int j=0; j<alternatif.length; j++){
                String p = alternatif[j];
                String g = terpilih[i];
                Cursor cursor = dBrules.atribut(p,g);
                if (cursor.moveToNext()){
                    int n = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBrules.row_nilai)));
                    atribut[i][j] = n;
                }
            }
        }

        //Nilai Pembagi
        pembagi = new double[kriteria.length];
        for (int i=0; i<kriteria.length; i++){
            pembagi[i] = 0;
            for (int j=0; j<alternatif.length; j++){
                pembagi[i] = pembagi[i] + (atribut[i][j] * atribut[i][j]);
            }
            pembagi[i] = Math.sqrt(pembagi[i]);
        }

        //Matriks ternormalisasi
        normalisasi = new double[kriteria.length][alternatif.length];
        for (int i=0; i<alternatif.length; i++){
            for (int j=0; j<kriteria.length; j++){
                normalisasi[j][i] = Double.parseDouble(String.format("%.5f", atribut[j][i] / pembagi[j]).replace(',','.'));
            }
        }

        //Matriks normalisasi terbobot
        terbobot = new double[kriteria.length][alternatif.length];
        for (int i=0; i<alternatif.length; i++){
            for (int j=0; j<kriteria.length; j++){
                terbobot[j][i] = Double.parseDouble(String.format("%.5f", normalisasi[j][i] * bobot[j]).replace(',','.'));
            }
        }

        //Solusi ideal positif
        yPlus = new double[kriteria.length];
        for (int i=0; i<kriteria.length; i++){
            for (int j=0; j<alternatif.length; j++){
                if ((j==0 || (yPlus[i] < terbobot[i][j]))){
                    yPlus[i] = terbobot[i][j];
                }
            }
        }

        //Solusi ideal negatif
        yMin = new double[kriteria.length];
        for (int i=0; i<kriteria.length; i++){
            for (int j=0; j<alternatif.length; j++){
                if ((j==0 || (yMin[i] > terbobot[i][j]))){
                    yMin[i] = terbobot[i][j];
                }
            }
        }

        //Jarak solusi ideal dengan alternatif
        dPlus = new double[alternatif.length];
        for (int i=0; i<alternatif.length; i++){
            dPlus[i] = 0;
            for (int j=0; j<kriteria.length; j++){
                dPlus[i] = dPlus[i] + ((terbobot[j][i] - yPlus[j]) * (terbobot[j][i] - yPlus[j]));
            }
            dPlus[i] = Double.parseDouble(String.format("%.5f", Math.sqrt(dPlus[i])).replace(',','.'));
        }

        dMin = new double[alternatif.length];
        for (int i=0; i<alternatif.length; i++){
            dMin[i] = 0;
            for (int j=0; j<kriteria.length; j++){
                dMin[i] = dMin[i] + ((terbobot[j][i] - yMin[j]) * (terbobot[j][i] - yMin[j]));
            }
            dMin[i] = Double.parseDouble(String.format("%.5f", Math.sqrt(dMin[i])).replace(',','.'));
        }

        preferensi = new double[alternatif.length];
        for (int i=0; i<alternatif.length; i++){
            preferensi[i] = Double.parseDouble(String.format("%.5f", dMin[i] / (dMin[i] + dPlus[i])).replace(',','.'));
        }

        //Rangking
        aRank = new String[alternatif.length];
        nRank = new double[alternatif.length];
        for (int i=0; i<alternatif.length; i++){
            nRank[i] = preferensi[i];
            aRank[i] = alternatif[i];
        }
        for (int i=0; i<alternatif.length; i++){
            for (int j=1; j<alternatif.length; j++){
                if (nRank[j] > nRank[i]){
                    double tempN = nRank[i];
                    String tempA = aRank[i];
                    nRank[i] = nRank[j];
                    aRank[i] = aRank[j];
                    nRank[j] = Double.parseDouble(String.format("%.5f", tempN).replace(',','.'));
                    aRank[j] = tempA;
                }
            }
        }
        Cursor curNama = dBpenyakit.hasil(aRank[0]);
        if (curNama.moveToFirst()){
            tvNama.setText(curNama.getString(curNama.getColumnIndex(DBpenyakit.row_nmPenyakit)));
        }
        persentasi.setText(Integer.valueOf((int) Math.round(nRank[0] * 100))+" %");

        tampilTOPSIS();

        LinearLayout ly = findViewById(R.id.teksHasil);
        Button btn = findViewById(R.id.btn_topsis);
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String tulis = btn.getText().toString().trim();
                if (tulis.equals("TAMPILKAN PERHITUNGAN")){
                    ly.setVisibility(View.VISIBLE);
                    btn.setText("SEMBUNYIKAN PERHITUNGAN");
                } else
                if (tulis.equals("SEMBUNYIKAN PERHITUNGAN")){
                    ly.setVisibility(View.GONE);
                    btn.setText("TAMPILKAN PERHITUNGAN");
                }
            }
        });

        spin = findViewById(R.id.spinner1);
        spin.setSelection(1);
        nilaiSpin = spin.getSelectedItemPosition();
       // tampilHitung();

        mundur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nilaiSpin = nilaiSpin - 1;
                spin.setSelection(nilaiSpin);
                maju.setVisibility(View.VISIBLE);
                majuTdk.setVisibility(View.GONE);
                if (nilaiSpin <= 1){
                    mundur.setVisibility(View.GONE);
                    mundurTdk.setVisibility(View.VISIBLE);
                }
            }
        });

        maju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nilaiSpin = nilaiSpin + 1;
                spin.setSelection(nilaiSpin);
                if (nilaiSpin > 1){
                    mundur.setVisibility(View.VISIBLE);
                    mundurTdk.setVisibility(View.GONE);
                    maju.setVisibility(View.VISIBLE);
                    majuTdk.setVisibility(View.GONE);
                }
                if (nilaiSpin == 6){
                    maju.setVisibility(View.GONE);
                    majuTdk.setVisibility(View.VISIBLE);
                }
            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nilaiSpin = spin.getSelectedItemPosition();
                tampilHitung();
                if (nilaiSpin <= 1){
                    mundur.setVisibility(View.GONE);
                    mundurTdk.setVisibility(View.VISIBLE);
                    majuTdk.setVisibility(View.GONE);
                    maju.setVisibility(View.VISIBLE);
                } else
                if (nilaiSpin > 1 && nilaiSpin < 6){
                    mundur.setVisibility(View.VISIBLE);
                    mundurTdk.setVisibility(View.GONE);
                    majuTdk.setVisibility(View.GONE);
                    maju.setVisibility(View.VISIBLE);
                }
                if (nilaiSpin == 6){
                    maju.setVisibility(View.GONE);
                    majuTdk.setVisibility(View.VISIBLE);
                    mundur.setVisibility(View.VISIBLE);
                    mundurTdk.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DBdiagnosa dBdiagnosa = new DBdiagnosa(this);
        Cursor kode = dBdiagnosa.kode();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat nomorkode = new SimpleDateFormat("yyMMdd");
        String tgl = nomorkode.format(c.getTime());
        String kd_diagnosa = "";
        if (kode.moveToNext()) {
            String a = kode.getString(1).substring(6);
            int b = Integer.parseInt(a);
            int cc = b + 1;
            String d = String.valueOf(cc);
            kd_diagnosa = tgl+d;
        } else {
            kd_diagnosa = tgl+1;
        }

        String nik = getIntent().getStringExtra("nik");
        Cursor coba = dBuser.cekData(nik);
        String nama = "";
        if (coba.moveToFirst()){
            nama = coba.getString(coba.getColumnIndex(DBuser.row_nama));
        }

        SimpleDateFormat dt = new SimpleDateFormat("d MMM yyyy");
        String formatdate = dt.format(c.getTime());
        ContentValues dataDiag = new ContentValues();
        dataDiag.put(DBdiagnosa.row_kdDiagnosa, kd_diagnosa);
        dataDiag.put(DBdiagnosa.row_nik_pasien, nik);
        dataDiag.put(DBdiagnosa.row_nama_pasien, nama);
        dataDiag.put(DBdiagnosa.row_hasil_diagnosa, persentasi.getText().toString()+" "+tvNama.getText().toString());
        dataDiag.put(DBdiagnosa.row_tgl_diagnosa, formatdate);
        dBdiagnosa.insertData(dataDiag);
    }

    public void tampilHitung(){
        switch (nilaiSpin){
            case 0:
                lyTerbobot.setVisibility(View.VISIBLE);
                lySolusi.setVisibility(View.VISIBLE);
                lyNormalisasi.setVisibility(View.VISIBLE);
                lyJarak.setVisibility(View.VISIBLE);
                lyBoot.setVisibility(View.VISIBLE);
                lyAtribut.setVisibility(View.VISIBLE);

                tTerbobot.setVisibility(View.VISIBLE);
                tSolusi.setVisibility(View.VISIBLE);
                tNormalisasi.setVisibility(View.VISIBLE);
                tJarak.setVisibility(View.VISIBLE);
                tBobot.setVisibility(View.VISIBLE);
                tAtribut.setVisibility(View.VISIBLE);
                break;
            case 1:
                lyTerbobot.setVisibility(View.GONE);
                lySolusi.setVisibility(View.GONE);
                lyNormalisasi.setVisibility(View.GONE);
                lyJarak.setVisibility(View.GONE);
                lyBoot.setVisibility(View.VISIBLE);
                lyAtribut.setVisibility(View.GONE);
                kosong();
                break;
            case 2:
                lyTerbobot.setVisibility(View.GONE);
                lySolusi.setVisibility(View.GONE);
                lyNormalisasi.setVisibility(View.GONE);
                lyJarak.setVisibility(View.GONE);
                lyBoot.setVisibility(View.GONE);
                lyAtribut.setVisibility(View.VISIBLE);
                kosong();
                break;
            case 3:
                lyTerbobot.setVisibility(View.GONE);
                lySolusi.setVisibility(View.GONE);
                lyNormalisasi.setVisibility(View.VISIBLE);
                lyJarak.setVisibility(View.GONE);
                lyBoot.setVisibility(View.GONE);
                lyAtribut.setVisibility(View.GONE);
                kosong();
                break;
            case 4:
                lyTerbobot.setVisibility(View.VISIBLE);
                lySolusi.setVisibility(View.GONE);
                lyNormalisasi.setVisibility(View.GONE);
                lyJarak.setVisibility(View.GONE);
                lyBoot.setVisibility(View.GONE);
                lyAtribut.setVisibility(View.GONE);
                kosong();
                break;
            case 5:
                lyTerbobot.setVisibility(View.GONE);
                lySolusi.setVisibility(View.VISIBLE);
                lyNormalisasi.setVisibility(View.GONE);
                lyJarak.setVisibility(View.GONE);
                lyBoot.setVisibility(View.GONE);
                lyAtribut.setVisibility(View.GONE);
                kosong();
                break;
            case 6:
                lyTerbobot.setVisibility(View.GONE);
                lySolusi.setVisibility(View.GONE);
                lyNormalisasi.setVisibility(View.GONE);
                lyJarak.setVisibility(View.VISIBLE);
                lyBoot.setVisibility(View.GONE);
                lyAtribut.setVisibility(View.GONE);
                kosong();
                break;
        }
    }

    private void kosong(){
        tTerbobot.setVisibility(View.GONE);
        tSolusi.setVisibility(View.GONE);
        tNormalisasi.setVisibility(View.GONE);
        tJarak.setVisibility(View.GONE);
        tBobot.setVisibility(View.GONE);
        tAtribut.setVisibility(View.GONE);
    }

    private void tampilTOPSIS(){
        //Tampil Tabel Nilai Bobot
        RecyclerView rvBobot = findViewById(R.id.rvBobot);
        aBobot adapter1 = new aBobot(getBobot());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        rvBobot.setLayoutManager(linearLayoutManager1);
        rvBobot.setAdapter(adapter1);

        //Tampil Tabel Nilai Atribut
        RecyclerView rvAtribut = findViewById(R.id.rvAtribut);
        aAtribut adapter2 = new aAtribut(getAtribut());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        rvAtribut.setLayoutManager(linearLayoutManager2);
        rvAtribut.setAdapter(adapter2);

        //Tampil Tabel Matrik Normalisasi
        RecyclerView rvNormal = findViewById(R.id.rvNormalisasi);
        aNormalisasi adapter3 = new aNormalisasi(getNormalisasi());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        rvNormal.setLayoutManager(linearLayoutManager3);
        rvNormal.setAdapter(adapter3);

        //Tampil Tabel Matrik Normalisasi Terbobot
        RecyclerView rvTerbobot = findViewById(R.id.rvTerbobot);
        aTerbobot adapter4 = new aTerbobot(getTerbobot());
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
        rvTerbobot.setLayoutManager(linearLayoutManager4);
        rvTerbobot.setAdapter(adapter4);

        //Tampil Tabel Solusi Ideal
        RecyclerView rvSolusiIdeal = findViewById(R.id.rvSolusiIdeal);
        aSolusiIdeal adapter5 = new aSolusiIdeal(getSolusiIdeal());
        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(this);
        rvSolusiIdeal.setLayoutManager(linearLayoutManager5);
        rvSolusiIdeal.setAdapter(adapter5);

        //Tampil Tabel Jarak Solusi Ideal Terhadap Alternatif
        RecyclerView rvJarak = findViewById(R.id.rvJarak);
        aJarak adapter6 = new aJarak(getJarak());
        LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(this);
        rvJarak.setLayoutManager(linearLayoutManager6);
        rvJarak.setAdapter(adapter6);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityHasil.this, DiagnosaActivity.class);
        intent.putExtra("nik", getIntent().getStringExtra("nik"));
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }

    private List getBobot(){
        List<cBobot> data = new ArrayList<>();
        for (int i=0; i<kriteria.length; i++){
            data.add(new cBobot(terpilih[i], bobot[i]));
        }
        return data;
    }

    private List getAtribut(){
        List<cAtribut> data = new ArrayList<>();
        for (int i=0; i<kriteria.length; i++){
            data.add(new cAtribut(terpilih[i], atribut[i][0], atribut[i][1], atribut[i][2], atribut[i][3]));
        }
        return data;
    }

    private List getNormalisasi(){
        List<cNormalisasi> data = new ArrayList<>();
        for (int i=0; i<kriteria.length; i++){
            data.add(new cNormalisasi(terpilih[i], normalisasi[i][0], normalisasi[i][1], normalisasi[i][2], normalisasi[i][3]));
        }
        return data;
    }

    private List getTerbobot(){
        List<cTerbobot> data = new ArrayList<>();
        for (int i=0; i<kriteria.length; i++){
            data.add(new cTerbobot(terpilih[i], terbobot[i][0], terbobot[i][1], terbobot[i][2], terbobot[i][3]));
        }
        return data;
    }

    private List getSolusiIdeal(){
        List<cSolusiIdeal> data = new ArrayList<>();
        for (int i=0; i<kriteria.length; i++){
            data.add(new cSolusiIdeal(terpilih[i], yPlus[i], yMin[i]));
        }
        return data;
    }

    private List getJarak(){
        List<cJarak> data = new ArrayList<>();
        for (int i=0; i<alternatif.length; i++){
            data.add(new cJarak(alternatif[i], dPlus[i], dMin[i], preferensi[i]));
        }
        return data;
    }

    private void cetak() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = new Canvas();
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(70);
        canvas.drawText("HASIL DIAGNOSA", 1200, 500, titlePaint);

        pdfDocument.finishPage(page);

       /* String kodebaru = "";
        String kodelama = cur.getString(cur.getColumnIndex(DBcetak.row_kdCetak));
        Cursor kode = dBcetak.kode(kodelama);
        if (kode.getCount() == 0){
            kodebaru = kodelama + "-1";
        } else {
            int jml = kode.getCount() + 1;
            kodebaru = kodelama + "-" + jml;
        }*/

        File file = new File(Environment.getExternalStorageDirectory(), "/1.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }
}