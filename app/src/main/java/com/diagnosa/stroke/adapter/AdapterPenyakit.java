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

import com.diagnosa.stroke.db.DBpenyakit;
import com.diagnosa.stroke.R;

public class AdapterPenyakit extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdapterPenyakit(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_penyakit, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDjenisrow);
        holder.ListKodeJenis = (TextView)v.findViewById(R.id.listkodejenisrow);
        holder.ListNamaJenis = (TextView)v.findViewById(R.id.listnamajenisrow);
        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBpenyakit.row_id)));
        holder.ListKodeJenis.setText(cursor.getString(cursor.getColumnIndex(DBpenyakit.row_kdPenyakit)));
        holder.ListNamaJenis.setText(cursor.getString(cursor.getColumnIndex(DBpenyakit.row_nmPenyakit)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListKodeJenis;
        TextView ListNamaJenis;
    }
}
