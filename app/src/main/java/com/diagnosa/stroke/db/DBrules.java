package com.diagnosa.stroke.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBrules extends SQLiteOpenHelper {

    public static final String database_name = "stroke_rules.db";
    public static final String table_name = "rules";
    public static final String row_id = "_id";
    public static final String row_kdRules = "kd_rules";
    public static final String row_alternatif = "alternatif";
    public static final String row_kriteria = "kriteria";
    public static final String row_nilai = "nilai";

    private SQLiteDatabase db;

    public DBrules(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_kdRules + " TEXT,"
                + row_alternatif + " TEXT," + row_kriteria + " TEXT," + row_nilai + " INTEGER)";
        String query1 = "INSERT INTO " + table_name + "(" + row_kdRules +", "+ row_alternatif +", "+ row_kriteria +", "+ row_nilai +")"
                + "VALUES ('R01', 'S1', 'G01', 3),"
                + "('R02', 'S1', 'G02', 3),"
                + "('R03', 'S1', 'G03', 3),"
                + "('R04', 'S1', 'G04', 1),"
                + "('R05', 'S1', 'G05', 2),"
                + "('R06', 'S1', 'G06', 3),"
                + "('R07', 'S1', 'G07', 2),"
                + "('R08', 'S1', 'G08', 3),"
                + "('R09', 'S1', 'G09', 4),"
                + "('R10', 'S1', 'G10', 1),"
                + "('R11', 'S1', 'G11', 2),"
                + "('R12', 'S1', 'G12', 5),"
                + "('R13', 'S1', 'G13', 2),"
                + "('R14', 'S1', 'G14', 5),"
                + "('R15', 'S1', 'G15', 5),"
                + "('R16', 'S2', 'G01', 3),"
                + "('R17', 'S2', 'G02', 3),"
                + "('R18', 'S2', 'G03', 4),"
                + "('R19', 'S2', 'G04', 1),"
                + "('R20', 'S2', 'G05', 2),"
                + "('R21', 'S2', 'G06', 4),"
                + "('R22', 'S2', 'G07', 5),"
                + "('R23', 'S2', 'G08', 3),"
                + "('R24', 'S2', 'G09', 4),"
                + "('R25', 'S2', 'G10', 1),"
                + "('R26', 'S2', 'G11', 2),"
                + "('R27', 'S2', 'G12', 5),"
                + "('R28', 'S2', 'G13', 2),"
                + "('R29', 'S2', 'G14', 5),"
                + "('R30', 'S2', 'G15', 5),"
                + "('R31', 'S3', 'G01', 3),"
                + "('R32', 'S3', 'G02', 3),"
                + "('R33', 'S3', 'G03', 5),"
                + "('R34', 'S3', 'G04', 5),"
                + "('R35', 'S3', 'G05', 5),"
                + "('R36', 'S3', 'G06', 3),"
                + "('R37', 'S3', 'G07', 1),"
                + "('R38', 'S3', 'G08', 2),"
                + "('R39', 'S3', 'G09', 3),"
                + "('R40', 'S3', 'G10', 5),"
                + "('R41', 'S3', 'G11', 5),"
                + "('R42', 'S3', 'G12', 1),"
                + "('R43', 'S3', 'G13', 5),"
                + "('R44', 'S3', 'G14', 3),"
                + "('R45', 'S3', 'G15', 3),"
                + "('R46', 'S4', 'G01', 3),"
                + "('R47', 'S4', 'G02', 3),"
                + "('R48', 'S4', 'G03', 5),"
                + "('R49', 'S4', 'G04', 5),"
                + "('R50', 'S4', 'G05', 5),"
                + "('R51', 'S4', 'G06', 4),"
                + "('R52', 'S4', 'G07', 1),"
                + "('R53', 'S4', 'G08', 2),"
                + "('R54', 'S4', 'G09', 3),"
                + "('R55', 'S4', 'G10', 5),"
                + "('R56', 'S4', 'G11', 5),"
                + "('R57', 'S4', 'G12', 1),"
                + "('R58', 'S4', 'G13', 5),"
                + "('R59', 'S4', 'G14', 4),"
                + "('R60', 'S4', 'G15', 4)";
        db.execSQL(query);
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", null);
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

    public Cursor kode(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_kdRules + " DESC", null);
        return cur;
    }

    public Cursor checkData (String alt, String kri) {
        String table = table_name;
        String[] columns = new String[] {"*"};
        String selection = row_alternatif+ "=?" + " and " + row_kriteria + "=?";
        String[] selectionArgs = {alt,kri};
        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
        return cursor;
    }

    public boolean cekData(String alt, String Ci) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif = ? AND kriteria = ?",new String[] {alt,Ci});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor dataAlt(String alt) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif LIKE ? ORDER BY "+row_id+"",new String[] {alt});
        return cursor;
    }

    public Cursor dataKri(String ci) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE kriteria LIKE ? ORDER BY "+row_id+"",new String[] {ci});
        return cursor;
    }

    @SuppressLint("Range")
    public List<Integer> a1() {
        List<Integer> listData = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif = ? ORDER BY " + row_id + " ASC ", new String[] {"S1"});
        if (cursor.moveToFirst()) {
            do {
                listData.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex(row_nilai))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listData;
    }

    @SuppressLint("Range")
    public List<Integer> a2() {
        List<Integer> listData = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif = ? ORDER BY " + row_id + " ASC ", new String[] {"S2"});
        if (cursor.moveToFirst()) {
            do {
                listData.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex(row_nilai))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listData;
    }

    public Cursor atribut(String alt, String kri) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif = ? AND kriteria = ? ORDER BY " + row_id + " ASC ", new String[] {alt, kri});
        return cursor;
    }
}
