package com.diagnosa.stroke.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBcetak extends SQLiteOpenHelper {

    public static final String database_name = "stroke_gejala1.db";
    public static final String table_name = "cetak";
    public static final String row_id = "_id";
    public static final String row_kdCetak = "kd_cetak";
    public static final String row_nik = "nik";
    public static final String row_nama = "nama";
    public static final String row_gejala = "gejala";
    public static final String row_hasil = "hasil";
    public static final String row_persen = "persen";

    private SQLiteDatabase db;

    public DBcetak(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_kdCetak + " TEXT, "
                + row_nik + "TEXT, " + row_nama + "TEXT, " + row_gejala + "TEXT, " + row_hasil + "TEXT, " + row_persen + "TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", new String[]{});
        return cur;
    }

    public Cursor oneData(long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(table_name, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }

    public Cursor kode(String kode){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_kdCetak + " LIKE " + kode, null);
        return cur;
    }
}
