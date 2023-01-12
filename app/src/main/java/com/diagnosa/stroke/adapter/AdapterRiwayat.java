package com.diagnosa.stroke.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBdiagnosa;
import com.diagnosa.stroke.db.DBgejala;

public class AdapterRiwayat extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdapterRiwayat(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_riwayat, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDriwayatrow);
        holder.ListKode = (TextView)v.findViewById(R.id.listkoderiwayatrow);
        holder.ListNIK = (TextView)v.findViewById(R.id.listnikpasienrow);
        holder.ListNama = (TextView)v.findViewById(R.id.listnamapasienrow);
        holder.ListHasil = (TextView)v.findViewById(R.id.listhasilriwayatrow);
        holder.ListTgl = (TextView)v.findViewById(R.id.listtglriwayatrow);
        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBdiagnosa.row_id)));
        holder.ListKode.setText(cursor.getString(cursor.getColumnIndex(DBdiagnosa.row_kdDiagnosa)));
        holder.ListNIK.setText(cursor.getString(cursor.getColumnIndex(DBdiagnosa.row_nik_pasien)));
        holder.ListNama.setText(cursor.getString(cursor.getColumnIndex(DBdiagnosa.row_nama_pasien)));
        holder.ListHasil.setText(cursor.getString(cursor.getColumnIndex(DBdiagnosa.row_hasil_diagnosa)));
        holder.ListTgl.setText(cursor.getString(cursor.getColumnIndex(DBdiagnosa.row_tgl_diagnosa)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListKode;
        TextView ListNIK;
        TextView ListNama;
        TextView ListHasil;
        TextView ListTgl;
    }
}
