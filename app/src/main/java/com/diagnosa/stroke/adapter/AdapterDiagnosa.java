package com.diagnosa.stroke.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.diagnosa.stroke.db.DBgejala;
import com.diagnosa.stroke.R;

public class AdapterDiagnosa extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdapterDiagnosa(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_diagnosa, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListBobot = (TextView)v.findViewById(R.id.listBobot_diagnosarow);
        holder.ListGejala = (CheckBox) v.findViewById(R.id.listgejaladiagnosarow);
        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();
        holder.ListBobot.setText(cursor.getString(cursor.getColumnIndex(DBgejala.row_bobot)));
        holder.ListGejala.setText(cursor.getString(cursor.getColumnIndex(DBgejala.row_kdGejala))+" "+cursor.getString(cursor.getColumnIndex(DBgejala.row_nmGejala)));
    }

    class MyHolder {
        TextView ListBobot;
        CheckBox ListGejala;
    }
}
