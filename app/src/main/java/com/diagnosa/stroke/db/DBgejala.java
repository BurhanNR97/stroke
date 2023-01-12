package com.diagnosa.stroke.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBgejala extends SQLiteOpenHelper {

    public static final String database_name = "stroke_gejala.db";
    public static final String table_name = "gejalapenyakit";
    public static final String row_id = "_id";
    public static final String row_kdGejala = "kd_gejala";
    public static final String row_nmGejala = "nm_gejala";
    public static final String row_bobot = "bobot";

    private SQLiteDatabase db;

    public DBgejala(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_kdGejala + " TEXT,"
                + row_nmGejala + " TEXT," + row_bobot + " INTEGER)";
        String query1 = "INSERT INTO " + table_name + "(" + row_kdGejala +", "+row_nmGejala+", "+row_bobot+")"
                + "VALUES ('G01', 'Gangguan penglihatan',3),"
                + "('G02', 'Kehilangan keseimbangan tubuh',5),"
                + "('G03', 'Kehilangan keterampilan motorik (gerak) halus',4),"
                + "('G04', 'Kejang tanpa riwayat kejang sebelumnya',3),"
                + "('G05', 'Kelainan pada rasa pengecapan',2),"
                + "('G06', 'Kelemahan di lengan atau kaki',4),"
                + "('G07', 'Kelemahan pada bagian wajah secara tiba-tiba',3),"
                + "('G08', 'Kesemutan atau mati rasa pada wajah, lengan atau kaki',5),"
                + "('G09', 'Kesulitan bicara atau memahami pembicaraan',4),"
                + "('G10', 'Kesulitan menelan',3),"
                + "('G11', 'Kesulitan menulis atau membaca',3),"
                + "('G12', 'Lupa mendadak',2),"
                + "('G13', 'Mual atau muntah',3),"
                + "('G14', 'Penurunan atau kehilangan kesadaran',5),"
                + "('G15', 'Sakit kepala hebat tiba-tiba',4)";
        db.execSQL(query);
        db.execSQL(query1);
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

    public Cursor dGejala(String kode){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_kdGejala + "= ?", new String[] {kode});
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

    public Cursor kode(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_kdGejala + " DESC", null);
        return cur;
    }

    public List<String> ambilData() {
        List<String> listData = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", null);
        if (cursor.moveToFirst()) {
            do {
                listData.add(cursor.getString(1) + " - " + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return listData;
    }

    public List<String> kriteria() {
        List<String> listData = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", null);
        if (cursor.moveToFirst()) {
            do {
                listData.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listData;
    }
}
